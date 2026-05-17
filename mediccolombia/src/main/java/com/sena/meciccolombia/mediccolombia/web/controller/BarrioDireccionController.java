package com.sena.meciccolombia.mediccolombia.web.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.service.BarrioDireccionService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.BarrioDireccionRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.BarrioDireccionResponseDTO;

@RestController
@RequestMapping("/api/barrio_direccions")
@RequiredArgsConstructor
public class BarrioDireccionController {

    private final BarrioDireccionService barrioDireccionService;

    @PostMapping
    public ResponseEntity<BarrioDireccionResponseDTO> crear(@RequestBody BarrioDireccionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(barrioDireccionService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BarrioDireccionResponseDTO> actualizar(@PathVariable Long id, @RequestBody BarrioDireccionRequestDTO dto) {
        return ResponseEntity.ok(barrioDireccionService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        barrioDireccionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarrioDireccionResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(barrioDireccionService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<BarrioDireccionResponseDTO>> listar() {
        return ResponseEntity.ok(barrioDireccionService.listar());
    }
}
