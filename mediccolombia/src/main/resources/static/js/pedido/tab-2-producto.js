// =============================================
// BLOQUE 4: TAB 2 — POR PRODUCTO (Comparar Proveedores)
// =============================================

// Referencias DOM para el buscador
const inputArticulo = document.getElementById('inputBuscarArticulo');
const resultadosArt = document.getElementById('resultadosBusquedaArticulo');

// Búsqueda con debounce (espera 300ms después de dejar de escribir)
let debounceArt;
inputArticulo.addEventListener('input', function () {
    clearTimeout(debounceArt);
    const q = this.value.trim();
    if (q.length < 2) { 
        resultadosArt.style.display = 'none'; 
        return; 
    }
    debounceArt = setTimeout(() => buscarArticulo(q), 300);
});

// Busca productos en la API por nombre
async function buscarArticulo(nombre) {
    try {
        const res = await fetch(
            `/api/productos/buscar?nombre=${encodeURIComponent(nombre)}`,
            { headers: { 'Accept': 'application/json' } }
        );
        if (!res.ok) return;
        const lista = await res.json();
        resultadosArt.innerHTML = '';
        
        if (lista.length === 0) {
            resultadosArt.innerHTML =
                '<div class="list-group-item text-muted small">Sin resultados</div>';
        } else {
            lista.forEach(p => {
                const btn = document.createElement('button');
                btn.type = 'button';
                btn.className = 'list-group-item list-group-item-action';
                btn.innerHTML = `
                    <div class="d-flex justify-content-between align-items-center">
                        <span class="fw-bold">${p.nombreProducto}</span>
                        <span class="badge bg-info text-dark">Stock: ${p.stock}</span>
                    </div>
                    <small class="text-muted">${p.lote ?? ''}</small>`;
                btn.onclick = () => seleccionarArticulo(p);
                resultadosArt.appendChild(btn);
            });
        }
        resultadosArt.style.display = 'block';
    } catch (e) { 
        console.error(e); 
    }
}

// Cuando se selecciona un producto de la lista
async function seleccionarArticulo(p) {
    articuloActual = p;
    inputArticulo.value = p.nombreProducto;
    resultadosArt.style.display = 'none';
    document.getElementById('nombreArticuloBadge').textContent = p.nombreProducto;
    document.getElementById('articuloBadge').classList.remove('d-none');
    document.getElementById('msgBuscaArticulo').style.display = 'none';
    await cargarComparacionProveedores(p.id, p.nombreProducto);
}

// Cerrar resultados al hacer clic fuera
document.addEventListener('click', e => {
    if (!inputArticulo.contains(e.target)) {
        resultadosArt.style.display = 'none';
    }
});

// Limpia la búsqueda y el badge del Tab 2
function limpiarBusquedaArticulo() {
    articuloActual = null;
    inputArticulo.value = '';
    limpiarBadgeTab2(); // Función del bloque 2
    document.getElementById('tablaComparacionWrapper').style.display = 'none';
    document.getElementById('sinProveedoresMsg').classList.add('d-none');
    document.getElementById('msgBuscaArticulo').style.display = 'block';
    document.getElementById('alertaProveedorBloqueadoB').classList.add('d-none');

    // Si el carrito está vacío, sincronizar todo (limpia select del Tab 1)
    if (pedido.items.length === 0) {
        sincronizarUI();
    }
}

