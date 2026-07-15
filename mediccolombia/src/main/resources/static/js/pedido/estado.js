// =============================================
// BLOQUE 1: ESTADO CENTRAL DEL PEDIDO
// =============================================

// Objeto principal que contiene todo el estado de la aplicación
// items: array de productos agregados al carrito
// proveedor: { id, nombre } del proveedor activo (el del primer item)
const pedido = {
    items: [],
    proveedor: null
};

// Variable para almacenar el producto seleccionado en el Tab 2
// se usa para mantener referencia cuando se está comparando precios
let articuloActual = null;

// Cache del catálogo del proveedor seleccionado en el Tab 1
// se usa para evitar múltiples llamadas a la API
let catalogoActual = {};

// js/pedido/estado.js
console.log('estado.js cargado');