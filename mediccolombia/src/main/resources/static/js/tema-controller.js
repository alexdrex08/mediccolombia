
(function () {
    'use strict';

    // ── Helpers ──────────────────────────────────────────────────
    function getUserId() {
        return window.MC_USER_ID || 'guest';
    }

    function getStorageKey() {
        return 'mc-tema-' + getUserId();
    }

    function getTemaActual() {
        return document.documentElement.getAttribute('data-theme') === 'dark'
            ? 'oscuro' : 'claro';
    }

    function aplicarTema(tema, conTransicion) {
        if (conTransicion) {
            document.body.classList.add('tema-cargado');
        }

        if (tema === 'oscuro' || tema === 'dark') {
            document.documentElement.setAttribute('data-theme', 'dark');
        } else {
            document.documentElement.removeAttribute('data-theme');
        }

        var key = getStorageKey();
        sessionStorage.setItem(key, tema);
        localStorage.setItem(key, tema);

        actualizarSelectores(tema);
    }

    function actualizarSelectores(tema) {
        var selectores = document.querySelectorAll('#selectTema, [data-tema-select]');
        selectores.forEach(function (sel) {
            sel.value = tema;
        });
    }

    // ── Sincronización con el backend (en background) ─────────────

    function guardarTemaEnBackend(tema) {
        return fetch('/api/configuracion/preferencias/tema', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ valor: tema })
        }).catch(function () {
            console.warn('No se pudo sincronizar el tema con el servidor.');
        });
    }

    // ── API pública ───────────────────────────────────────────────
    window.MedicolombiaTheme = {

        /**
         * Aplicar un tema (sin guardarlo en BD).
         * Usado para preview al cambiar el select.
         */
        preview: function (tema) {
            aplicarTema(tema, true);
        },

        /**
         * Guardar el tema seleccionado en storages Y en el backend.
         * Llamar desde el botón "Guardar cambios" de configuracion-perfil.
         */
        guardar: function (tema) {
            aplicarTema(tema, true);
            return guardarTemaEnBackend(tema);
        },

        /**
         * Obtener el tema actualmente aplicado ('claro' | 'oscuro').
         */
        actual: function () {
            return getTemaActual();
        },

        /**
         * Inicializar: activar transiciones y sincronizar selectores.
         * Llamar en DOMContentLoaded desde el fragment scripts.html.
         */
        init: function () {
            requestAnimationFrame(function () {
                requestAnimationFrame(function () {
                    document.body.classList.add('tema-cargado');
                });
            });

            actualizarSelectores(getTemaActual());
        }
    };

    // ── Inicializar automáticamente al cargar el DOM ──────────────
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', window.MedicolombiaTheme.init);
    } else {
        window.MedicolombiaTheme.init();
    }

})();