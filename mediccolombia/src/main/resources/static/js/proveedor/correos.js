// =============================================
// CORREOS
// =============================================
async function agregarCorreo() {
    const correo = document.getElementById('inputCorreo').value.trim();
    const idTipo = parseInt(document.getElementById('selectTipoCorreo').value);
    if (!correo) { mostrarMensaje('Ingresa un correo.', 'danger'); return; }
    if (!idTipo) { mostrarMensaje('Selecciona el tipo.', 'danger'); return; }
    try {
        await postAPI('/api/correos', {
            correoElectronico: correo,
            idCliente: null,
            idProveedor: PROVEEDOR_ID,
            idTipoCorreo: idTipo
        });
        cerrarModal('modalCorreo');
        mostrarMensaje('Correo agregado correctamente.');
        setTimeout(() => location.reload(), 800);
    } catch (e) { mostrarMensaje('Error: ' + e.message, 'danger'); }
}

async function eliminarCorreo(id) {
    if (!confirm('¿Eliminar este correo?')) return;
    try {
        await deleteAPI(`/api/correos/${id}`);
        mostrarMensaje('Correo eliminado.');
        setTimeout(() => location.reload(), 800);
    } catch (e) { mostrarMensaje('Error al eliminar.', 'danger'); }
}
