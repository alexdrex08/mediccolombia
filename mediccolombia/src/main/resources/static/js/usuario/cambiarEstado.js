function abrirModalEstado(id, nombre) {
    document.getElementById('modalNombreUsuario').textContent = nombre;
    document.getElementById('idUsuarioCambio').value = id;
    document.getElementById('formCambiarEstado').reset();
    const ahora = new Date();
    const fechaLocal = ahora.toISOString().slice(0, 16);
    document.getElementById('inputFechaInicio').value = fechaLocal;
    new bootstrap.Modal(document.getElementById('modalCambiarEstado')).show();
}

document.addEventListener('DOMContentLoaded', function() {
    const btnConfirmar = document.getElementById('btnConfirmarCambioEstado');
    if (btnConfirmar) {
        btnConfirmar.addEventListener('click', function() {
            const idUsuario = document.getElementById('idUsuarioCambio').value;
            const idTipoEstado = document.getElementById('selectTipoEstado').value;
            const fechaInicio = document.getElementById('inputFechaInicio').value;
            const fechaFin = document.getElementById('inputFechaFin').value;
            const observacion = document.getElementById('inputObservacionEstado').value;

            if (!idTipoEstado || !fechaInicio) {
                mostrarMensaje('Error', 'Debes seleccionar un estado y una fecha de inicio.', 'warning');
                return;
            }

            mostrarModalConfirmacion(
                'Confirmar cambio de estado',
                '¿Estás seguro de cambiar el estado de este usuario? Esta acción quedará registrada.',
                'warning',
                function() {
                    fetch(`/usuarios/${idUsuario}/estado`, {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                        body: new URLSearchParams({
                            idTipoEstado: idTipoEstado,
                            fechaInicio: fechaInicio,
                            fechaFin: fechaFin || '',
                            observacion: observacion || ''
                        })
                    })
                    .then(response => {
                        if (response.ok) {
                            bootstrap.Modal.getInstance(document.getElementById('modalCambiarEstado')).hide();
                            mostrarMensaje('Éxito', 'Estado actualizado correctamente.', 'success');
                            setTimeout(() => location.reload(), 1200);
                        } else {
                            return response.text().then(text => { throw new Error(text || 'Error al cambiar el estado.'); });
                        }
                    })
                    .catch(error => {
                        mostrarMensaje('Error', error.message, 'danger');
                    });
                },
                'Sí, cambiar estado'
            );
        });
    }
});