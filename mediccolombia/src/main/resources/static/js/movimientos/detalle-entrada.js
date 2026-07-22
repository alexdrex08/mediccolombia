// =============================================
// AJAX — Cargar información del pedido origen
// =============================================
(async function cargarPedidoOrigen() {
    const infoPedido = document.getElementById('infoPedido');
    const picker = infoPedido.dataset.picker;

    // Si no hay pickerChecker o no tiene formato PRES, mostramos mensaje
    if (!picker || !picker.startsWith('PRES')) {
        document.getElementById('pedidoCargando').style.display = 'none';
        document.getElementById('pedidoNoDisponible').style.display = 'block';
        return;
    }
    const match = picker.match(/^PRES(\d+)P/);
    if (!match) {
        document.getElementById('pedidoCargando').style.display = 'none';
        document.getElementById('pedidoNoDisponible').style.display = 'block';
        return;
    }

    const idPedido = match[1];

    try {
        // Llamando al RestController existente: GET /api/pedidos/{id}
        const res = await fetch(`/api/pedidos/${idPedido}`);
        if (!res.ok) throw new Error('Pedido no encontrado');
        const pedido = await res.json();

        document.getElementById('pedidoId').textContent = '#' + pedido.id;
        document.getElementById('pedidoProveedor').textContent = pedido.nombreProveedor;
        document.getElementById('pedidoEstado').textContent = pedido.estadoPedido;
        document.getElementById('pedidoTotal').textContent =
            '$ ' + Number(pedido.totalPedido).toLocaleString('es-CO');

        document.getElementById('pedidoCargando').style.display = 'none';
        document.getElementById('pedidoContenido').style.display = 'flex';

    } catch (e) {
        document.getElementById('pedidoCargando').style.display = 'none';
        document.getElementById('pedidoNoDisponible').style.display = 'block';
        console.error('Error cargando pedido origen:', e);
    }
})();