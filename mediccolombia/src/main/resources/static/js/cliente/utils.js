
// =============================================
// UTILIDADES
// =============================================
function mostrarMensaje(texto, tipo = 'success') {
    const div = document.getElementById('mensajeAjax');
    const span = document.getElementById('textoMensajeAjax');
    const icon = document.getElementById('iconoMensaje');
    div.className = `alert alert-${tipo} py-2 mb-3`;
    icon.className = tipo === 'success'
        ? 'fa-solid fa-circle-check me-2'
        : 'fa-solid fa-circle-exclamation me-2';
    span.textContent = texto;
    div.classList.remove('d-none');
    // Ocultar después de 3 segundos
    setTimeout(() => div.classList.add('d-none'), 3000);
}

function cerrarModal(id) {
    bootstrap.Modal.getInstance(document.getElementById(id))?.hide();
}

async function postAPI(url, body) {
    const res = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
    });
    if (!res.ok) {
        const txt = await res.text();
        throw new Error(txt || `Error ${res.status}`);
    }
    return res.json();
}

async function deleteAPI(url) {
    const res = await fetch(url, { method: 'DELETE' });
    if (!res.ok) throw new Error(`Error ${res.status}`);
}

/**
 * @param {number} id
 * @param {string} nombre -
 */
function eliminarCliente(id, nombre) {
    mostrarModalConfirmacion(
        'Eliminar cliente',
        `¿Estás seguro de que deseas eliminar al cliente <strong>${nombre}</strong>?<br><br>
        <span class="text-danger fw-semibold">
            <i class="fa-solid fa-circle-exclamation me-1"></i>
            Esta acción no se puede deshacer.
        </span>`,
        'danger',
        function () {
            fetch(`/clientes/${id}/eliminar`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    '_method': 'delete'
                })
            })
                .then(response => {
                    if (response.ok) {
                        window.location.reload();
                    } else {
                        mostrarMensaje('Error al eliminar el cliente.', 'danger');
                    }
                })
                .catch(() => {
                    mostrarMensaje('Error de conexión al eliminar el cliente.', 'danger');
                });
        },
        'Sí, eliminar cliente'
    );
}
