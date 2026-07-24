(function () {
            // Validación de contraseñas en tiempo real
            const nueva = document.getElementById('nuevaContrasena');
            const confirmar = document.getElementById('confirmarContrasena');
            const feedback = document.getElementById('feedbackConfirmar');

            function verificar() {
                const coincide = nueva.value === confirmar.value;
                feedback.style.display = (confirmar.value && !coincide) ? '' : 'none';
                confirmar.classList.toggle('is-invalid', confirmar.value.length > 0 && !coincide);
                confirmar.classList.toggle('is-valid', confirmar.value.length > 0 && coincide);
            }
            nueva.addEventListener('input', verificar);
            confirmar.addEventListener('input', verificar);

            document.getElementById('formContrasena').addEventListener('submit', function (e) {
                if (nueva.value !== confirmar.value) {
                    e.preventDefault();
                    feedback.style.display = '';
                }
            });
        })();