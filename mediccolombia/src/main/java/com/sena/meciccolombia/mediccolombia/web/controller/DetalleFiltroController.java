package com.sena.meciccolombia.mediccolombia.web.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.service.DetalleFiltroService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DetalleFiltroRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DetalleFiltroResponseDTO;

@RestController
@RequestMapping("/api/detalle_filtros")
@RequiredArgsConstructor
public class DetalleFiltroController {

    private final DetalleFiltroService detalle_filtroService;

    @PostMapping
    public ResponseEntity<DetalleFiltroResponseDTO> crear(@RequestBody DetalleFiltroRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(detalle_filtroService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleFiltroResponseDTO> actualizar(@PathVariable Long id, @RequestBody DetalleFiltroRequestDTO dto) {
        return ResponseEntity.ok(detalle_filtroService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        detalle_filtroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleFiltroResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(detalle_filtroService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<DetalleFiltroResponseDTO>> listar() {
        return ResponseEntity.ok(detalle_filtroService.listar());
    }
}
