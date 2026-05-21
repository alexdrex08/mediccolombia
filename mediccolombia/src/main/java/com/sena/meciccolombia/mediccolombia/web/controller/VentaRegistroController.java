package com.sena.meciccolombia.mediccolombia.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.meciccolombia.mediccolombia.service.IVentaRegistroService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.VentaRegistroRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.VentaRegistroResponseDTO;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaRegistroController {

    private final IVentaRegistroService ventaRegistroService;

    @PostMapping
    public ResponseEntity<VentaRegistroResponseDTO> registrarVenta(@RequestBody VentaRegistroRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ventaRegistroService.registrarVenta(dto));
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<VentaRegistroResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ventaRegistroService.obtenerVentaPorId(id));
    }
    @GetMapping
    public ResponseEntity<List<VentaRegistroResponseDTO>> listarVentas() {
        return ResponseEntity.ok(ventaRegistroService.listarVentas());
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<VentaRegistroResponseDTO>> listarVentasPorCliente(@PathVariable Long idCliente) {
        return ResponseEntity.ok(ventaRegistroService.listarVentasPorCliente(idCliente));
    }
        @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<VentaRegistroResponseDTO>> listarVentasPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(ventaRegistroService.listarVentasPorUsuario(idUsuario));
    }
    
}
