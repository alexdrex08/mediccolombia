document.addEventListener('DOMContentLoaded', function () {
            // ============================================
            // oBTENER ID DEL USUARIO
            // ============================================
            const userIdElement = document.getElementById('userIdHidden');
            const userId = userIdElement ? userIdElement.value : null;

            if (!userId || userId === 'null' || userId === '') {
                console.error('ID de usuario no disponible');
                mostrarAlerta('error', 'Error: No se pudo identificar al usuario');
                return;
            }

            // ============================================
            // LEMENTOS DEL DOM
            // ============================================
            const inputFoto = document.getElementById('inputFoto');
            const fotoPerfil = document.getElementById('fotoPerfil');
            const progressBar = document.getElementById('progressBar');
            const progressBarInner = document.getElementById('progressBarInner');
            const progressText = document.getElementById('progressText');
            const btnEliminarFoto = document.getElementById('btnEliminarFoto');

            // ============================================
            // FUNCIÓN PARA MOSTRAR ALERTAS
            // ============================================
            function mostrarAlerta(tipo, mensaje) {
                const alertas = document.querySelectorAll('.alert-foto');
                alertas.forEach(a => a.remove());

                const alerta = document.createElement('div');
                alerta.className = `alert alert-${tipo === 'success' ? 'success' : 'danger'} alert-dismissible fade show py-2 mb-3 alert-foto`;
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

            // ============================================
            // PREVISUALIZACIÓN Y SUBIDA
            // ============================================
            inputFoto.addEventListener('change', function (event) {
                const file = event.target.files[0];
                if (!file) return;

                if (!file.type.startsWith('image/')) {
                    mostrarAlerta('error', 'Solo se permiten imágenes');
                    inputFoto.value = '';
                    return;
                }

                if (file.size > 2 * 1024 * 1024) {
                    mostrarAlerta('error', 'La imagen no debe superar los 2MB');
                    inputFoto.value = '';
                    return;
                }

                const reader = new FileReader();
                reader.onload = function (e) {
                    fotoPerfil.src = e.target.result;
                };
                reader.readAsDataURL(file);

                subirFoto(file);
            });

            // ============================================
            //FUNCIÓn SUBIR FOTO
            // ============================================
            function subirFoto(file) {
                const formData = new FormData();
                formData.append('foto', file);

                progressBar.style.display = 'block';
                progressBarInner.style.width = '0%';
                progressBarInner.textContent = '0%';
                progressText.textContent = 'Subiendo...';

                let progreso = 0;
                const intervalo = setInterval(() => {
                    progreso += 10;
                    if (progreso <= 90) {
                        progressBarInner.style.width = progreso + '%';
                        progressBarInner.textContent = progreso + '%';
                    }
                }, 200);

                fetch(`/api/usuario/${userId}/foto`, {
                    method: 'POST',
                    body: formData
                })
                .then(response => {
                    clearInterval(intervalo);
                    if (!response.ok) {
                        return response.text().then(text => {
                            throw new Error(text || 'Error al subir la foto');
                        });
                    }
                    return response.json();
                })
                .then(data => {
                    fotoPerfil.src = data.url + '?t=' + new Date().getTime();
                    progressBarInner.style.width = '100%';
                    progressBarInner.textContent = '100%';
                    progressText.textContent = '¡Completado!';

                    //Mostrar botón eliminar
                    if (btnEliminarFoto) {
                        btnEliminarFoto.style.display = 'block';
                    }

                    mostrarAlerta('success', 'Foto actualizada correctamente');

                    setTimeout(() => {
                        progressBar.style.display = 'none';
                        progressBarInner.style.width = '0%';
                        progressBarInner.textContent = '0%';
                    }, 2000);
                })
                .catch(error => {
                    clearInterval(intervalo);
                    console.error('Error:', error);

                    const urlOriginal = fotoPerfil.src.split('?')[0];
                    if (urlOriginal && !urlOriginal.includes('avatar-placeholder')) {
                        fotoPerfil.src = urlOriginal + '?t=' + new Date().getTime();
                    } else {
                        fotoPerfil.src = '/img/avatar-placeholder.webp';
                    }

                    mostrarAlerta('error', error.message || 'Error al subir la foto');
                    progressBar.style.display = 'none';
                });
            }

            // ============================================
            // MODAL DE CONFIRMACIÓN
            // ============================================
            if (btnEliminarFoto) {
                btnEliminarFoto.addEventListener('click', function () {
                    //Usar el modal reutilizable
                    mostrarModalConfirmacion(
                        'Eliminar foto de perfil',
                        `¿Estás seguro de que deseas eliminar tu foto de perfil?<br><br>
                         <span class="text-danger fw-semibold">
                             <i class="fa-solid fa-circle-exclamation me-1"></i>
                             Esta acción no se puede deshacer.
                         </span>`,
                        'danger',
                        function () {
                            // Acción de eliminar
                            progressBar.style.display = 'block';
                            progressBarInner.style.width = '50%';
                            progressBarInner.textContent = '50%';
                            progressText.textContent = 'Eliminando...';

                            fetch(`/api/usuario/${userId}/foto`, {
                                method: 'DELETE'
                            })
                            .then(response => {
                                if (!response.ok) {
                                    return response.text().then(text => {
                                        throw new Error(text || 'Error al eliminar la foto');
                                    });
                                }
                                return response.json();
                            })
                            .then(data => {
                                fotoPerfil.src = '/img/avatar-placeholder.webp?t=' + new Date().getTime();
                                progressBarInner.style.width = '100%';
                                progressBarInner.textContent = '100%';
                                progressText.textContent = '¡Eliminada!';

                                // Ocultar botón eliminar
                                if (btnEliminarFoto) {
                                    btnEliminarFoto.style.display = 'none';
                                }

                                mostrarAlerta('success', 'Foto eliminada correctamente');

                                setTimeout(() => {
                                    progressBar.style.display = 'none';
                                    progressBarInner.style.width = '0%';
                                    progressBarInner.textContent = '0%';
                                }, 2000);
                            })
                            .catch(error => {
                                console.error('Error:', error);
                                mostrarAlerta('error', error.message || 'Error al eliminar la foto');
                                progressBar.style.display = 'none';
                            });
                        },
                        'Sí, eliminar foto'
                    );
                });
            }
        });