// Carga la tabla comparativa de proveedores para el producto seleccionado
async function cargarComparacionProveedores(idProducto, nombreProducto) {
    document.getElementById('cargandoComparacion').classList.remove('d-none');
    document.getElementById('tablaComparacionWrapper').style.display = 'none';
    document.getElementById('sinProveedoresMsg').classList.add('d-none');
    document.getElementById('alertaProveedorBloqueadoB').classList.add('d-none');

    try {
        const res = await fetch(
            `/api/detalle-proveedor-producto/producto/${idProducto}`,
            { headers: { 'Accept': 'application/json' } }
        );
        if (!res.ok) throw new Error();
        const lista = await res.json();
        document.getElementById('cargandoComparacion').classList.add('d-none');

        if (!lista || lista.length === 0) {
            document.getElementById('sinProveedoresMsg').classList.remove('d-none');
            return;
        }

        // Ordenar por precio ascendente (mejor precio primero)
        lista.sort((a, b) => parseFloat(a.precioUnitario) - parseFloat(b.precioUnitario));
        const precioMin = parseFloat(lista[0].precioUnitario);

        document.getElementById('nombreArticuloComparacion').textContent = nombreProducto;

        const tbody = document.getElementById('cuerpoTablaComparacion');
        tbody.innerHTML = lista.map((item, idx) => {
            const precio = parseFloat(item.precioUnitario);
            const esMejor = precio === precioMin;
            const bloqueado = pedido.proveedor !== null &&
                pedido.proveedor.id !== item.idProveedor;
            return `
            <tr class="${bloqueado ? 'opacity-50' : ''}">
                <td>
                    <span class="fw-bold">${item.nombreProveedor}</span>
                    ${esMejor ? '<span class="badge bg-success ms-2 small">Mejor precio</span>' : ''}
                    ${bloqueado ? '<span class="badge bg-secondary ms-2 small">No disponible</span>' : ''}
                </td>
                <td class="text-end">
                    <span class="${esMejor ? 'fw-bold text-success' : ''}">
                        $ ${fmt(precio)}
                    </span>
                </td>
                <td class="text-center">
                    <input type="number" id="comp-cant-${idx}"
                           class="form-control form-control-sm text-center"
                           min="1" value="1" style="width:75px;"
                           ${bloqueado ? 'disabled' : ''}>
                </td>
                <td class="text-center">
                    <button type="button"
                            class="btn btn-sm ${bloqueado ? 'btn-secondary' : 'btn-outline-success'}"
                            ${bloqueado ? 'disabled title="Solo puedes agregar del proveedor activo"' : ''}
                            onclick="agregarDesdeComparacion(
                                ${item.idProveedor},
                                '${item.nombreProveedor.replace(/'/g, "\\'")}',
                                ${item.idProducto},
                                '${item.nombreProducto.replace(/'/g, "\\'")}',
                                ${precio}, ${idx})">
                        <i class="fa-solid fa-plus me-1"></i>Pedir
                    </button>
                </td>
            </tr>`;
        }).join('');

        document.getElementById('tablaComparacionWrapper').style.display = 'block';
    } catch (e) {
        document.getElementById('cargandoComparacion').classList.add('d-none');
        document.getElementById('sinProveedoresMsg').classList.remove('d-none');
    }
}

// Agrega un producto desde la tabla comparativa (Tab 2)
function agregarDesdeComparacion(idProveedor, nombreProveedor,
    idProducto, nombreProducto, precio, idx) {
    document.getElementById('alertaProveedorBloqueadoB').classList.add('d-none');

    if (!validarProveedor(idProveedor, nombreProveedor, 'B')) {
        cargarComparacionProveedores(articuloActual.id, articuloActual.nombreProducto);
        return;
    }

    const cantInput = document.getElementById(`comp-cant-${idx}`);
    const cantidad = parseInt(cantInput.value);
    if (!cantidad || cantidad < 1) { 
        alert('Cantidad debe ser mayor a 0.'); 
        return; 
    }

    agregarAlCarrito(idProducto, nombreProducto, cantidad, precio, idProveedor, nombreProveedor);
    cantInput.value = 1;

    // Recargar tabla para actualizar botones bloqueados
    cargarComparacionProveedores(articuloActual.id, articuloActual.nombreProducto);

    // Feedback visual: cambiar el botón a "Agregado" por 1.5 segundos
    const btn = cantInput.closest('tr').querySelector('button');
    if (btn) {
        const original = btn.innerHTML;
        btn.innerHTML = '<i class="fa-solid fa-check me-1"></i>Agregado';
        btn.classList.replace('btn-outline-success', 'btn-success');
        setTimeout(() => {
            btn.innerHTML = original;
            btn.classList.replace('btn-success', 'btn-outline-success');
        }, 1500);
    }
}