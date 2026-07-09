// =============================================
// BLOQUE 3: TAB 1 — POR PROVEEDOR
// =============================================

// Se ejecuta al seleccionar un proveedor en el dropdown
// Carga el catálogo o muestra bloqueo si hay conflicto
function onSelectProveedor(idProveedor, textoOption) {
    document.getElementById('alertaProveedorBloqueadoA').classList.add('d-none');

    if (!idProveedor) {
        document.getElementById('catalogoProveedor').style.display = 'none';
        document.getElementById('msgSeleccionaProveedor').style.display = 'block';
        return;
    }

    // Si el carrito ya tiene un proveedor distinto → bloquear
    if (pedido.proveedor !== null && pedido.proveedor.id !== parseInt(idProveedor)) {
        document.getElementById('provBloqueadoNombreA').textContent = pedido.proveedor.nombre;
        document.getElementById('alertaProveedorBloqueadoA').classList.remove('d-none');
        sincronizarUI(); // Revierte el select al proveedor activo
        return;
    }

    cargarCatalogoProveedor(idProveedor);
}

// Carga el catálogo de productos del proveedor seleccionado
// Hace fetch a la API y renderiza la tabla
async function cargarCatalogoProveedor(idProveedor) {
    document.getElementById('msgSeleccionaProveedor').style.display = 'none';
    document.getElementById('catalogoProveedor').style.display = 'block';
    document.getElementById('cargandoCatalogo').classList.remove('d-none');
    catalogoActual = {};

    try {
        const res = await fetch(
            `/api/detalle-proveedor-producto/proveedor/${idProveedor}`,
            { headers: { 'Accept': 'application/json' } }
        );
        if (!res.ok) throw new Error();
        const lista = await res.json();

        // Guardar en cache
        lista.forEach(item => {
            catalogoActual[item.idProducto] = {
                nombre: item.nombreProducto,
                precio: parseFloat(item.precioUnitario),
                idProveedor: item.idProveedor,
                nombreProveedor: item.nombreProveedor
            };
        });

        // Renderizar tabla
        const tbody = document.getElementById('cuerpoTablaCatalogo');
        if (lista.length === 0) {
            tbody.innerHTML = `<tr><td colspan="4" class="text-center text-muted py-3">
                <i class="fa-solid fa-box-open me-2"></i>
                Este proveedor no tiene productos en su catálogo aún.
            </td></tr>`;
        } else {
            tbody.innerHTML = lista.map(item => `
            <tr>
                <td class="fw-bold">${item.nombreProducto}</td>
                <td class="text-end text-success fw-bold">$ ${fmt(item.precioUnitario)}</td>
                <td class="text-center">
                    <input type="number" id="cat-cant-${item.idProducto}"
                           class="form-control form-control-sm text-center"
                           min="1" value="1" style="width:75px;">
                </td>
                <td class="text-center">
                    <button type="button" class="btn btn-sm btn-outline-success"
                            onclick="agregarDesdeCatalogo(
                                ${item.idProducto},
                                '${item.nombreProducto.replace(/'/g, "\\'")}',
                                ${parseFloat(item.precioUnitario)},
                                ${item.idProveedor},
                                '${item.nombreProveedor.replace(/'/g, "\\'")}')">
                        <i class="fa-solid fa-plus"></i>
                    </button>
                </td>
            </tr>`).join('');
        }
    } catch (e) {
        document.getElementById('cuerpoTablaCatalogo').innerHTML =
            `<tr><td colspan="4" class="text-center text-muted py-3">
                Error cargando el catálogo.
            </td></tr>`;
    } finally {
        document.getElementById('cargandoCatalogo').classList.add('d-none');
    }
}

// Agrega un producto desde el catálogo del Tab 1
// Valida proveedor, toma cantidad del input y llama a agregarAlCarrito()
function agregarDesdeCatalogo(idProducto, nombreProducto, precio,
    idProveedor, nombreProveedor) {
    document.getElementById('alertaProveedorBloqueadoA').classList.add('d-none');

    if (!validarProveedor(idProveedor, nombreProveedor, 'A')) return;

    const cantInput = document.getElementById(`cat-cant-${idProducto}`);
    const cantidad = parseInt(cantInput.value);
    if (!cantidad || cantidad < 1) { 
        alert('Cantidad debe ser mayor a 0.'); 
        return; 
    }

    agregarAlCarrito(idProducto, nombreProducto, cantidad, precio, idProveedor, nombreProveedor);
    cantInput.value = 1; // Resetear a 1 después de agregar
}

// Función vacía para el onchange del tab
function limpiarBusquedaProveedor() {
    // No hace nada porque cambiar de tab no debe limpiar el carrito
}