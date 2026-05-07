package com.sena.meciccolombia.mediccolombia.web.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.service.TipoMovimientoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoMovimientoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoMovimientoResponseDTO;

@RestController
@RequestMapping("/api/tipo_movimientos")
@RequiredArgsConstructor
public class TipoMovimientoController {

    private final TipoMovimientoService tipo_movimientoService;

    @PostMapping
    public ResponseEntity<TipoMovimientoResponseDTO> crear(@RequestBody TipoMovimientoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipo_movimientoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoMovimientoResponseDTO> actualizar(@PathVariable Long id, @RequestBody TipoMovimientoRequestDTO dto) {
        return ResponseEntity.ok(tipo_movimientoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tipo_movimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoMovimientoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tipo_movimientoService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TipoMovimientoResponseDTO>> listar() {
        return ResponseEntity.ok(tipo_movimientoService.listar());
    }
}
