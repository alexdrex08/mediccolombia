// =============================================
// BLOQUE 7: INICIALIZACIÓN Y EVENTOS
// =============================================

// Validación antes de enviar el formulario
document.getElementById('formPedido').addEventListener('submit', function (e) {
    const selectProv = document.getElementById('selectProveedor');
    
    // Si el proveedor fue fijado desde el Tab 2, asignarlo al select
    if (!selectProv.value && pedido.proveedor) {
        selectProv.value = pedido.proveedor.id;
    }
    
    // Validaciones finales
    if (!selectProv.value) {
        e.preventDefault();
        alert('No hay proveedor asignado. Agrega productos primero.');
        return;
    }
    if (pedido.items.length === 0) {
        e.preventDefault();
        alert('Agrega al menos un producto al pedido.');
        return;
    }
});

// Inicializar la UI al cargar la página
document.addEventListener('DOMContentLoaded', function () {
    sincronizarUI();
});