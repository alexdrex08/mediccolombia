// =============================================
// BLOQUE 5: CARRITO — AGREGAR, QUITAR, RENDERIZAR
// =============================================

// Función central para agregar productos al carrito
function agregarAlCarrito(idProducto, nombreProducto, cantidad,
    precioUnitario, idProveedor, nombreProveedor) {
    const existente = pedido.items.find(i => i.idProducto === idProducto);
    if (existente) {
        existente.cantidad += cantidad;
        existente.subtotal = existente.cantidad * existente.precioUnitario;
    } else {
        pedido.items.push({
            idProducto, nombreProducto, cantidad, precioUnitario,
            subtotal: cantidad * precioUnitario,
            idProveedor, nombreProveedor
        });
    }

    // Actualizar proveedor activo (siempre el del primer item)
    if (pedido.items.length > 0) {
        const primerItem = pedido.items[0];
        pedido.proveedor = {
            id: primerItem.idProveedor,
            nombre: primerItem.nombreProveedor
        };
    }

    renderDetalle();
    sincronizarUI();
}

// Renderiza la tabla del carrito con los items actuales
function renderDetalle() {
    const tbody = document.getElementById('tablaDetallePedido');
    const inputsDiv = document.getElementById('inputsOcultos');
    const bloqueDetalle = document.getElementById('bloqueDetalle');

    if (pedido.items.length === 0) {
        tbody.innerHTML = `<tr><td colspan="5" class="text-center text-muted py-3">
            Agrega productos desde la búsqueda</td></tr>`;
        inputsDiv.innerHTML = '';
        bloqueDetalle.style.display = 'none';
        actualizarResumen();
        return;
    }

    bloqueDetalle.style.display = 'block';
    tbody.innerHTML = pedido.items.map((item, idx) => `
    <tr>
        <td class="fw-bold">${item.nombreProducto}</td>
        <td class="text-center">${item.cantidad}</td>
        <td class="text-end">$ ${fmt(item.precioUnitario)}</td>
        <td class="text-end fw-bold">$ ${fmt(item.subtotal)}</td>
        <td class="text-center">
            <button type="button" class="btn btn-sm btn-outline-danger"
                    onclick="quitarItem(${idx})">
                <i class="fa-solid fa-xmark"></i>
            </button>
        </td>
    </tr>`).join('');

    // Inputs ocultos para el POST del formulario
    inputsDiv.innerHTML = pedido.items.map(item => `
    <input type="hidden" name="idProducto" value="${item.idProducto}">
    <input type="hidden" name="cantidad" value="${item.cantidad}">
    <input type="hidden" name="precioUnitario" value="${item.precioUnitario}">
    `).join('');

    actualizarResumen();
    actualizarVisibilidadBotonesCarrito();
}

// Elimina un item del carrito por su índice
function quitarItem(idx) {
    pedido.items.splice(idx, 1);

    if (pedido.items.length === 0) {
        // Carrito vacío: liberar proveedor y limpiar UI
        pedido.proveedor = null;
        limpiarBadgeTab2();
        actualizarBadgeProveedor();
        document.getElementById('tablaComparacionWrapper').style.display = 'none';
        document.getElementById('sinProveedoresMsg').classList.add('d-none');
        document.getElementById('msgBuscaArticulo').style.display = 'block';
        document.getElementById('alertaProveedorBloqueadoA').classList.add('d-none');
        document.getElementById('alertaProveedorBloqueadoB').classList.add('d-none');
    } else {
        // Mantener el proveedor del primer item
        const primerItem = pedido.items[0];
        pedido.proveedor = {
            id: primerItem.idProveedor,
            nombre: primerItem.nombreProveedor
        };
    }

    renderDetalle();
    sincronizarUI();
}

// Actualiza el resumen: total, unidades, contador de productos
function actualizarResumen() {
    const total = pedido.items.reduce((acc, i) => acc + i.subtotal, 0);
    const unidades = pedido.items.reduce((acc, i) => acc + i.cantidad, 0);
    document.getElementById('resumenProductos').textContent = pedido.items.length;
    document.getElementById('resumenUnidades').textContent = unidades;
    document.getElementById('resumenTotal').textContent = '$ ' + fmt(total);
    document.getElementById('contadorProductos').textContent =
        pedido.items.length + ' producto' + (pedido.items.length !== 1 ? 's' : '');
}

// Vacía completamente el carrito y resetea todo
function vaciarCarrito() {
    // Si el carrito ya está vacío, no hacer nada
    if (pedido.items.length === 0) return;

    mostrarModalConfirmacion(
        'Vaciar carrito',
        `¿Estás seguro de que deseas vaciar el carrito?<br><br>
         <span class="text-danger fw-semibold">
             <i class="fa-solid fa-circle-exclamation me-1"></i>
             Se eliminarán todos los productos agregados.
         </span>`,
        'danger',
        function () {
            // Acción real de vaciar
            pedido.items = [];
            pedido.proveedor = null;

            renderDetalle();
            sincronizarUI();
            limpiarBadgeTab2();

            document.getElementById('selectProveedor').value = '';
            document.getElementById('catalogoProveedor').style.display = 'none';
            document.getElementById('msgSeleccionaProveedor').style.display = 'block';
            document.getElementById('alertaProveedorBloqueadoA').classList.add('d-none');
            document.getElementById('alertaProveedorBloqueadoB').classList.add('d-none');
            actualizarBadgeProveedor();
        },
        'Sí, vaciar carrito'
    );
}

// Controla la visibilidad del botón "Vaciar carrito"
function actualizarVisibilidadBotonesCarrito() {
    const carrito = pedido.items;
    const btnVaciar = document.getElementById('btnVaciarCarrito');
    if (carrito && carrito.length > 0) {
        btnVaciar.style.display = 'inline-block';
    } else {
        btnVaciar.style.display = 'none';
    }
}