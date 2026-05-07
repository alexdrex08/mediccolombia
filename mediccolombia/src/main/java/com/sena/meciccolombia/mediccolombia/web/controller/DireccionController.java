package com.sena.meciccolombia.mediccolombia.web.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.service.DireccionService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DireccionRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DireccionUpdateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DireccionResponseDTO;

@RestController
@RequestMapping("/api/direccions")
@RequiredArgsConstructor
public class DireccionController {

    private final DireccionService direccionService;

    @PostMapping
    public ResponseEntity<DireccionResponseDTO> crear(@RequestBody DireccionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(direccionService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DireccionResponseDTO> actualizar(@PathVariable Long id, @RequestBody DireccionUpdateRequestDTO dto) {
        return ResponseEntity.ok(direccionService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        direccionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DireccionResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(direccionService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<DireccionResponseDTO>> listar() {
        return ResponseEntity.ok(direccionService.listar());
    }
}
