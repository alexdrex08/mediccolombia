// =============================================
// BLOQUE 2: UTILIDADES
// =============================================

function fmt(num) {
    return Number(num).toLocaleString('es-CO');
}

// =============================================
// VALIDACIÓN DE PROVEEDOR
// =============================================
function validarProveedor(idProveedor, nombreProveedor, origen) {
    if (pedido.proveedor === null) {
        pedido.proveedor = { id: idProveedor, nombre: nombreProveedor };
        actualizarBadgeProveedor();
        return true;
    }
    if (pedido.proveedor.id === idProveedor) {
        return true;
    }
    if (origen === 'A') {
        document.getElementById('provBloqueadoNombreA').textContent = pedido.proveedor.nombre;
        document.getElementById('alertaProveedorBloqueadoA').classList.remove('d-none');
    } else {
        document.getElementById('provBloqueadoNombreB').textContent = pedido.proveedor.nombre;
        document.getElementById('alertaProveedorBloqueadoB').classList.remove('d-none');
    }
    return false;
}

// =============================================
// BADGE DEL PROVEEDOR
// =============================================
function actualizarBadgeProveedor() {
    const badge = document.getElementById('badgeProveedorActivo');
    const nombre = document.getElementById('nombreProveedorActivo');
    const resumen = document.getElementById('resumenProveedor');
    if (pedido.proveedor) {
        nombre.textContent = pedido.proveedor.nombre;
        badge.classList.remove('d-none');
        resumen.textContent = pedido.proveedor.nombre;
        resumen.classList.remove('text-muted', 'fst-italic');
    } else {
        badge.classList.add('d-none');
        resumen.textContent = 'Sin asignar';
        resumen.classList.add('text-muted', 'fst-italic');
    }
}

// =============================================
// LIMPIAR BADGE DEL TAB 2
// =============================================
function limpiarBadgeTab2() {
    articuloActual = null;
    document.getElementById('inputBuscarArticulo').value = '';
    document.getElementById('articuloBadge').classList.add('d-none');
    document.getElementById('resultadosBusquedaArticulo').style.display = 'none';
}

// =============================================
// MODAL DE CONFIRMACIÓN REUTILIZABLE
// =============================================
function mostrarModalConfirmacion(titulo, mensaje, tipo = 'warning', onConfirm, textoBotonConfirmar = 'Confirmar') {
    const header = document.getElementById('modalConfirmacionHeader');
    const iconos = {
        danger: 'fa-solid fa-circle-exclamation',
        warning: 'fa-solid fa-triangle-exclamation',
        info: 'fa-solid fa-circle-info',
        success: 'fa-solid fa-check-circle'
    };
    const colores = {
        danger: 'bg-danger text-white',
        warning: 'bg-warning text-dark',
        info: 'bg-info text-white',
        success: 'bg-success text-white'
    };

    header.className = `modal-header ${colores[tipo] || 'bg-warning text-dark'}`;

    document.getElementById('modalConfirmacionLabel').innerHTML = `
        <i class="${iconos[tipo] || 'fa-solid fa-triangle-exclamation'} me-2"></i>
        ${titulo}
    `;

    document.getElementById('modalConfirmacionBody').innerHTML = mensaje;

    const btnConfirmar = document.getElementById('btnConfirmarAccion');
    btnConfirmar.textContent = textoBotonConfirmar;

    const clasesBtn = {
        danger: 'btn-danger',
        warning: 'btn-warning',
        info: 'btn-info',
        success: 'btn-success'
    };
    btnConfirmar.className = `btn ${clasesBtn[tipo] || 'btn-danger'} btn-sm`;

    // Reemplazar el botón para evitar eventos duplicados
    btnConfirmar.replaceWith(btnConfirmar.cloneNode(true));
    const nuevoBtn = document.getElementById('btnConfirmarAccion');

    nuevoBtn.addEventListener('click', function () {
        const modal = bootstrap.Modal.getInstance(document.getElementById('modalConfirmacion'));
        modal.hide();
        if (typeof onConfirm === 'function') {
            onConfirm();
        }
    });

    const modal = new bootstrap.Modal(document.getElementById('modalConfirmacion'));
    modal.show();
}

// =============================================
// CANCELAR PEDIDO CON MODAL
// =============================================
function cancelarPedido() {
    mostrarModalConfirmacion(
        'Cancelar pedido',
        `¿Estás seguro de que deseas cancelar el pedido en curso?<br><br>
         <span class="text-danger fw-semibold">
             <i class="fa-solid fa-circle-exclamation me-1"></i>
             Los productos agregados se perderán.
         </span>`,
        'warning',
        function () {
            window.location.href = '/pedidos';
        },
        'Sí, cancelar pedido'
    );
}

// Mantener compatibilidad
function cancelar() {
    cancelarPedido();
}