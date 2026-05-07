package com.sena.meciccolombia.mediccolombia.web.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.service.TipoTelefonoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoTelefonoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoTelefonoResponseDTO;

@RestController
@RequestMapping("/api/tipo_telefonos")
@RequiredArgsConstructor
public class TipoTelefonoController {

    private final TipoTelefonoService tipo_telefonoService;

    @PostMapping
    public ResponseEntity<TipoTelefonoResponseDTO> crear(@RequestBody TipoTelefonoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipo_telefonoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoTelefonoResponseDTO> actualizar(@PathVariable Long id, @RequestBody TipoTelefonoRequestDTO dto) {
        return ResponseEntity.ok(tipo_telefonoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tipo_telefonoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoTelefonoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tipo_telefonoService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TipoTelefonoResponseDTO>> listar() {
        return ResponseEntity.ok(tipo_telefonoService.listar());
    }
}
