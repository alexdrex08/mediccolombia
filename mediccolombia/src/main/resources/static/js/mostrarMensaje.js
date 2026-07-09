        function mostrarMensaje(titulo, mensaje, tipo = "warning") {

            const modal = document.getElementById("modalMensaje");

            const header = modal.querySelector(".modal-header");

            const tituloModal =
                document.getElementById("modalMensajeTitulo");

            const texto =
                document.getElementById("modalMensajeTexto");

            // Reiniciar colores
            header.className = "modal-header";

            switch (tipo) {

                case "danger":
                    header.classList.add("bg-danger", "text-white");
                    break;

                case "success":
                    header.classList.add("bg-success", "text-white");
                    break;

                case "info":
                    header.classList.add("bg-info", "text-white");
                    break;

                default:
                    header.classList.add("bg-warning", "text-dark");
            }

            tituloModal.innerHTML =
                `<i class="fa-solid fa-circle-exclamation me-2"></i>${titulo}`;

            texto.textContent = mensaje;

            bootstrap.Modal
                .getOrCreateInstance(modal)
                .show();
        }