document.addEventListener('DOMContentLoaded', function () {
    // ==========================================
    // FUNCIÓN PARA FILTRAR POR TEXTO
    // ==========================================
    function inicializarFiltro(inputId, tablaId, paginacionId) {
        const input = document.getElementById(inputId);
        const tabla = document.getElementById(tablaId);
        if (!input || !tabla) return;

        let paginacionControl = null;

        // Buscar la paginación asociada
        const paginacion = document.getElementById(paginacionId);
        if (paginacion) {

            const key = 'paginacion_' + tablaId;
            paginacionControl = window[key];
        }

        input.addEventListener('input', function () {
            const texto = this.value.toLowerCase().trim();
            const filas = tabla.querySelectorAll('tr');

            // Mostrar/ocultar según el filtro
            filas.forEach(fila => {
                const nombre = fila.dataset.nombre || '';
                fila.style.display = nombre.includes(texto) ? '' : 'none';
            });


            if (paginacionControl) {
                location.reload();
            }
        });
    }

    // Filtros del historial
    const inputBuscar = document.getElementById('buscarHistorial');
    const selectTipo = document.getElementById('filtroTipo');
    const btnLimpiar = document.getElementById('limpiarFiltros');

    if (inputBuscar) {
        inputBuscar.addEventListener('input', function () {
            const texto = this.value.toLowerCase().trim();
            const tipo = selectTipo ? selectTipo.value : '';
            const filas = document.querySelectorAll('#tablaHistorial tr');

            filas.forEach(fila => {
                const nombre = fila.dataset.nombre || '';
                const tipoFila = fila.dataset.tipo || '';
                const coincideTexto = texto === '' || nombre.includes(texto);
                const coincideTipo = tipo === '' || tipoFila === tipo;
                fila.style.display = (coincideTexto && coincideTipo) ? '' : 'none';
            });

            // Reiniciar paginación
            if (historialPaginacion) {
                location.reload();
            }
        });
    }

    if (selectTipo) {
        selectTipo.addEventListener('change', function () {
            inputBuscar.dispatchEvent(new Event('input'));
        });
    }

    if (btnLimpiar) {
        btnLimpiar.addEventListener('click', function () {
            if (inputBuscar) inputBuscar.value = '';
            if (selectTipo) selectTipo.value = '';
            inputBuscar.dispatchEvent(new Event('input'));
        });
    }
});