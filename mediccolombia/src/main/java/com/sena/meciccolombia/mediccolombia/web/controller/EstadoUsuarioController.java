package com.sena.meciccolombia.mediccolombia.web.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.service.EstadoUsuarioService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.EstadoUsuarioRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.EstadoUsuarioResponseDTO;

@RestController
@RequestMapping("/api/estado_usuarios")
@RequiredArgsConstructor
public class EstadoUsuarioController {

    private final EstadoUsuarioService estado_usuarioService;

    @PostMapping
    public ResponseEntity<EstadoUsuarioResponseDTO> crear(@RequestBody EstadoUsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estado_usuarioService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoUsuarioResponseDTO> actualizar(@PathVariable Long id, @RequestBody EstadoUsuarioRequestDTO dto) {
        return ResponseEntity.ok(estado_usuarioService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        estado_usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoUsuarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(estado_usuarioService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<EstadoUsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(estado_usuarioService.listar());
    }
}
