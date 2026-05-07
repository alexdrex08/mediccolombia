package com.sena.meciccolombia.mediccolombia.web.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.service.TelefonoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TelefonoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TelefonoUpdateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TelefonoResponseDTO;

@RestController
@RequestMapping("/api/telefonos")
@RequiredArgsConstructor
public class TelefonoController {

    private final TelefonoService telefonoService;

    @PostMapping
    public ResponseEntity<TelefonoResponseDTO> crear(@RequestBody TelefonoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(telefonoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TelefonoResponseDTO> actualizar(@PathVariable Long id, @RequestBody TelefonoUpdateRequestDTO dto) {
        return ResponseEntity.ok(telefonoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        telefonoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelefonoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(telefonoService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TelefonoResponseDTO>> listar() {
        return ResponseEntity.ok(telefonoService.listar());
    }
}
