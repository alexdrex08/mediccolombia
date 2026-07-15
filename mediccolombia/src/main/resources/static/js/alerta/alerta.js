function prepararEliminar(id, nombreProducto) {
    mostrarModalConfirmacion(
        'Resolver alerta',
        `¿Deseas resolver la alerta del producto <strong>${nombreProducto || '#' + id}</strong>?<br><br>
                 <span class="text-warning fw-semibold">
                     <i class="fa-solid fa-triangle-exclamation me-1"></i>
                     Al resolverla, la alerta se eliminará y no volverá a aparecer.
                 </span>`,
        'warning',
        function () {
            fetch(`/productos/alertas/${id}/eliminar`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (response.ok) {
                        window.location.reload();
                    } else {
                        mostrarAlerta('error', 'Error al resolver la alerta');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    mostrarAlerta('error', 'Error al resolver la alerta');
                });
        },
        'Sí, resolver alerta'
    );
}

function mostrarAlerta(tipo, mensaje) {
    const alerta = document.createElement('div');
    alerta.className = `alert alert-${tipo === 'success' ? 'success' : 'danger'} alert-dismissible fade show py-2 mb-3`;
    alerta.innerHTML = `
                <i class="fa-solid fa-${tipo === 'success' ? 'circle-check' : 'circle-exclamation'} me-2"></i>
                <span>${mensaje}</span>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            `;

    const titulo = document.querySelector('.d-flex.justify-content-between.align-items-center');
    if (titulo && titulo.parentNode) {
        titulo.parentNode.insertBefore(alerta, titulo.nextSibling);
    }

    setTimeout(() => {
        if (alerta.parentNode) {
            alerta.remove();
        }
    }, 5000);
}