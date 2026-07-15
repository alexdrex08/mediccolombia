// =============================================
// ESTADO CENTRAL DE LA VENTA
// =============================================
const venta = {
    items: [],      // { idProducto, nombreProducto, cantidad, precioUnitario, subtotal }
    cliente: null,  // { id, nombreCliente, identificacion }
    medioPago: null // 'EFECTIVO' | 'TARJETA'
};

function fmt(num) {
    return Number(num).toLocaleString('es-CO');
}