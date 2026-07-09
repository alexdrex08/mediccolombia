function seleccionarMedioPago(tipo) {
    venta.medioPago = tipo;
    // Limpiar selección visual anterior
    ['btnEfectivo', 'btnTarjeta'].forEach(id => {
        document.getElementById(id).classList.remove('btn-primary');
        document.getElementById(id).classList.add('btn-outline-secondary');
    });
    // Resaltar el seleccionado
    const seleccionado = tipo === 'EFECTIVO' ? 'btnEfectivo' : 'btnTarjeta';
    document.getElementById(seleccionado).classList.remove('btn-outline-secondary');
    document.getElementById(seleccionado).classList.add('btn-primary');
    // Mostrar botón de registrar
    document.getElementById('btnRegistrarVenta').classList.remove('d-none');
}

// =============================================
// MEDIO DE PAGO Y REGISTRO FINAL
// =============================================
function mostrarMedioPago() {
    if (venta.items.length === 0) { mostrarMensaje("Sistema", 'Agrega al menos un producto.'); return; }
    if (!venta.cliente) { mostrarMensaje("Sistema", 'Asigna un cliente antes de confirmar.'); return; }
    document.getElementById('seccionMedioPago').classList.remove('d-none');
    document.getElementById('btnConfirmarVenta').classList.add('d-none');
}