// =============================================
// CORREOS
// POST /api/correos
// =============================================
async function agregarCorreo() {
    const correo = document.getElementById('inputCorreo').value.trim();
    const idTipoCorreo = document.getElementById('selectTipoCorreo').value;

    if (!correo) { mostrarMensaje('Ingresa un correo electrónico.', 'danger'); return; }
    if (!idTipoCorreo) { mostrarMensaje('Selecciona el tipo de correo.', 'danger'); return; }

    try {
        await postAPI('/api/correos', {
            correoElectronico: correo,
            idCliente: CLIENTE_ID,
            idProveedor: null,
            idTipoCorreo: parseInt(idTipoCorreo)
        });
        cerrarModal('modalCorreo');
        mostrarMensaje('Correo agregado correctamente.');
        setTimeout(() => location.reload(), 800);
    } catch (e) {
        mostrarMensaje('Error al agregar el correo: ' + e.message, 'danger');
    }
}

// DELETE /api/correos/{id}
async function eliminarCorreo(id) {
    if (!confirm('¿Eliminar este correo?')) return;
    try {
        await deleteAPI(`/api/correos/${id}`);
        mostrarMensaje('Correo eliminado.');
        setTimeout(() => location.reload(), 800);
    } catch (e) {
        mostrarMensaje('Error al eliminar el correo.', 'danger');
    }
}
