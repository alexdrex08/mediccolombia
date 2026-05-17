package com.sena.meciccolombia.mediccolombia.web.controller;

import com.sena.meciccolombia.mediccolombia.web.dto.request.ProductoCreateRequestDto;
import com.sena.meciccolombia.mediccolombia.web.dto.request.ProductoUpdateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProductoDetalleDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProductoHistorialDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProductoResumenDTO;
import com.sena.meciccolombia.mediccolombia.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoDetalleDTO> crearProducto(@RequestBody ProductoCreateRequestDto dto) {
        ProductoDetalleDTO creado = productoService.crearProducto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDetalleDTO> actualizarProducto(
            @PathVariable Long id,
            @RequestBody ProductoUpdateRequestDTO dto) {
        ProductoDetalleDTO actualizado = productoService.actualizarProducto(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDetalleDTO> obtenerPorId(@PathVariable Long id) {
        ProductoDetalleDTO producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }
     @GetMapping("/{id}/historial")
    public ResponseEntity<ProductoHistorialDTO> obtenerHistorial(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.productoHistorial(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductoResumenDTO>> listarProductos() {
        List<ProductoResumenDTO> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoResumenDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<ProductoResumenDTO> productos = productoService.buscarPorNombre(nombre);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<ProductoResumenDTO>> filtrarPorCategoria(@PathVariable Long idCategoria) {
        List<ProductoResumenDTO> productos = productoService.filtrarPorCategoria(idCategoria);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<ProductoResumenDTO>> productosConStockBajo() {
        List<ProductoResumenDTO> productos = productoService.productosConStockBajo();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/proximos-a-vencer")
    public ResponseEntity<List<ProductoResumenDTO>> productosProximosAVencer(@RequestParam int dias) {
        List<ProductoResumenDTO> productos = productoService.productosProximosAVencer(dias);
        return ResponseEntity.ok(productos);
    }
}