package com.sena.meciccolombia.mediccolombia.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.meciccolombia.mediccolombia.scheduler.AlertaInvScheduler;
import com.sena.meciccolombia.mediccolombia.service.impl.AlertaInvService;
import com.sena.meciccolombia.mediccolombia.web.dto.response.AlertaInvResponseDTO;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
public class AlertaInvController {

    private final AlertaInvService alertaInvService;
    private final AlertaInvScheduler scheduler;

    @GetMapping
    public ResponseEntity<List<AlertaInvResponseDTO>> listar() {
        return ResponseEntity.ok(alertaInvService.listar());
    }
    @GetMapping("/{id}")
    public ResponseEntity<AlertaInvResponseDTO> obtenerPorID(@PathVariable Long id) {
        return ResponseEntity.ok(alertaInvService.obtenerPorId(id));
    }
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<AlertaInvResponseDTO>> listarPorProducto(@PathVariable Long idProducto) {
        return ResponseEntity.ok(alertaInvService.listarPorProducto(idProducto));
    }
    @GetMapping("/tipo/{tipoAlerta}")
    public ResponseEntity<List<AlertaInvResponseDTO>> listarPorTipoAlerta(@PathVariable String tipoAlerta) {
        return ResponseEntity.ok(alertaInvService.listarPorTipo(tipoAlerta));
    }
    @GetMapping("/generar-alerta")
    public ResponseEntity<String> generarAlertasPrueba() {
        scheduler.generarAlertasAutomaticas();
        return ResponseEntity.ok("Alertas generadas");
    }
    
    
    
}
