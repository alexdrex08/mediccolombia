
    // Filtramos SOLO los elementos que tienen data-bs-target y que apuntan a #collapse-
    document.querySelectorAll('[data-bs-toggle="collapse"]').forEach(function (header) {
        const targetId = header.getAttribute('data-bs-target');

        // Verificar que targetId existe y empieza con #collapse-
        if (targetId && targetId.startsWith('#collapse-')) {
            const collapseElement = document.querySelector(targetId);
            const icon = document.getElementById('icon-' + targetId.replace('#collapse-', ''));
            if (collapseElement && icon) {
                collapseElement.addEventListener('shown.bs.collapse', function () {
                    icon.style.transform = 'rotate(180deg)';
                });
                collapseElement.addEventListener('hidden.bs.collapse', function () {
                    icon.style.transform = 'rotate(0deg)';
                });
            }
        }
    });
