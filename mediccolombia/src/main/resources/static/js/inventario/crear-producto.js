document.addEventListener('DOMContentLoaded', function () {
    const stockInput = document.getElementById('stock');
    const minimoInput = document.getElementById('stockMinimo');
    const maximoInput = document.getElementById('stockMaximo');
    const fechaInput = document.getElementById('fechaExpiracion');
    const indicador = document.getElementById('indicadorStock');
    const textoRango = document.getElementById('textoRango');
    const diasCard = document.getElementById('diasRestantes');
    const textoDias = document.getElementById('textoDiasRestantes');
    const formProducto = document.getElementById('formProducto');

    if (!formProducto) return;

    // ── Indicador de rango de stock ──────────────────────────────
    function actualizarRango() {
        const min = parseInt(minimoInput.value);
        const max = parseInt(maximoInput.value);
        if (!isNaN(min) && !isNaN(max) && max > 0) {
            indicador.style.display = '';
            textoRango.textContent = min + ' (mínimo) → ' + max + ' (máximo)';

            const valido = min < max;
            minimoInput.classList.toggle('is-invalid', !valido);
            maximoInput.classList.toggle('is-invalid', !valido);
            minimoInput.classList.toggle('is-valid', valido);
            maximoInput.classList.toggle('is-valid', valido);
        } else {
            indicador.style.display = 'none';
        }
    }
    minimoInput.addEventListener('input', actualizarRango);
    maximoInput.addEventListener('input', actualizarRango);

    // ── Contador de días hasta vencimiento ──
    function validarFecha() {
        const seleccionada = new Date(fechaInput.value);
        const ahora = new Date();
        // Limpiar horas para comparar solo fechas
        ahora.setHours(0, 0, 0, 0);
        seleccionada.setHours(0, 0, 0, 0);

        if (seleccionada > ahora) {
            const dias = Math.ceil((seleccionada - ahora) / (1000 * 60 * 60 * 24));
            if (dias < 7) {
                fechaInput.classList.add('is-invalid');
                fechaInput.classList.remove('is-valid');
                document.getElementById('feedbackFecha').textContent =
                    'La fecha de expiración debe ser al menos 7 días en el futuro. Quedan ' + dias + ' días.';
                document.getElementById('feedbackFecha').style.display = '';
                diasCard.style.display = 'none';
                return false;
            } else {
                fechaInput.classList.remove('is-invalid');
                fechaInput.classList.add('is-valid');
                document.getElementById('feedbackFecha').style.display = 'none';
                diasCard.style.display = '';
                textoDias.textContent = dias + ' día(s)';
                textoDias.className = 'fw-bold fs-5 ' + (dias <= 30 ? 'text-warning' : 'text-success');
                return true;
            }
        } else {
            fechaInput.classList.add('is-invalid');
            fechaInput.classList.remove('is-valid');
            document.getElementById('feedbackFecha').textContent =
                'La fecha de expiración debe ser una fecha futura.';
            document.getElementById('feedbackFecha').style.display = '';
            diasCard.style.display = 'none';
            return false;
        }
    }

    fechaInput.addEventListener('change', validarFecha);
    fechaInput.addEventListener('input', validarFecha);

    // ── Validación final ─────────────────────────
    document.getElementById('formProducto').addEventListener('submit', function (e) {
        let valido = true;

        // Campos required vacíos
        this.querySelectorAll('[required]').forEach(campo => {
            if (!campo.value.trim()) {
                campo.classList.add('is-invalid');
                valido = false;
            }
        });

        // Stock min < max
        const min = parseInt(minimoInput.value) || 0;
        const max = parseInt(maximoInput.value) || 0;
        if (min >= max) {
            minimoInput.classList.add('is-invalid');
            maximoInput.classList.add('is-invalid');
            valido = false;
        }

        // Fecha válida (>= 7 días)
        if (!validarFecha()) {
            valido = false;
        }

        if (!valido) {
            e.preventDefault();
            window.scrollTo({ top: 0, behavior: 'smooth' });
        }
    });
});