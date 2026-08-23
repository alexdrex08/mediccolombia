(function () {
    'use strict';

    function getKey() {
        var uid = window.MC_USER_ID || 'guest';
        return 'mc-tema-' + uid;
    }

    function aplicarTema(tema) {
        if (tema === 'oscuro' || tema === 'dark') {
            document.documentElement.setAttribute('data-theme', 'dark');
        } else {
            document.documentElement.removeAttribute('data-theme');
        }
    }

    var key = getKey();
    var temaSession = sessionStorage.getItem(key);
    if (temaSession) {
        aplicarTema(temaSession);
        return;
    }

    var temaLocal = localStorage.getItem(key);
    if (temaLocal) {
        aplicarTema(temaLocal);
        sessionStorage.setItem(key, temaLocal);
        return;
    }

})();