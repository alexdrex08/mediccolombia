// =============================================
// AJAX — Cargar origen de la salida
//
// Flujo:
// 1. Leer pickerChecker del data-attribute
// 2. Si empieza con "VENTA-" → parsear id, llamar GET /api/ventas/{id}
//    y mostrar bloque de venta con cliente y detalles
// 3. Si no → mostrar bloque genérico con tipo y motivo del movimiento
// =============================================
(async function cargarOrigenSalida() {
    const bloque = document.getElementById('bloqueOrigen');
    const picker = bloque.dataset.picker;
    const datosExtra = document.getElementById('datosMovimiento');
    const tipoMov = datosExtra.dataset.tipo || '';
    const motivoMov = datosExtra.dataset.motivo || 'Sin descripción registrada';

    const elCargando = document.getElementById('origenCargando');
    const elVenta = document.getElementById('origenVenta');
    const elOtro = document.getElementById('origenOtro');

    if (picker && picker.startsWith('VENTA-')) {
        const idVenta = picker.replace('VENTA-', '').trim();

        try {
            const res = await fetch(`/api/ventas/${idVenta}`);
            if (!res.ok) throw new Error('Venta no encontrada');
            const venta = await res.json();

            // Poblar encabezado
            document.getElementById('ventaIdLabel').textContent = '#' + venta.id;
            document.getElementById('ventaId').textContent = '#' + venta.id;
            document.getElementById('ventaCliente').textContent = venta.nombreCliente ?? '—';
            document.getElementById('ventaTotal').textContent =
                '$ ' + Number(venta.totalVenta).toLocaleString('es-CO');
            if (venta.fechaVenta) {
                const fecha = new Date(Array.isArray(venta.fechaVenta)
                    ? venta.fechaVenta[0] + '-' +
                    String(venta.fechaVenta[1]).padStart(2, '0') + '-' +
                    String(venta.fechaVenta[2]).padStart(2, '0') + 'T' +
                    String(venta.fechaVenta[3] ?? 0).padStart(2, '0') + ':' +
                    String(venta.fechaVenta[4] ?? 0).padStart(2, '0')
                    : venta.fechaVenta);
                document.getElementById('ventaFecha').textContent =
                    fecha.toLocaleDateString('es-CO', { day: '2-digit', month: '2-digit', year: 'numeric' })
                    + ' ' + fecha.toLocaleTimeString('es-CO', { hour: '2-digit', minute: '2-digit' });
            }

            document.getElementById('linkVerVenta').href = '/ventas/' + venta.id;
            const tbodyDetalles = document.getElementById('ventaDetalles');
            if (venta.detalles && venta.detalles.length > 0) {
                tbodyDetalles.innerHTML = venta.detalles.map(d => `
                            <tr>
                                <td>${d.nombreProducto ?? '—'}</td>
                                <td class="text-center">${d.cantidad}</td>
                                <td class="text-end">$ ${Number(d.precioUnitario).toLocaleString('es-CO')}</td>
                                <td class="text-end fw-bold">$ ${Number(d.subtotal).toLocaleString('es-CO')}</td>
                            </tr>
                        `).join('');
            } else {
                tbodyDetalles.innerHTML = `
                            <tr><td colspan="4" class="text-center text-muted py-2">
                                Sin detalles disponibles
                            </td></tr>`;
            }

            elCargando.style.display = 'none';
            elVenta.style.display = 'block';

        } catch (e) {
            console.error('Error cargando venta origen:', e);
            mostrarBloqueGenerico(tipoMov, motivoMov);
        }

    } else {
        mostrarBloqueGenerico(tipoMov, motivoMov);
    }
})();

function mostrarBloqueGenerico(tipo, motivo) {
    document.getElementById('origenCargando').style.display = 'none';
    document.getElementById('origenTipoLabel').textContent = tipo || 'Salida de inventario';
    document.getElementById('origenMotivo').textContent = motivo || 'Sin descripción registrada';

    if (tipo && tipo.toLowerCase().includes('vencimiento')) {
        document.getElementById('notaVencimiento').style.display = 'block';
    }
    document.getElementById('origenOtro').style.display = 'block';
}