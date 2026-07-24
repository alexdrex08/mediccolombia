(function () {
            const selectTema = document.getElementById('selectTema');
            if (selectTema) {
                selectTema.addEventListener('change', function () {
                    const alerta = document.createElement('div');
                    alerta.className = 'alert alert-warning alert-dismissible fade show py-2 small mt-2';
                    alerta.innerHTML = `
                        <i class="fa-solid fa-triangle-exclamation me-2"></i>
                        Cambio de tema temporalmente deshabilitado. Usa el botón "Guardar preferencias" para aplicar cambios.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    `;
                    const form = document.getElementById('formPreferencias');
                    const parent = form.parentNode;
                    const existingAlerts = parent.querySelectorAll('.alert-warning:not(.alert-dismissible)');
                    existingAlerts.forEach(el => el.remove());
                    parent.insertBefore(alerta, form);
                    setTimeout(() => {
                        if (alerta.parentNode) alerta.remove();
                    }, 5000);
                });
            }
        })();