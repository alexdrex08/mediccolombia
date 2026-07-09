async function registrarVenta() {
    if (!venta.medioPago) { mostrarMensaje("Sistema", 'Selecciona un medio de pago.'); return; }
    const body = {
        idCliente: venta.cliente.id,
        idUsuario: ID_USUARIO_ACTUAL,
        medioPago: venta.medioPago,
        detalles: venta.items.map(i => ({
            idProducto: i.idProducto,
            cantidad: i.cantidad,
            precioUnitario: i.precioUnitario
        }))
    };

    try {
        const res = await fetch('/api/ventas', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });
        if (!res.ok) {
            const err = await res.json().catch(() => ({}));
            mostrarMensaje("Sistema", 'Error al registrar la venta: ' + (err.message ?? res.status));
            return;
        }
        // VentaRegistroResponseDTO: { id, fechaVenta, nombreCliente, nombreUsuario, totalVenta, detalles }
        const ventaGuardada = await res.json();
        // Redirigir al detalle de la venta recién creada
        window.location.href = '/ventas/' + ventaGuardada.id;
    } catch (e) {
        mostrarMensaje("Sistema", 'Error de conexión al registrar la venta.');
        console.error(e);
    }
}

function limpiarTodo() {
    const modal = new bootstrap.Modal(
        document.getElementById('modalCancelarVenta')
    );

    modal.show();
}

function confirmarCancelarVenta() {
    venta.items = [];
    venta.cliente = null;
    venta.medioPago = null;
    productoActual = null;

    renderCarrito();
    limpiarBuscador();
    limpiarCliente();
    

    document.getElementById("inputCantidad").value = 1;
    document.getElementById("inputPrecio").value = "";

    document.getElementById('seccionMedioPago').classList.add('d-none');
    document.getElementById('btnConfirmarVenta').classList.remove('d-none');

    document.getElementById("productoSeleccionado")
        .classList.add("d-none");

    bootstrap.Modal.getInstance(
        document.getElementById("modalCancelarVenta")
    ).hide();
}