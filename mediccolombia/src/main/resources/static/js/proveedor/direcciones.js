
// =============================================
// DIRECCIONES
// =============================================
async function agregarDireccion() {
    const dir = document.getElementById('inputDireccion').value.trim();
    const idBarr = parseInt(document.getElementById('selectBarrio').value);
    const idTipo = parseInt(document.getElementById('selectTipoDireccion').value);
    const compl = document.getElementById('inputComplementoDir').value.trim();
    if (!dir) { mostrarMensaje('Ingresa una dirección.', 'danger'); return; }
    if (!idBarr) { mostrarMensaje('Selecciona un barrio.', 'danger'); return; }
    if (!idTipo) { mostrarMensaje('Selecciona el tipo.', 'danger'); return; }
    try {
        await postAPI('/api/direccions', {
            direccion: dir,
            complemento: compl || null,
            idCliente: null,
            idProveedor: PROVEEDOR_ID,
            idBarrio: idBarr,
            idTipoDireccion: idTipo
        });
        cerrarModal('modalDireccion');
        mostrarMensaje('Dirección agregada correctamente.');
        setTimeout(() => location.reload(), 800);
    } catch (e) { mostrarMensaje('Error: ' + e.message, 'danger'); }
}

async function eliminarDireccion(id) {
    if (!confirm('¿Eliminar esta dirección?')) return;
    try {
        await deleteAPI(`/api/direccions/${id}`);
        mostrarMensaje('Dirección eliminada.');
        setTimeout(() => location.reload(), 800);
    } catch (e) { mostrarMensaje('Error al eliminar.', 'danger'); }
}