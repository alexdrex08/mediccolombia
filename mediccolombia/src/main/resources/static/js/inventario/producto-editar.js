document.addEventListener('DOMContentLoaded', function () {
    const formEditar = document.getElementById('formEditar');
    if (!formEditar) return;

    const minimoInput = document.getElementById('stockMinimo');
    const maximoInput = document.getElementById('stockMaximo');
    const fechaInput = document.getElementById('fechaExpiracion');
    const indicador = document.getElementById('indicadorStock');
    const textoRango = document.getElementById('textoRango');
    const diasCard = document.getElementById('diasRestantes');
    const textoDias = document.getElementById('textoDiasRestantes');
    const feedbackFecha = document.getElementById('feedbackFecha');

    function parseFechaLocal(fechaString) {
        if (!fechaString) return null;
        const [year, month, day] = fechaString.split('-').map(Number);
        return new Date(year, month - 1, day);
    }

    // ── Indicador de rango de stock ──────────────────────────────
    function actualizarRango() {
        if (!minimoInput || !maximoInput) return;
        const min = parseInt(minimoInput.value);
        const max = parseInt(maximoInput.value);
        if (!isNaN(min) && !isNaN(max) && max > 0) {
            if (indicador) indicador.style.display = '';
            if (textoRango) textoRango.textContent = min + ' (mínimo) → ' + max + ' (máximo)';
            const valido = min < max;
            minimoInput.classList.toggle('is-invalid', !valido);
            maximoInput.classList.toggle('is-invalid', !valido);
            minimoInput.classList.toggle('is-valid', valido);
            maximoInput.classList.toggle('is-valid', valido);
        } else {
            if (indicador) indicador.style.display = 'none';
        }
    }

    minimoInput?.addEventListener('input', actualizarRango);
    maximoInput?.addEventListener('input', actualizarRango);

    // ── Validación de fecha (>= 7 días en el futuro) ─────────────
    function validarFecha() {
        if (!fechaInput) return false;
        const valor = fechaInput.value;
        if (!valor) {
            fechaInput.classList.remove('is-valid', 'is-invalid');
            if (diasCard) diasCard.style.display = 'none';
            return false;
        }

        const seleccionada = parseFechaLocal(valor);
        const hoy = new Date();
        hoy.setHours(0, 0, 0, 0);
        seleccionada.setHours(0, 0, 0, 0);

        if (seleccionada <= hoy) {
            fechaInput.classList.add('is-invalid');
            fechaInput.classList.remove('is-valid');
            if (diasCard) diasCard.style.display = 'none';
            if (feedbackFecha) {
                feedbackFecha.textContent = 'La fecha de expiración debe ser una fecha futura.';
                feedbackFecha.style.display = '';
            }
            return false;
        }

        const dias = Math.ceil((seleccionada - hoy) / (1000 * 60 * 60 * 24));
        if (dias < 7) {
            fechaInput.classList.add('is-invalid');
            fechaInput.classList.remove('is-valid');
            if (diasCard) diasCard.style.display = 'none';
            if (feedbackFecha) {
                feedbackFecha.textContent = 'La fecha de expiración debe ser al menos 7 días en el futuro. Quedan ' + dias + ' días.';
                feedbackFecha.style.display = '';
            }
            return false;
        } else {
            fechaInput.classList.remove('is-invalid');
            fechaInput.classList.add('is-valid');
            if (feedbackFecha) feedbackFecha.style.display = 'none';
            if (diasCard) {
                diasCard.style.display = '';
                textoDias.textContent = dias + ' día(s)';
                textoDias.className = 'fw-bold fs-5 ' + (dias <= 30 ? 'text-warning' : 'text-success');
            }
            return true;
        }
    }

    // Validar al cargar la página
    if (fechaInput?.value) {
        validarFecha();
    }

    fechaInput?.addEventListener('change', validarFecha);
    fechaInput?.addEventListener('input', validarFecha);

    // ── Validación final ──────────────────────
    formEditar.addEventListener('submit', function (e) {
        let valido = true;

        // Validar requeridos
        this.querySelectorAll('[required]').forEach(campo => {
            if (!campo.value.trim()) {
                campo.classList.add('is-invalid');
                valido = false;
            }
        });

        // Validar stock min < max
        const min = parseInt(minimoInput?.value) || 0;
        const max = parseInt(maximoInput?.value) || 0;
        if (min >= max) {
            minimoInput?.classList.add('is-invalid');
            maximoInput?.classList.add('is-invalid');
            valido = false;
        }

        // Validar fecha (>= 7 días)
        if (!validarFecha()) {
            valido = false;
        }

        if (!valido) {
            e.preventDefault();
            window.scrollTo({ top: 0, behavior: 'smooth' });
        }
    });

    actualizarRango();
});