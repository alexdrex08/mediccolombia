package com.sena.meciccolombia.mediccolombia.web.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.service.TipoProyeccionService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoProyeccionRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoProyeccionResponseDTO;

@RestController
@RequestMapping("/api/tipo_proyeccions")
@RequiredArgsConstructor
public class TipoProyeccionController {

    private final TipoProyeccionService tipo_proyeccionService;

    @PostMapping
    public ResponseEntity<TipoProyeccionResponseDTO> crear(@RequestBody TipoProyeccionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipo_proyeccionService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoProyeccionResponseDTO> actualizar(@PathVariable Long id, @RequestBody TipoProyeccionRequestDTO dto) {
        return ResponseEntity.ok(tipo_proyeccionService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tipo_proyeccionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoProyeccionResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tipo_proyeccionService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TipoProyeccionResponseDTO>> listar() {
        return ResponseEntity.ok(tipo_proyeccionService.listar());
    }
}
