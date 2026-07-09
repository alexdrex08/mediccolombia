// =============================================
// BLOQUE 6: SINCRONIZACIÓN CENTRAL DE UI
// =============================================

// Mantiene TODA la UI consistente con el estado del pedido
// Esta es la función más importante para la consistencia
function sincronizarUI() {
    // 1. Badge del proveedor (header y resumen)
    actualizarBadgeProveedor();

    // 2. Visibilidad del botón vaciar
    actualizarVisibilidadBotonesCarrito();

    // 3. Sincronizar el select del Tab 1
    const selectA = document.getElementById('selectProveedor');
    if (pedido.proveedor) {
        selectA.value = pedido.proveedor.id;
        cargarCatalogoProveedor(pedido.proveedor.id);
    } else {
        selectA.value = '';
        document.getElementById('catalogoProveedor').style.display = 'none';
        document.getElementById('msgSeleccionaProveedor').style.display = 'block';
        document.getElementById('cargandoCatalogo').classList.add('d-none');
    }

    // 4. Sincronizar el badge del Tab 2
    if (pedido.items.length === 0) {
        limpiarBadgeTab2();
        document.getElementById('tablaComparacionWrapper').style.display = 'none';
        document.getElementById('sinProveedoresMsg').classList.add('d-none');
        document.getElementById('msgBuscaArticulo').style.display = 'block';
    }

    // 5. Ocultar todas las alertas de bloqueo
    document.getElementById('alertaProveedorBloqueadoA').classList.add('d-none');
    document.getElementById('alertaProveedorBloqueadoB').classList.add('d-none');
}