document.addEventListener('DOMContentLoaded', function () {
    // Obtener el botón de toggle y el body
    var sidebarToggle = document.getElementById('sidebarToggle');
    var body = document.querySelector('body');

    if (sidebarToggle) {
        sidebarToggle.addEventListener('click', () => {
            if (window.innerWidth <= 992) {
                document.body.classList.toggle('show-sidebar');
            } else {
                document.body.classList.toggle('toggled');
            }
        });
    }
    document.addEventListener('click', (e) => {
        if (
            document.body.classList.contains('show-sidebar') &&
            !document.getElementById('sidebar-wrapper').contains(e.target) &&
            !sidebarToggle.contains(e.target)
        ) {
            document.body.classList.remove('show-sidebar');
        }
    });
});