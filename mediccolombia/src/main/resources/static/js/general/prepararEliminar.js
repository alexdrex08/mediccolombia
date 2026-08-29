/**
 * @param {Object} config - Configuración del modal
 * @param {string|number} config.id - ID de la entidad a eliminar
 * @param {string} config.nombre - Nombre a mostrar en el modal
 * @param {string} config.urlPattern - Ejemplo: '/productos/eliminar/{id}' o '/clientes/{id}/eliminar'
 * @param {string} [config.modalId='modalEliminar'] - ID del modal (opcional)
 * @param {string} [config.nombreElemId='nombreModal'] - ID de la etiqueta del nombre (opcional)
 * @param {string} [config.formElemId='formEliminar'] - ID del formulario (opcional)
 */
function prepararEliminar({ id, nombre, urlPattern, modalId = 'modalEliminar', nombreElemId = 'nombreModal', formElemId = 'formEliminar' }) {
    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1)) || '';
    
    const urlFinal = `${contextPath}${urlPattern.replace('{id}', id)}`;

    const nombreElem = document.getElementById(nombreElemId);
    const formElem = document.getElementById(formElemId);
    const modalElem = document.getElementById(modalId);

    if (nombreElem) nombreElem.textContent = nombre;
    if (formElem) formElem.action = urlFinal;

    if (modalElem) {
        const modal = bootstrap.Modal.getOrCreateInstance(modalElem);
        modal.show();
    }
}