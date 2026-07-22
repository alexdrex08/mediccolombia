package com.sena.meciccolombia.mediccolombia.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.meciccolombia.mediccolombia.service.MovimientoProdService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.MovimientoProdRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.MovimientoProdResponseDTO;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/movimiento_prod")
@RequiredArgsConstructor
public class MovimientoProdController {

    private final MovimientoProdService movimientoProdService;

    @GetMapping
    public ResponseEntity<List<MovimientoProdResponseDTO>> listarMovimientos() {
        List<MovimientoProdResponseDTO> movientos = movimientoProdService.listar();
        return ResponseEntity.ok(movientos);
    }

    @PostMapping
    public ResponseEntity<MovimientoProdResponseDTO> crear(@RequestBody MovimientoProdRequestDTO dto) {
        return ResponseEntity.ok(movimientoProdService.registrarMovimiento(dto));
    }

    @GetMapping("/{idProducto}/producto")
    public ResponseEntity<List<MovimientoProdResponseDTO>> buscarPorProducto(@PathVariable Long idProducto) {
        return ResponseEntity.ok(movimientoProdService.listarPorProducto(idProducto));
    }

    @GetMapping("/{idUsuario}/usuario")
    public ResponseEntity<List<MovimientoProdResponseDTO>> buscarPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(movimientoProdService.listarPorUsuario(idUsuario));
    }

    @GetMapping("/{idTipoMovimiento}/tipo_movimiento")
    public ResponseEntity<List<MovimientoProdResponseDTO>> buscarPorTipoMovimiento(
            @PathVariable Long idTipoMovimiento) {
        return ResponseEntity.ok(movimientoProdService.listarPorTipoMovimiento(idTipoMovimiento));
    }

    @GetMapping("/signo/{signo}")
    public ResponseEntity<List<MovimientoProdResponseDTO>> listarPorSigno(
            @PathVariable int signo) {
        return ResponseEntity.ok(movimientoProdService.listarPorSigno(signo));
    }

}
