async function guardarProveedor() {
    const nombre = document.getElementById('nombreProv').value.trim();
    const nit = document.getElementById('nit').value.trim();
    const id = document.getElementById('proveedorId').value;
    const esEdicion = document.getElementById('esModoEdicion').value === 'true';

    if (!nombre || !nit) {
        mostrarError('Completa todos los campos obligatorios.'); return;
    }

    const btn = document.getElementById('btnGuardar');
    btn.disabled = true;
    btn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Guardando...';

    try {
        const url = esEdicion ? `/api/proveedores/${id}` : '/api/proveedores';
        const method = esEdicion ? 'PUT' : 'POST';

        const res = await fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ nombreProv: nombre, nit })
        });

        if (!res.ok) {
            const txt = await res.text();
            throw new Error(txt || 'Error al guardar');
        }

        const data = await res.json();
        mostrarExito(esEdicion
            ? 'Proveedor actualizado correctamente.'
            : 'Proveedor registrado correctamente.');

        setTimeout(() => {
            window.location.href = `/proveedores/${data.id}`;
        }, 1000);

    } catch (e) {
        mostrarError(e.message || 'Error inesperado.');
        btn.disabled = false;
        btn.innerHTML = esEdicion
            ? '<i class="fa-solid fa-floppy-disk me-2"></i>Guardar Cambios'
            : '<i class="fa-solid fa-floppy-disk me-2"></i>Registrar Proveedor';
    }
}