
// =============================================
// DIRECCIONES
// POST /api/direccions
// =============================================
async function agregarDireccion() {
    const dir = document.getElementById('inputDireccion').value.trim();
    const idBarrio = document.getElementById('selectBarrio').value;
    const idTipo = document.getElementById('selectTipoDireccion').value;
    const complemento = document.getElementById('inputComplementoDir').value.trim();

    if (!dir) { mostrarMensaje('Ingresa una dirección.', 'danger'); return; }
    if (!idBarrio) { mostrarMensaje('Selecciona un barrio.', 'danger'); return; }
    if (!idTipo) { mostrarMensaje('Selecciona el tipo de dirección.', 'danger'); return; }

    try {
        await postAPI('/api/direccions', {
            direccion: dir,
            complemento: complemento || null,
            idCliente: CLIENTE_ID,
            idProveedor: null,
            idBarrio: parseInt(idBarrio),
            idTipoDireccion: parseInt(idTipo)
        });
        cerrarModal('modalDireccion');
        mostrarMensaje('Dirección agregada correctamente.');
        setTimeout(() => location.reload(), 800);
    } catch (e) {
        mostrarMensaje('Error al agregar la dirección: ' + e.message, 'danger');
    }
}

// DELETE /api/direccions/{id}
function eliminarDireccion(id) {
    mostrarModalConfirmacion(
        'Eliminar dirección',
        '¿Estás seguro de que deseas eliminar esta dirección? Esta acción no se puede deshacer.',
        'danger',
        async function () {
            try {
                await deleteAPI(`/api/direccions/${id}`);
                mostrarMensaje('Dirección eliminada.');
                setTimeout(() => location.reload(), 800);
            } catch (e) {
                mostrarMensaje('Error al eliminar.', 'danger');
            }
        },
        'Sí, eliminar'
    );
}