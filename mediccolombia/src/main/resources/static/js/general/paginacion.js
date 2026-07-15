/**
 * Inicializa paginación y búsqueda con múltiples filtros.
 * @param {Object} opciones
 * @param {string} opciones.tablaId - ID del tbody.
 * @param {string} opciones.paginacionId - ID del contenedor <ul> de paginación.
 * @param {string} [opciones.inputId] - ID del input de búsqueda (opcional).
 * @param {Array<{id: string, attr: string}>} [opciones.filtrosSelect] - Lista de selects con su atributo data.
 * @param {string} [opciones.btnLimpiarId] - ID del botón limpiar (opcional).
 * @param {string[]} [opciones.inputsLimpiar] - IDs de inputs adicionales a limpiar (ej: fechas).
 * @param {string[]} [opciones.inputsAdicional] - IDs de inputs que deben disparar el filtro al cambiar.
 * @param {number} [opciones.filasPorPagina=5] - Filas por página.
 * @param {Function} [opciones.filtroAdicional] - Función extra que recibe la fila y retorna boolean.
 * @returns {{ render: Function, reiniciar: Function }}
 */
function inicializarPaginacionYBusqueda(opciones) {
    const {
        tablaId,
        paginacionId,
        inputId = null,
        filtrosSelect = [],
        btnLimpiarId = null,
        inputsLimpiar = [],
        inputsAdicional = [],    
        filasPorPagina = 5,
        filtroAdicional = null
    } = opciones;

    const tabla = document.getElementById(tablaId);
    if (!tabla) return { render: () => { }, reiniciar: () => { } };

    // Filas que tienen data-nombre
    let filas = Array.from(tabla.querySelectorAll('tr'))
        .filter(f => !f.querySelector('td[colspan]'));

    if (filas.length === 0) return { render: () => { }, reiniciar: () => { } };

    const contenedorPaginacion = document.getElementById(paginacionId);
    if (!contenedorPaginacion) return { render: () => { }, reiniciar: () => { } };

    const input = inputId ? document.getElementById(inputId) : null;
    const btnLimpiar = btnLimpiarId ? document.getElementById(btnLimpiarId) : null;

    // Obtener referencias a los selects
    const selects = filtrosSelect.map(f => ({
        el: document.getElementById(f.id),
        attr: f.attr
    })).filter(s => s.el !== null);

    let paginaActual = 1;

    // ── Función de filtrado ──
    function filasFiltradas() {
        const texto = input ? input.value.trim().toLowerCase() : '';

        return filas.filter(fila => {
            // Filtro por texto (data-nombre)
            const nombre = fila.dataset.nombre || '';
            const coincideTexto = nombre.includes(texto);

            // Filtros por select 
            let coincideSelects = true;
            for (const s of selects) {
                const valorSelect = s.el.value;
                if (valorSelect === '') continue;
                const valorFila = fila.dataset[s.attr] || '';
                if (valorFila !== valorSelect) {
                    coincideSelects = false;
                    break;
                }
            }

            // Filtro adicional personalizado
            let coincideExtra = true;
            if (filtroAdicional) {
                coincideExtra = filtroAdicional(fila);
            }

            return coincideTexto && coincideSelects && coincideExtra;
        });
    }

    // ── Renderiza tabla y paginación ──
    function render() {
        const visibles = filasFiltradas();
        const totalPaginas = Math.max(1, Math.ceil(visibles.length / filasPorPagina));
        if (paginaActual > totalPaginas) paginaActual = totalPaginas;

        filas.forEach(f => f.style.display = 'none');
        const inicio = (paginaActual - 1) * filasPorPagina;
        const fin = Math.min(inicio + filasPorPagina, visibles.length);
        for (let i = inicio; i < fin; i++) {
            visibles[i].style.display = '';
        }

        renderPaginacion(totalPaginas, visibles.length);
    }

    // ── Construyir botones de paginación ──
    function renderPaginacion(totalPaginas, totalFilas) {
        contenedorPaginacion.innerHTML = '';

        if (totalFilas === 0) {
            contenedorPaginacion.innerHTML =
                '<li class="page-item disabled"><span class="page-link">Sin resultados</span></li>';
            return;
        }

        const crearItem = (texto, pagina, deshabilitado, activo) => {
            const li = document.createElement('li');
            li.className = 'page-item' + (deshabilitado ? ' disabled' : '') + (activo ? ' active' : '');
            const a = document.createElement('a');
            a.className = 'page-link';
            a.href = '#';
            a.textContent = texto;
            a.addEventListener('click', (e) => {
                e.preventDefault();
                if (!deshabilitado) {
                    paginaActual = pagina;
                    render();
                }
            });
            li.appendChild(a);
            return li;
        };

        contenedorPaginacion.appendChild(crearItem('Anterior', paginaActual - 1, paginaActual === 1, false));

        const maxVisibles = 5;
        let inicioPaginacion = Math.max(1, paginaActual - Math.floor(maxVisibles / 2));
        let finPaginacion = Math.min(totalPaginas, inicioPaginacion + maxVisibles - 1);
        if (finPaginacion - inicioPaginacion + 1 < maxVisibles) {
            inicioPaginacion = Math.max(1, finPaginacion - maxVisibles + 1);
        }

        if (inicioPaginacion > 1) {
            contenedorPaginacion.appendChild(crearItem('1', 1, false, false));
            if (inicioPaginacion > 2) {
                const liPuntos = document.createElement('li');
                liPuntos.className = 'page-item disabled';
                liPuntos.innerHTML = '<span class="page-link">…</span>';
                contenedorPaginacion.appendChild(liPuntos);
            }
        }

        for (let i = inicioPaginacion; i <= finPaginacion; i++) {
            contenedorPaginacion.appendChild(crearItem(String(i), i, false, i === paginaActual));
        }

        if (finPaginacion < totalPaginas) {
            if (finPaginacion < totalPaginas - 1) {
                const liPuntos = document.createElement('li');
                liPuntos.className = 'page-item disabled';
                liPuntos.innerHTML = '<span class="page-link">…</span>';
                contenedorPaginacion.appendChild(liPuntos);
            }
            contenedorPaginacion.appendChild(crearItem(String(totalPaginas), totalPaginas, false, false));
        }

        contenedorPaginacion.appendChild(crearItem('Siguiente', paginaActual + 1, paginaActual === totalPaginas, false));
    }

    // ── Reiniciar a página 1 y renderizar ──
    function reiniciar() {
        paginaActual = 1;
        render();
    }

    // ── Event listeners ──
    if (input) {
        input.addEventListener('input', reiniciar);
    }
    for (const s of selects) {
        s.el.addEventListener('change', reiniciar);
    }

    //Event listeners para inputs externos que disparen el filtro
    for (const idInput of inputsAdicional) {
        const el = document.getElementById(idInput);
        if (el) {
            el.addEventListener('change', reiniciar);
        }
    }

    if (btnLimpiar) {
        btnLimpiar.addEventListener('click', () => {
            if (input) input.value = '';
            for (const s of selects) {
                s.el.value = '';
            }
            // Limpiar inputsLimpiar
            for (const idInput of inputsLimpiar) {
                const el = document.getElementById(idInput);
                if (el) el.value = '';
            }
            // Limpiar también inputsAdicional
            for (const idInput of inputsAdicional) {
                const el = document.getElementById(idInput);
                if (el) el.value = '';
            }
            reiniciar();
        });
    }

    // ── Render inicial ──
    render();

    // ── Devolver API pública ──
    return {
        render,
        reiniciar
    };
}