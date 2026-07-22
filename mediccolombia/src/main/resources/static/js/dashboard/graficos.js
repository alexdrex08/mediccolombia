(function () {

    const COLORES = {
        critico: '#dc3545',
        suficiente: '#198754',
        exceso: '#ffc107',
        entrada: '#0d6efd',
        salida: '#dc3545',
        ventaLinea: '#0dcaf0',
        ventaRelleno: 'rgba(13, 202, 240, 0.12)',
        stockBajo: '#ffc107',
        proximoVencer: '#fd7e14',
        vencido: '#dc3545',
    };

    function ocultarSpinner(id) {
        const el = document.getElementById(id);
        if (el) el.style.display = 'none';
    }
    function mostrarError(id) {
        const el = document.getElementById(id);
        if (el) el.style.display = '';
    }
    function diasDelMesActual() {
        const hoy = new Date();
        const total = new Date(hoy.getFullYear(), hoy.getMonth() + 1, 0).getDate();
        return Array.from({ length: total }, (_, i) => i + 1);
    }
    function nombreMesActual() {
        return new Date().toLocaleDateString('es-CO', { month: 'long', year: 'numeric' });
    }

    let loadedInventario = false;
    let loadedVentas = false;
    let loadedAlertas = false;

    let chartStock = null;
    let chartMovimientos = null;
    let chartVentas = null;
    let chartTop = null;
    let chartAlertas = null;

    // ══════════════════════════════════════════════════════════════
    // 1. CARGA TAB INVENTARIO
    // ══════════════════════════════════════════════════════════════
    function loadInventario() {
        if (loadedInventario) return;
        loadedInventario = true;

        // 1.1 Stock Doughnut
        fetch('/api/productos')
            .then(r => r.ok ? r.json() : Promise.reject(r.status))
            .then(productos => {
                const conteo = { 'Crítico': 0, 'Suficiente': 0, 'Exceso': 0 };
                productos.forEach(p => {
                    if (conteo[p.estadoStock] !== undefined) conteo[p.estadoStock]++;
                    else conteo['Suficiente']++;
                });
                ocultarSpinner('spinnerStock');
                const ctx = document.getElementById('graficoStock').getContext('2d');
                chartStock = new Chart(ctx, {
                    type: 'doughnut',
                    data: {
                        labels: Object.keys(conteo),
                        datasets: [{
                            data: Object.values(conteo),
                            backgroundColor: [COLORES.critico, COLORES.suficiente, COLORES.exceso],
                            borderWidth: 2,
                            borderColor: '#fff',
                        }]
                    },
                    options: {
                        responsive: true,
                        cutout: '68%',
                        plugins: { legend: { display: false } }
                    }
                });
                const coloresLeyenda = [COLORES.critico, COLORES.suficiente, COLORES.exceso];
                document.getElementById('leyendaStock').innerHTML = Object.entries(conteo)
                    .map(([label, val], i) =>
                        `<span>
                                        <span style="display:inline-block;width:10px;height:10px;border-radius:50%;background:${coloresLeyenda[i]};margin-right:4px;"></span>
                                        ${label}: <strong>${val}</strong>
                                    </span>`)
                    .join('');
            })
            .catch(() => {
                ocultarSpinner('spinnerStock');
                mostrarError('errorStock');
            });

        // 1.2 Entradas vs Salidas
        const ahora = new Date();
        const mesActual = ahora.getMonth();
        const anioActual = ahora.getFullYear();
        const dias = diasDelMesActual();
        document.getElementById('labelMesMovimientos').textContent = nombreMesActual();

        Promise.all([
            fetch('/api/movimiento_prod/signo/1').then(r => r.ok ? r.json() : Promise.reject()),
            fetch('/api/movimiento_prod/signo/-1').then(r => r.ok ? r.json() : Promise.reject())
        ])
            .then(([entradas, salidas]) => {
                function agruparPorDia(movs) {
                    const mapa = {};
                    dias.forEach(d => mapa[d] = 0);
                    movs.forEach(m => {
                        const fecha = new Date(m.fechaMovimiento);
                        if (fecha.getMonth() === mesActual && fecha.getFullYear() === anioActual) {
                            const dia = fecha.getDate();
                            mapa[dia] = (mapa[dia] || 0) + m.cantidad;
                        }
                    });
                    return dias.map(d => mapa[d] || 0);
                }
                ocultarSpinner('spinnerMovimientos');
                const ctx = document.getElementById('graficoMovimientos').getContext('2d');
                chartMovimientos = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: dias.map(d => String(d)),
                        datasets: [{
                            label: 'Entradas',
                            data: agruparPorDia(entradas),
                            backgroundColor: 'rgba(13, 110, 253, 0.7)',
                            borderColor: '#0d6efd',
                            borderWidth: 1,
                            borderRadius: 3,
                        }, {
                            label: 'Salidas',
                            data: agruparPorDia(salidas),
                            backgroundColor: 'rgba(220, 53, 69, 0.7)',
                            borderColor: '#dc3545',
                            borderWidth: 1,
                            borderRadius: 3,
                        }]
                    },
                    options: {
                        responsive: true,
                        plugins: { legend: { position: 'top' } },
                        scales: {
                            x: { grid: { display: false }, ticks: { maxTicksLimit: 15, font: { size: 10 } } },
                            y: { beginAtZero: true, ticks: { precision: 0 } }
                        }
                    }
                });
            })
            .catch(() => {
                ocultarSpinner('spinnerMovimientos');
                mostrarError('errorMovimientos');
            });
    }

    // ══════════════════════════════════════════════════════════════
    // 2. CARGA TAB VENTAS
    // ══════════════════════════════════════════════════════════════
    function loadVentas() {
        if (loadedVentas) return;
        loadedVentas = true;

        // 2.1 Ventas 30 días (Line)
        fetch('/api/ventas')
            .then(r => r.ok ? r.json() : Promise.reject())
            .then(ventas => {
                const hace30 = new Date();
                hace30.setDate(hace30.getDate() - 29);
                hace30.setHours(0, 0, 0, 0);

                const etiquetas = [];
                const mapaDias = {};
                for (let i = 29; i >= 0; i--) {
                    const d = new Date();
                    d.setDate(d.getDate() - i);
                    const key = `${d.getFullYear()}-${d.getMonth()}-${d.getDate()}`;
                    const label = d.toLocaleDateString('es-CO', { day: '2-digit', month: '2-digit' });
                    etiquetas.push(label);
                    mapaDias[key] = 0;
                }

                ventas.forEach(v => {
                    const fecha = new Date(v.fechaVenta);
                    if (fecha >= hace30) {
                        const key = `${fecha.getFullYear()}-${fecha.getMonth()}-${fecha.getDate()}`;
                        if (mapaDias[key] !== undefined) {
                            mapaDias[key] += parseFloat(v.totalVenta || 0);
                        }
                    }
                });

                ocultarSpinner('spinnerVentas');
                const ctx = document.getElementById('graficoVentas').getContext('2d');
                chartVentas = new Chart(ctx, {
                    type: 'line',
                    data: {
                        labels: etiquetas,
                        datasets: [{
                            label: 'Ventas (COP)',
                            data: Object.values(mapaDias),
                            borderColor: COLORES.ventaLinea,
                            backgroundColor: COLORES.ventaRelleno,
                            borderWidth: 2,
                            fill: true,
                            tension: 0.3,
                            pointRadius: 3,
                            pointHoverRadius: 6,
                        }]
                    },
                    options: {
                        responsive: true,
                        plugins: { legend: { display: false } },
                        scales: {
                            x: { grid: { display: false }, ticks: { maxTicksLimit: 10, font: { size: 10 } } },
                            y: {
                                beginAtZero: true,
                                ticks: {
                                    callback: val =>
                                        val >= 1_000_000 ? `$${(val / 1_000_000).toFixed(1)}M` :
                                            val >= 1_000 ? `$${(val / 1_000).toFixed(0)}K` : `$${val}`
                                }
                            }
                        }
                    }
                });
            })
            .catch(() => {
                ocultarSpinner('spinnerVentas');
                mostrarError('errorVentas');
            });

        // 2.2 Top 5 más vendidos (Horizontal Bar)
        fetch('/api/productos')
            .then(r => r.ok ? r.json() : Promise.reject())
            .then(productos => {
                const top = productos
                    .sort((a, b) => (b.stock || 0) - (a.stock || 0))
                    .slice(0, 5);

                if (top.length === 0) {
                    ocultarSpinner('spinnerTop');
                    document.getElementById('sinTop').style.display = '';
                    return;
                }

                ocultarSpinner('spinnerTop');
                const ctx = document.getElementById('graficoTop').getContext('2d');
                chartTop = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: top.map(p => p.nombreProducto),
                        datasets: [{
                            label: 'Stock actual',
                            data: top.map(p => p.stock),
                            backgroundColor: 'rgba(255, 193, 7, 0.7)',
                            borderColor: '#ffc107',
                            borderWidth: 1,
                        }]
                    },
                    options: {
                        indexAxis: 'y',
                        responsive: true,
                        plugins: {
                            legend: { display: false },
                            tooltip: { callbacks: { label: ctx => ` ${ctx.raw} ud` } }
                        },
                        scales: {
                            x: { beginAtZero: true, ticks: { precision: 0 } },
                            y: { grid: { display: false } }
                        }
                    }
                });
                document.querySelector('#content-ventas .card-header .fa-trophy').parentElement.innerHTML =
                    `<i class="fa-solid fa-trophy me-2 text-warning"></i>Top 5 productos en stock (demo)`;
            })
            .catch(() => {
                ocultarSpinner('spinnerTop');
                mostrarError('errorTop');
            });
    }

    // ══════════════════════════════════════════════════════════════
    // 3. CARGA TAB ALERTAS
    // ══════════════════════════════════════════════════════════════
    function loadAlertas() {
        if (loadedAlertas) return;
        loadedAlertas = true;

        fetch('/api/alertas')
            .then(r => r.ok ? r.json() : Promise.reject())
            .then(alertas => {
                ocultarSpinner('spinnerAlertas');

                if (!alertas || alertas.length === 0) {
                    document.getElementById('sinAlertas').style.display = '';
                    document.getElementById('resumenAlertasTexto').innerHTML =
                        '<i class="fa-solid fa-circle-check text-success me-1"></i> Sin alertas activas. Todo en orden.';
                    return;
                }

                const conteo = { 'STOCK_BAJO': 0, 'PROXIMO_A_VENCER': 0, 'PRODUCTO_VENCIDO': 0 };
                alertas.forEach(a => {
                    if (conteo[a.tipoAlerta] !== undefined) conteo[a.tipoAlerta]++;
                });

                const etiquetas = {
                    'STOCK_BAJO': 'Stock bajo',
                    'PROXIMO_A_VENCER': 'Próx. a vencer',
                    'PRODUCTO_VENCIDO': 'Vencido',
                };
                const coloresAlertas = [COLORES.stockBajo, COLORES.proximoVencer, COLORES.vencido];

                const ctx = document.getElementById('graficoAlertas').getContext('2d');
                chartAlertas = new Chart(ctx, {
                    type: 'doughnut',
                    data: {
                        labels: Object.keys(conteo).map(k => etiquetas[k]),
                        datasets: [{
                            data: Object.values(conteo),
                            backgroundColor: coloresAlertas,
                            borderWidth: 2,
                            borderColor: '#fff',
                        }]
                    },
                    options: {
                        responsive: true,
                        cutout: '65%',
                        plugins: { legend: { display: false } }
                    }
                });

                // Leyenda
                document.getElementById('leyendaAlertas').innerHTML = Object.entries(conteo)
                    .map(([key, val], i) =>
                        `<div class="d-flex justify-content-between align-items-center py-1 border-bottom">
                                        <span>
                                            <span style="display:inline-block;width:10px;height:10px;border-radius:50%;background:${coloresAlertas[i]};margin-right:6px;"></span>
                                            ${etiquetas[key]}
                                        </span>
                                        <strong>${val}</strong>
                                    </div>`)
                    .join('');

                // Resumen textual
                document.getElementById('alertasDetalle').style.display = '';
                document.getElementById('detalleStockBajo').textContent = `Stock bajo: ${conteo['STOCK_BAJO'] || 0}`;
                document.getElementById('detalleProximoVencer').textContent =
                    `Próximos a vencer: ${conteo['PROXIMO_A_VENCER'] || 0}`;
                document.getElementById('detalleVencidos').textContent = `Vencidos: ${conteo['PRODUCTO_VENCIDO'] || 0}`;
                document.getElementById('resumenAlertasTexto').style.display = 'none';
            })
            .catch(() => {
                ocultarSpinner('spinnerAlertas');
                mostrarError('errorAlertas');
            });
    }

    // ══════════════════════════════════════════════════════════════
    // 4. EVENTOS DE TABS (Lazy Loading)
    // ══════════════════════════════════════════════════════════════
    document.addEventListener('DOMContentLoaded', function () {

        loadInventario();

        document.querySelectorAll('[data-bs-toggle="tab"]').forEach(tab => {
            tab.addEventListener('shown.bs.tab', function (e) {
                const targetId = this.getAttribute('data-bs-target');
                switch (targetId) {
                    case '#content-inventario':
                        loadInventario();
                        break;
                    case '#content-ventas':
                        loadVentas();
                        break;
                    case '#content-alertas':
                        loadAlertas();
                        break;
                }
            });
        });

    });

})();