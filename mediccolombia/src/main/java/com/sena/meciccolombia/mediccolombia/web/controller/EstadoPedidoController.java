package com.sena.meciccolombia.mediccolombia.web.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.service.EstadoPedidoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.EstadoPedidoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.EstadoPedidoResponseDTO;

@RestController
@RequestMapping("/api/estado_pedidos")
@RequiredArgsConstructor
public class EstadoPedidoController {

    private final EstadoPedidoService estado_pedidoService;

    @PostMapping
    public ResponseEntity<EstadoPedidoResponseDTO> crear(@RequestBody EstadoPedidoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estado_pedidoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoPedidoResponseDTO> actualizar(@PathVariable Long id, @RequestBody EstadoPedidoRequestDTO dto) {
        return ResponseEntity.ok(estado_pedidoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        estado_pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoPedidoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(estado_pedidoService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<EstadoPedidoResponseDTO>> listar() {
        return ResponseEntity.ok(estado_pedidoService.listar());
    }
}
