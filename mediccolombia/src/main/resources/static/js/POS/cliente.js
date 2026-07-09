// =============================================
// CLIENTE — AJAX
// =============================================

async function buscarCliente() {
    const doc = document.getElementById('inputDocumentoCliente').value.trim();
    if (!doc) { mostrarMensaje("Sistema", 'Ingresa un número de documento.'); return; }
    try {
        // GET /api/clientes/identificacion/{identificacion}
        const res = await fetch(`/api/clientes/identificacion/${encodeURIComponent(doc)}`);
        if (!res.ok) {
            // 404
            document.getElementById('formNuevoCliente').classList.remove('d-none');
            document.getElementById('clienteEncontrado').classList.add('d-none');
            return;
        }
        // ClienteResponseDTO: { id, nombreCliente, identificacion }
        const cliente = await res.json();
        asignarCliente(cliente);
    } catch (e) {
        document.getElementById('formNuevoCliente').classList.remove('d-none');
    }
}

function asignarCliente(cliente) {
    venta.cliente = { id: cliente.id, nombreCliente: cliente.nombreCliente, identificacion: cliente.identificacion };
    document.getElementById('nombreClienteEncontrado').textContent = cliente.nombreCliente;
    document.getElementById('docClienteEncontrado').textContent = '— ' + cliente.identificacion;
    document.getElementById('clienteEncontrado').classList.remove('d-none');
    document.getElementById('formNuevoCliente').classList.add('d-none');
    document.getElementById('resumenCliente').textContent = cliente.nombreCliente;
    document.getElementById('resumenCliente').classList.remove('text-muted', 'fst-italic');
}

async function guardarNuevoCliente() {
    const nombreCliente = document.getElementById('nuevoNombreCliente').value.trim();
    const identificacion = document.getElementById('inputDocumentoCliente').value.trim();

    if (!nombreCliente) { mostrarMensaje("Sistema", 'Ingresa el nombre completo del cliente.'); return; }
    if (!identificacion) { mostrarMensaje("Sistema", 'El número de documento no puede estar vacío.'); return; }

    try {
        // POST /api/clientes — body según ClienteResquestDTO: { nombreCliente, identificacion }
        const res = await fetch('/api/clientes', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ nombreCliente, identificacion })
        });
        if (!res.ok) {
            const err = await res.json().catch(() => ({}));
            mostrarMensaje("Sistema", 'Error al guardar el cliente: ' + (err.message ?? res.status));
            return;
        }
        // Respuesta: ClienteResponseDTO { id, nombreCliente, identificacion }
        const clienteGuardado = await res.json();
        asignarCliente(clienteGuardado);
    } catch (e) {
        mostrarMensaje("Sistema", 'Error de conexión al guardar el cliente.');
        console.error(e);
    }
}

function limpiarCliente() {
    venta.cliente = null;
    document.getElementById('clienteEncontrado').classList.add('d-none');
    document.getElementById('formNuevoCliente').classList.add('d-none');
    document.getElementById('inputDocumentoCliente').value = '';
    document.getElementById('nuevoNombreCliente').value = '';
    document.getElementById('resumenCliente').textContent = 'Sin cliente asignado';
    document.getElementById('resumenCliente').classList.add('text-muted', 'fst-italic');
}