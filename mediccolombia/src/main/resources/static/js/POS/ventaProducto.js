// =============================================
// BÚSQUEDA AJAX DE PRODUCTO
// =============================================


let productoActual = null;
const inputBuscar = document.getElementById('inputBuscarProducto');
const resultados = document.getElementById('resultadosBusqueda');

let debounce;
inputBuscar.addEventListener('input', function () {
    clearTimeout(debounce);
    const q = this.value.trim();
    if (q.length < 2) { resultados.style.display = 'none'; return; }
    debounce = setTimeout(() => buscarProducto(q), 300);
});

async function buscarProducto(nombre) {
    try {
        // GET /api/productos/buscar?nombre=...
        const res = await fetch(`/api/productos/buscar?nombre=${encodeURIComponent(nombre)}`);
        if (!res.ok) throw new Error();

        const lista = await res.json();
        resultados.innerHTML = '';

        if (lista.length === 0) {
            resultados.innerHTML = '<div class="list-group-item text-muted small">Sin resultados</div>';
        } else {

            lista.forEach(p => {
                let vencido = false;

                if (p.fechaExpiracion) {
                    const hoy = new Date();
                    hoy.setHours(0, 0, 0, 0);

                    const fechaExp = new Date(p.fechaExpiracion);
                    fechaExp.setHours(0, 0, 0, 0);

                    vencido = fechaExp < hoy;
                }

                let badgeClase;
                let badgeTexto;

                if (vencido) {
                    badgeClase = "bg-danger";
                    badgeTexto = "VENCIDO";
                } else if (p.stock > 0) {
                    badgeClase = "bg-success";
                    badgeTexto = `Stock: ${p.stock}`;
                } else {
                    badgeClase = "bg-secondary";
                    badgeTexto = "Sin stock";
                }

                const btn = document.createElement('button');
                btn.type = 'button';
                btn.className = 'list-group-item list-group-item-action';

                btn.innerHTML = `
                    <div class="d-flex justify-content-between align-items-center">
                        <span class="fw-bold">${p.nombreProducto}</span>
                        <span class="badge ${badgeClase} ms-2">${badgeTexto}</span>
                    </div>
                    <small class="text-muted">${p.lote ?? ''}</small>`;

                btn.onclick = () => seleccionarProducto(p);

                resultados.appendChild(btn);
            });
        }

        resultados.style.display = 'block';

    } catch (e) {
        console.error('Error buscando producto:', e);
    }
}

function seleccionarProducto(p) {
    productoActual = p;
    inputBuscar.value = p.nombreProducto;
    resultados.style.display = 'none';
    document.getElementById('nombreProductoSeleccionado').textContent = p.nombreProducto;
    document.getElementById('stockProductoSeleccionado').textContent = p.stock;
    document.getElementById('productoSeleccionado').classList.remove('d-none');
}

document.addEventListener('click', e => {
    if (!inputBuscar.contains(e.target)) resultados.style.display = 'none';
});
