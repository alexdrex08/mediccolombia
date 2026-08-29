 async function verificarPaso1() {
            const identificacion = document.getElementById('inputIdentificacion').value.trim();
            const token = document.getElementById('inputToken').value.trim();

            if (!identificacion) { mostrarError('Ingresa tu número de documento.'); return; }
            if (!token) { mostrarError('Ingresa el token de recuperación.'); return; }

            try {
                // Verificar que el documento existe en BD
                console.log("verificado documento:" + identificacion);
                const res = await fetch(
                    `/auth/api/verificar-token?identificacion=${encodeURIComponent(identificacion)}&token=${encodeURIComponent(token)}`,
                    { headers: { 'Accept': 'application/json' } }
                );

                const contentType = res.headers.get('Content-Type') || '';
                if (contentType.includes('text/html')) {
                    mostrarError('Error de sesión. Recarga la página');
                    return;
                }
                if (res.status === 403) {
                    mostrarError('Token de recuperación inválido. Contacta al administrador.');
                    return;
                }
                if (res.status === 404) {
                    mostrarError('No encontramos un usuario con ese número de documento.');
                    return;
                }

                if (!res.ok) {
                    mostrarError('No encontramos un usuario con ese número de documento.');
                    return;
                }

                // Documento encontrado — pasar datos a campos ocultos y mostrar paso 2
                document.getElementById('hiddenIdentificacion').value = identificacion;
                document.getElementById('hiddenToken').value = token;
                ocultarError();
                irPaso(2);

            } catch (e) {
                mostrarError('Error de conexión. Intenta de nuevo.');
                console.error(e);
            }
        }

        // =============================================
        // NAVEGACIÓN
        // =============================================
        function irPaso(num) {
            document.getElementById('paso1').style.display = num === 1 ? 'block' : 'none';
            document.getElementById('paso2').style.display = num === 2 ? 'block' : 'none';

            const dot1 = document.getElementById('dot1');
            const dot2 = document.getElementById('dot2');
            dot1.className = `rounded-circle d-flex align-items-center justify-content-center mx-auto mb-1 ${num >= 1 ? 'bg-primary' : 'bg-secondary'}`;
            dot2.className = `rounded-circle d-flex align-items-center justify-content-center mx-auto mb-1 ${num >= 2 ? 'bg-primary' : 'bg-secondary'}`;

            const subtitulos = {
                1: 'Paso 1 de 2 — Verifica tu identidad',
                2: 'Paso 2 de 2 — Nueva contraseña'
            };
            document.getElementById('subtitulo').textContent = subtitulos[num];
        }

        function volverPaso1() {
            ocultarError();
            irPaso(1);
        }

        // =============================================
        // VALIDACIÓN DEL FORMULARIO PASO 2
        // =============================================
        document.addEventListener('DOMContentLoaded', function () {
            const form = document.querySelector('#paso2 form');
            if (!form) return;

            form.addEventListener('submit', function (e) {
                const nueva = document.getElementById('inputNueva').value;
                const confirmar = document.getElementById('inputConfirmar').value;
                const errDiv = document.getElementById('errorConfirmar');
                if (nueva !== confirmar) {
                    e.preventDefault();
                    errDiv.style.display = 'block';
                    document.getElementById('inputConfirmar').classList.add('is-invalid');
                } else {
                    errDiv.style.display = 'none';
                }
            });

            document.getElementById('inputConfirmar').addEventListener('input', function () {
                const nueva = document.getElementById('inputNueva').value;
                const errDiv = document.getElementById('errorConfirmar');
                if (this.value && this.value !== nueva) {
                    errDiv.style.display = 'block';
                    this.classList.add('is-invalid');
                } else {
                    errDiv.style.display = 'none';
                    this.classList.remove('is-invalid');
                }
            });
        });

        // =============================================
        // UTILIDADES
        // =============================================
        function mostrarError(msg) {
            const el = document.getElementById('mensajeError');
            document.getElementById('textoError').textContent = msg;
            el.classList.remove('d-none');
        }

        function ocultarError() {
            document.getElementById('mensajeError').classList.add('d-none');
        }

        function toggleVisibilidad(inputId, btn) {
            const input = document.getElementById(inputId);
            const icon = btn.querySelector('i');
            if (input.type === 'password' || input.type === 'text') {
                const esPassword = input.type === 'password';
                input.type = esPassword ? 'text' : 'password';
                icon.classList.toggle('fa-eye', !esPassword);
                icon.classList.toggle('fa-eye-slash', esPassword);
            }
        }