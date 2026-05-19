package com.sena.meciccolombia.mediccolombia.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.meciccolombia.mediccolombia.service.IDetalleProveedorProductoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.ActualizarPrecioRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DetalleProveedorProductoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DetalleProveedorProductoResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/detalle-proveedor-producto")
@RequiredArgsConstructor
public class DetalleProveedorProductoController {

    private final IDetalleProveedorProductoService detalleProveedorProductoService;

    @PostMapping
    public ResponseEntity<DetalleProveedorProductoResponseDTO> asignar(
            @RequestBody DetalleProveedorProductoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(detalleProveedorProductoService.asignar(dto));
    }

    @PatchMapping("/{idProveedor}/{idProducto}/precio")
    public ResponseEntity<DetalleProveedorProductoResponseDTO> actualizarPrecio(
            @PathVariable Long idProveedor,
            @PathVariable Long idProducto,
            @RequestBody ActualizarPrecioRequestDTO dto) {
        return ResponseEntity.ok(detalleProveedorProductoService.actualizarPrecio(idProveedor, idProducto, dto.getNuevoPrecio()));
    }

    @DeleteMapping("/{idProveedor}/{idProducto}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long idProveedor,
            @PathVariable Long idProducto) {
        detalleProveedorProductoService.eliminar(idProveedor, idProducto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idProveedor}/{idProducto}")
    public ResponseEntity<DetalleProveedorProductoResponseDTO> obtenerPorId(
            @PathVariable Long idProveedor,
            @PathVariable Long idProducto) {
        return ResponseEntity.ok(detalleProveedorProductoService.obtenerPorId(idProveedor, idProducto));
    }

    @GetMapping("/proveedor/{idProveedor}")
    public ResponseEntity<List<DetalleProveedorProductoResponseDTO>> listarPorProveedor(
            @PathVariable Long idProveedor) {
        return ResponseEntity.ok(detalleProveedorProductoService.listarPorProveedor(idProveedor));
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<DetalleProveedorProductoResponseDTO>> listarPorProducto(
            @PathVariable Long idProducto) {
        return ResponseEntity.ok(detalleProveedorProductoService.listarPorProducto(idProducto));
    }
}