async function guardarCliente() {
    const nombre = document.getElementById('nombreCliente').value.trim();
    const identificacion = document.getElementById('identificacion').value.trim();
    const id = document.getElementById('clienteId').value;
    const esEdicion = document.getElementById('esModoEdicion').value === 'true';

    if (!nombre || !identificacion) {
        mostrarError('Completa todos los campos obligatorios.');
        return;
    }

    const btn = document.getElementById('btnGuardar');
    btn.disabled = true;
    btn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Guardando...';

    try {
        const url = esEdicion ? `/api/clientes/${id}` : '/api/clientes';
        const method = esEdicion ? 'PUT' : 'POST';

        const res = await fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ nombreCliente: nombre, identificacion })
        });

        if (!res.ok) {
            const txt = await res.text();
            throw new Error(txt || 'Error al guardar el cliente');
        }

        const data = await res.json();
        mostrarExito(esEdicion
            ? 'Cliente actualizado correctamente.'
            : 'Cliente registrado correctamente.');

        setTimeout(() => {
            window.location.href = `/clientes/${data.id}`;
        }, 1000);

    } catch (e) {
        mostrarError(e.message || 'Ocurrió un error inesperado.');
        btn.disabled = false;
        btn.innerHTML = esEdicion
            ? '<i class="fa-solid fa-floppy-disk me-2"></i>Guardar Cambios'
            : '<i class="fa-solid fa-floppy-disk me-2"></i>Registrar Cliente';
    }
}