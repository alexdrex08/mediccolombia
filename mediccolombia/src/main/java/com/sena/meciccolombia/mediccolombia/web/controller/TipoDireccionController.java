package com.sena.meciccolombia.mediccolombia.web.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.service.TipoDireccionService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoDireccionRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoDireccionResponseDTO;

@RestController
@RequestMapping("/api/tipo_direccions")
@RequiredArgsConstructor
public class TipoDireccionController {

    private final TipoDireccionService tipo_direccionService;

    @PostMapping
    public ResponseEntity<TipoDireccionResponseDTO> crear(@RequestBody TipoDireccionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipo_direccionService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoDireccionResponseDTO> actualizar(@PathVariable Long id, @RequestBody TipoDireccionRequestDTO dto) {
        return ResponseEntity.ok(tipo_direccionService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tipo_direccionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoDireccionResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tipo_direccionService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TipoDireccionResponseDTO>> listar() {
        return ResponseEntity.ok(tipo_direccionService.listar());
    }
}
