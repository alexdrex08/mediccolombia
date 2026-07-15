function actualizarHora() {
    document.getElementById('fechaHoraActual').textContent =
        new Date().toLocaleDateString('es-CO', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })
        + ' — ' + new Date().toLocaleTimeString('es-CO');
}
actualizarHora();
setInterval(actualizarHora, 1000);