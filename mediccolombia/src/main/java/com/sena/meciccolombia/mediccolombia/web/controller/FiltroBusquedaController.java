package com.sena.meciccolombia.mediccolombia.web.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.service.FiltroBusquedaService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.FiltroBusquedaRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.FiltroBusquedaResponseDTO;

@RestController
@RequestMapping("/api/filtro_busquedas")
@RequiredArgsConstructor
public class FiltroBusquedaController {

    private final FiltroBusquedaService filtro_busquedaService;

    @PostMapping
    public ResponseEntity<FiltroBusquedaResponseDTO> crear(@RequestBody FiltroBusquedaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(filtro_busquedaService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FiltroBusquedaResponseDTO> actualizar(@PathVariable Long id, @RequestBody FiltroBusquedaRequestDTO dto) {
        return ResponseEntity.ok(filtro_busquedaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        filtro_busquedaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FiltroBusquedaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(filtro_busquedaService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<FiltroBusquedaResponseDTO>> listar() {
        return ResponseEntity.ok(filtro_busquedaService.listar());
    }
}
