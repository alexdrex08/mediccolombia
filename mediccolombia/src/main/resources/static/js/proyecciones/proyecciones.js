(function () {
    const fechaInicio = document.getElementById('fechaInicio');
    const fechaFin = document.getElementById('fechaFin');
    const previoPeriodo = document.getElementById('previoPeriodo');
    const textoPeriodo = document.getElementById('textoPeriodo');
    const form = document.getElementById('formProyeccion');

    // ── Accesos rápidos ──────────────────────────────────────────────
    function pad(n) { return String(n).padStart(2, '0'); }
    function fmtLocal(d) {
        return d.getFullYear() + '-' + pad(d.getMonth() + 1) + '-' + pad(d.getDate())
            + 'T' + pad(d.getHours()) + ':' + pad(d.getMinutes());
    }

    document.getElementById('btnMesAnterior').addEventListener('click', () => {
        const hoy = new Date();
        const ini = new Date(hoy.getFullYear(), hoy.getMonth() - 1, 1, 0, 0);
        const fin = new Date(hoy.getFullYear(), hoy.getMonth(), 0, 23, 59);
        fechaInicio.value = fmtLocal(ini);
        fechaFin.value = fmtLocal(fin);
        actualizarPreview();
    });

    document.getElementById('btnMesActual').addEventListener('click', () => {
        const hoy = new Date();
        const ini = new Date(hoy.getFullYear(), hoy.getMonth(), 1, 0, 0);
        const fin = new Date(hoy.getFullYear(), hoy.getMonth() + 1, 0, 23, 59);
        fechaInicio.value = fmtLocal(ini);
        fechaFin.value = fmtLocal(fin);
        actualizarPreview();
    });

    document.getElementById('btnUltimos30').addEventListener('click', () => {
        const fin = new Date(); fin.setHours(23, 59);
        const ini = new Date(); ini.setDate(ini.getDate() - 30); ini.setHours(0, 0);
        fechaInicio.value = fmtLocal(ini);
        fechaFin.value = fmtLocal(fin);
        actualizarPreview();
    });

    document.getElementById('btnUltimos90').addEventListener('click', () => {
        const fin = new Date(); fin.setHours(23, 59);
        const ini = new Date(); ini.setDate(ini.getDate() - 90); ini.setHours(0, 0);
        fechaInicio.value = fmtLocal(ini);
        fechaFin.value = fmtLocal(fin);
        actualizarPreview();
    });

    // ── Preview del período ──────────────────────────────────────────
    function actualizarPreview() {
        const ini = fechaInicio.value;
        const fin = fechaFin.value;
        if (ini && fin) {
            const diasMs = new Date(fin) - new Date(ini);
            const dias = Math.ceil(diasMs / (1000 * 60 * 60 * 24));
            previoPeriodo.style.display = '';
            textoPeriodo.textContent = ini.slice(0, 10).split('-').reverse().join('/') +
                ' → ' + fin.slice(0, 10).split('-').reverse().join('/') +
                '  (' + dias + ' día(s))';

            // Validar que fin >= inicio
            if (new Date(fin) < new Date(ini)) {
                fechaFin.classList.add('is-invalid');
                fechaInicio.classList.add('is-invalid');
            } else {
                fechaFin.classList.remove('is-invalid');
                fechaInicio.classList.remove('is-invalid');
                fechaFin.classList.add('is-valid');
                fechaInicio.classList.add('is-valid');
            }
        } else {
            previoPeriodo.style.display = 'none';
        }
    }

    fechaInicio.addEventListener('change', actualizarPreview);
    fechaFin.addEventListener('change', actualizarPreview);

    // ── Validación antes de enviar ───────────────────────────────────
    form.addEventListener('submit', function (e) {
        let valido = true;

        const tipoChecked = document.querySelector('input[name="idTipoProyeccion"]:checked');
        if (!tipoChecked) {
            document.getElementById('feedbackTipo').style.removeProperty('display');
            valido = false;
        }

        if (!fechaInicio.value) { fechaInicio.classList.add('is-invalid'); valido = false; }
        if (!fechaFin.value) { fechaFin.classList.add('is-invalid'); valido = false; }

        if (fechaInicio.value && fechaFin.value && new Date(fechaFin.value) < new Date(fechaInicio.value)) {
            fechaFin.classList.add('is-invalid');
            valido = false;
        }

        if (!valido) {
            e.preventDefault();
            window.scrollTo({ top: 0, behavior: 'smooth' });
        }
    });

})();