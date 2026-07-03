package com.sena.meciccolombia.mediccolombia.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.meciccolombia.mediccolombia.service.ReporteInvService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.ReporteInvRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ReporteDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ReporteInvResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteInvController {
    
    private final ReporteInvService reporteInvService;

    @PostMapping("/generar")
    public ResponseEntity<ReporteDetalleResponseDTO> generarReporte(
            @RequestBody ReporteInvRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reporteInvService.generarReporte(dto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReporteInvResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reporteInvService.obtenerPorId(id));
    }
    @GetMapping
    public ResponseEntity<List<ReporteInvResponseDTO>> listar() {
        return ResponseEntity.ok(reporteInvService.listar());
    }
     @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ReporteInvResponseDTO>> listarPorUsuario(
            @PathVariable Long idUsuario) {
        return ResponseEntity.ok(reporteInvService.listarPorUsuario(idUsuario));
    }
    @GetMapping("/tipo/{tipoReporte}")
    public ResponseEntity<List<ReporteInvResponseDTO>> listarPorTipo(
            @PathVariable String tipoReporte) {
        return ResponseEntity.ok(reporteInvService.listarPorTipo(tipoReporte));
    }
}
