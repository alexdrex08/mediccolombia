
        // =============================================
        // TELÉFONOS
        // =============================================
        async function agregarTelefono() {
            const numero  = document.getElementById('inputNumero').value.trim();
            const idTipo  = parseInt(document.getElementById('selectTipoTelefono').value);
            const compl   = document.getElementById('inputComplementoTel').value.trim();
            if (!numero) { mostrarMensaje('Ingresa un número.', 'danger'); return; }
            if (!idTipo)  { mostrarMensaje('Selecciona el tipo.', 'danger'); return; }
            try {
                await postAPI('/api/telefonos', {
                    numero,
                    complemento: compl || null,
                    idCliente: null,
                    idProveedor: PROVEEDOR_ID,
                    idTipoTelefono: idTipo
                });
                cerrarModal('modalTelefono');
                mostrarMensaje('Teléfono agregado correctamente.');
                setTimeout(() => location.reload(), 800);
            } catch (e) { mostrarMensaje('Error: ' + e.message, 'danger'); }
        }

        async function eliminarTelefono(id) {
            if (!confirm('¿Eliminar este teléfono?')) return;
            try {
                await deleteAPI(`/api/telefonos/${id}`);
                mostrarMensaje('Teléfono eliminado.');
                setTimeout(() => location.reload(), 800);
            } catch (e) { mostrarMensaje('Error al eliminar.', 'danger'); }
        }
