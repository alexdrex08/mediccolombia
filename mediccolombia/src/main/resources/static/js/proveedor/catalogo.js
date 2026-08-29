// =============================================
// PRODUCTOS — Agregar al catálogo del proveedor
// =============================================
async function agregarProducto(idProducto) {
    const precioInput = document.getElementById(`precio-${idProducto}`);
    const precio = parseFloat(precioInput.value);

    if (!precio || precio <= 0) {
        mostrarMensaje('Ingresa un precio unitario válido.', 'danger');
        precioInput.focus();
        return;
    }

    try {
        await postAPI('/api/detalle-proveedor-producto', {
            idProveedor: PROVEEDOR_ID,
            idProducto: idProducto,
            precioUnitario: precio
        });
        cerrarModal('modalAgregarProducto');
        mostrarMensaje('Producto agregado al catálogo correctamente.');
        setTimeout(() => location.reload(), 800);
    } catch (e) {
        mostrarMensaje('Error al agregar el producto: ' + e.message, 'danger');
    }
}

function quitarProducto(idProveedor, idProducto) {
    mostrarModalConfirmacion(
        'Quitar producto del catálogo',
        '¿Estás seguro de que deseas quitar este producto del catálogo del proveedor? Esta acción no se puede deshacer.',
        'danger',
        async function () {
            try {
                await deleteAPI(`/api/detalle-proveedor-producto/${idProveedor}/${idProducto}`);
                mostrarMensaje('Producto removido del catálogo.');
                setTimeout(() => location.reload(), 800);
            } catch (e) {
                mostrarMensaje('Error al quitar el producto.', 'danger');
            }
        },
        'Sí, quitar'
    );
}

function filtrarProductosModal(texto) {
    const filas = document.querySelectorAll('#tablaProductosDisponibles tbody tr[data-nombre]');
    filas.forEach(row => {
        const nombre = (row.dataset.nombre || '').toLowerCase();
        row.style.display = nombre.includes(texto.toLowerCase()) ? '' : 'none';
    });
}