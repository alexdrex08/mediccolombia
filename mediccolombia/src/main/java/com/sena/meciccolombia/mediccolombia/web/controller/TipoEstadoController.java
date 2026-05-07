package com.sena.meciccolombia.mediccolombia.web.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.service.TipoEstadoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoEstadoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoEstadoResponseDTO;

@RestController
@RequestMapping("/api/tipo_estados")
@RequiredArgsConstructor
public class TipoEstadoController {

    private final TipoEstadoService tipo_estadoService;

    @PostMapping
    public ResponseEntity<TipoEstadoResponseDTO> crear(@RequestBody TipoEstadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipo_estadoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoEstadoResponseDTO> actualizar(@PathVariable Long id, @RequestBody TipoEstadoRequestDTO dto) {
        return ResponseEntity.ok(tipo_estadoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tipo_estadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoEstadoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tipo_estadoService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TipoEstadoResponseDTO>> listar() {
        return ResponseEntity.ok(tipo_estadoService.listar());
    }
}
