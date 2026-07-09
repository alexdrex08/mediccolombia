function agregarAlCarrito() {
    if (!productoActual) { mostrarMensaje('Producto', 'Selecciona un producto de la búsqueda primero.'); return; }

    // ==========================================
    // NUEVA VALIDACIÓN: FECHA DE VENCIMIENTO
    // ==========================================
    if (productoActual.fechaExpiracion) {
        const fechaExpiracion = new Date(productoActual.fechaExpiracion);
        const fechaFormateada = fechaExpiracion.toLocaleDateString('es-CO', {
            day: '2-digit',
            month: 'long',
            year: 'numeric'
        });
        const fechaActual = new Date();
        fechaActual.setHours(0, 0, 0, 0);

        if (fechaExpiracion < fechaActual) {
            mostrarMensaje("Producto vencido", `No se puede facturar. El producto seleccionado venció el ${fechaFormateada}.`);
            limpiarBuscador();
            return;
        }
    }
    // ==========================================

    const cantidad = parseInt(document.getElementById('inputCantidad').value);
    const precio = parseFloat(document.getElementById('inputPrecio').value);

    if (!cantidad || cantidad < 1) { mostrarMensaje("Producto", 'La cantidad debe ser mayor a 0.'); return; }
    if (!precio || precio <= 0) { mostrarMensaje("Producto", 'Ingresa un precio unitario válido.'); return; }
    if (cantidad > productoActual.stock) {
        mostrarMensaje("Producto", `Stock insuficiente. Disponible: ${productoActual.stock} ud`); return;
    }

    const existente = venta.items.find(i => i.idProducto === productoActual.id);
    if (existente) {
        existente.cantidad += cantidad;
        existente.subtotal = existente.cantidad * existente.precioUnitario;
    } else {
        venta.items.push({
            idProducto: productoActual.id,
            nombreProducto: productoActual.nombreProducto,
            cantidad,
            precioUnitario: precio,
            subtotal: cantidad * precio
        });
    }
    renderCarrito();
    limpiarBuscador();
}
function renderCarrito() {
    const tbody = document.getElementById('tablaCarrito');
    if (venta.items.length === 0) {
        tbody.innerHTML = `<tr id="filaVacia">
                <td colspan="5" class="text-center text-muted py-4">
                    <i class="fa-solid fa-box-open me-2"></i>Aún no hay productos agregados
                </td></tr>`;
        actualizarResumen();
        return;
    }
    tbody.innerHTML = venta.items.map((item, idx) => `
            <tr>
                <td>${item.nombreProducto}</td>
                <td class="text-center">${item.cantidad}</td>
                <td class="text-end">$ ${fmt(item.precioUnitario)}</td>
                <td class="text-end fw-bold">$ ${fmt(item.subtotal)}</td>
                <td class="text-center">
                    <button class="btn btn-sm btn-outline-danger" onclick="quitarItem(${idx})">
                        <i class="fa-solid fa-xmark"></i>
                    </button>
                </td>
            </tr>`).join('');
    actualizarResumen();
}

function quitarItem(idx) {
    venta.items.splice(idx, 1);
    renderCarrito();
}
function actualizarResumen() {
    const total = venta.items.reduce((acc, i) => acc + i.subtotal, 0);
    const totalItems = venta.items.reduce((acc, i) => acc + i.cantidad, 0);
    document.getElementById('resumenSubtotal').textContent = '$ ' + fmt(total);
    document.getElementById('resumenTotal').textContent = '$ ' + fmt(total);
    document.getElementById('resumenItems').textContent = totalItems;
    document.getElementById('contadorItems').textContent = venta.items.length + ' ítems';
}

function limpiarBuscador() {
    inputBuscar.value = '';
    document.getElementById('inputCantidad').value = 1;
    document.getElementById('inputPrecio').value = '';
    document.getElementById('productoSeleccionado').classList.add('d-none');
    productoActual = null;
}