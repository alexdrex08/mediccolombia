
// =============================================
// UTILIDADES
// =============================================
function mostrarMensaje(texto, tipo = 'success') {
    const div = document.getElementById('mensajeAjax');
    const span = document.getElementById('textoMensajeAjax');
    const icon = document.getElementById('iconoMensaje');
    div.className = `alert alert-${tipo} py-2 mb-3`;
    icon.className = tipo === 'success'
        ? 'fa-solid fa-circle-check me-2'
        : 'fa-solid fa-circle-exclamation me-2';
    span.textContent = texto;
    div.classList.remove('d-none');
    setTimeout(() => div.classList.add('d-none'), 3000);
}

function cerrarModal(id) {
    bootstrap.Modal.getInstance(document.getElementById(id))?.hide();
}

async function postAPI(url, body) {
    const res = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
    });
    if (!res.ok) {
        const txt = await res.text();
        throw new Error(txt || `Error ${res.status}`);
    }
    return res.status === 204 ? null : res.json();
}

async function deleteAPI(url) {
    const res = await fetch(url, { method: 'DELETE' });
    if (!res.ok) throw new Error(`Error ${res.status}`);
}
