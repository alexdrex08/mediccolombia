package com.sena.meciccolombia.mediccolombia.web.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.service.FiltroBusquedaService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.FiltroBusquedaRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.FiltroBusquedaDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.FiltroBusquedaResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/filtro_busqueda")
@RequiredArgsConstructor
public class FiltroBusquedaController {

    private final FiltroBusquedaService filtroBusquedaService;

    @PostMapping
    public ResponseEntity<FiltroBusquedaResponseDTO> crear(@RequestBody FiltroBusquedaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(filtroBusquedaService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FiltroBusquedaResponseDTO> actualizar(@PathVariable Long id, @RequestBody FiltroBusquedaRequestDTO dto) {
        return ResponseEntity.ok(filtroBusquedaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        filtroBusquedaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FiltroBusquedaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(filtroBusquedaService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<FiltroBusquedaResponseDTO>> listar() {
        return ResponseEntity.ok(filtroBusquedaService.listar());
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<FiltroBusquedaDetalleResponseDTO> obtenerDetalle(@PathVariable Long id) {
        return ResponseEntity.ok(filtroBusquedaService.obtenerDetalle(id));
    }
    
}
