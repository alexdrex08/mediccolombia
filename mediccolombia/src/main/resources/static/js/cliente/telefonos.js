
// =============================================
// TELÉFONOS
// POST /api/telefonos
// =============================================
async function agregarTelefono() {
    const numero = document.getElementById('inputNumero').value.trim();
    const idTipo = document.getElementById('selectTipoTelefono').value;
    const complemento = document.getElementById('inputComplementoTel').value.trim();

    if (!numero) { mostrarMensaje('Ingresa un número de teléfono.', 'danger'); return; }
    if (!idTipo) { mostrarMensaje('Selecciona el tipo de teléfono.', 'danger'); return; }

    try {
        await postAPI('/api/telefonos', {
            numero,
            complemento: complemento || null,
            idCliente: CLIENTE_ID,
            idProveedor: null,
            idTipoTelefono: parseInt(idTipo)
        });
        cerrarModal('modalTelefono');
        mostrarMensaje('Teléfono agregado correctamente.');
        setTimeout(() => location.reload(), 800);
    } catch (e) {
        mostrarMensaje('Error al agregar el teléfono: ' + e.message, 'danger');
    }
}

// DELETE /api/telefonos/{id}
async function eliminarTelefono(id) {
    if (!confirm('¿Eliminar este teléfono?')) return;
    try {
        await deleteAPI(`/api/telefonos/${id}`);
        mostrarMensaje('Teléfono eliminado.');
        setTimeout(() => location.reload(), 800);
    } catch (e) {
        mostrarMensaje('Error al eliminar el teléfono.', 'danger');
    }
}
