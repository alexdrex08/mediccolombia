package com.sena.meciccolombia.mediccolombia.web.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.service.CorreoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.CorreoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.CorreoUpdateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.CorreoResponseDTO;

@RestController
@RequestMapping("/api/correos")
@RequiredArgsConstructor
public class CorreoController {

    private final CorreoService correoService;

    @PostMapping
    public ResponseEntity<CorreoResponseDTO> crear(@RequestBody CorreoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(correoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorreoResponseDTO> actualizar(@PathVariable Long id, @RequestBody CorreoUpdateRequestDTO dto) {
        return ResponseEntity.ok(correoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        correoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorreoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(correoService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<CorreoResponseDTO>> listar() {
        return ResponseEntity.ok(correoService.listar());
    }
}
