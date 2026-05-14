package com.sena.meciccolombia.mediccolombia.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.meciccolombia.mediccolombia.service.ProveedorService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.ProveedorRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProveedorDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProveedorResponseDTO;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<ProveedorResponseDTO> crear(@RequestBody ProveedorRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> actualizar(@PathVariable Long id, @RequestBody ProveedorRequestDTO dto) {
        return ResponseEntity.ok(proveedorService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        proveedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proveedorService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ProveedorResponseDTO>> listar() {
        return ResponseEntity.ok(proveedorService.listar());
    }

    @GetMapping("/nit/{nit}")
    public ResponseEntity<ProveedorResponseDTO> buscarPorNit(@PathVariable String nit) {
        return ResponseEntity.ok(proveedorService.buscarPorNit(nit));
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<ProveedorDetalleResponseDTO> obtenerDetalle(@PathVariable Long id) {
        return ResponseEntity.ok(proveedorService.obtenerDetalles(id));
    }
    
}
