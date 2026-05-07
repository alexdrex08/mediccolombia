package com.sena.meciccolombia.mediccolombia.web.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.service.TipoCorreoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoCorreoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoCorreoResponseDTO;

@RestController
@RequestMapping("/api/tipo_correos")
@RequiredArgsConstructor
public class TipoCorreoController {

    private final TipoCorreoService tipo_correoService;

    @PostMapping
    public ResponseEntity<TipoCorreoResponseDTO> crear(@RequestBody TipoCorreoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipo_correoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoCorreoResponseDTO> actualizar(@PathVariable Long id, @RequestBody TipoCorreoRequestDTO dto) {
        return ResponseEntity.ok(tipo_correoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tipo_correoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoCorreoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tipo_correoService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TipoCorreoResponseDTO>> listar() {
        return ResponseEntity.ok(tipo_correoService.listar());
    }
}
