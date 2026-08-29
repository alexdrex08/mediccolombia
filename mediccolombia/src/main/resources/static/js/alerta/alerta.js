function prepararResolver(id, nombreProducto) {
    mostrarModalConfirmacion(
        'Resolver alerta',
        `¿Deseas resolver la alerta del producto <strong>${nombreProducto || '#' + id}</strong>?<br><br>
         <span class="text-warning fw-semibold">
             <i class="fa-solid fa-triangle-exclamation me-1"></i>
             Al resolverla, la alerta quedará marcada como resuelta y no volverá a aparecer en el listado de activas.
         </span>`,
        'warning',
        function () {
            // Usar el formulario en lugar de fetch
            const form = document.getElementById('formResolverAlerta');
            form.action = `/productos/alertas/${id}/resolver`;
            form.submit();  
        },
        'Sí, resolver alerta'
    );
}