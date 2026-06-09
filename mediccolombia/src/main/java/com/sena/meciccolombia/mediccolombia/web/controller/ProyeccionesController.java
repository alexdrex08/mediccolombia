package com.sena.meciccolombia.mediccolombia.web.controller;

import com.sena.meciccolombia.mediccolombia.service.ProyeccionesService;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProyeccionesResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyecciones")
@RequiredArgsConstructor
public class ProyeccionesController {

    private final ProyeccionesService proyeccionesService;

    @GetMapping
    public ResponseEntity<List<ProyeccionesResponseDTO>> listar() {
        return ResponseEntity.ok(proyeccionesService.listar());
    }

    @GetMapping("/tipo/{idTipo}")
    public ResponseEntity<List<ProyeccionesResponseDTO>> listarPorTipo(@PathVariable Long idTipo) {
        return ResponseEntity.ok(proyeccionesService.listarPorTipo(idTipo));
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<ProyeccionesResponseDTO>> listarPorProducto(@PathVariable Long idProducto) {
        return ResponseEntity.ok(proyeccionesService.listarPorProducto(idProducto));
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<ProyeccionesResponseDTO>> listarPorCategoria(@PathVariable Long idCategoria) {
        return ResponseEntity.ok(proyeccionesService.listarPorCategoria(idCategoria));
    }
     @GetMapping("/proveedores-fiables")
    public ResponseEntity<List<ProyeccionesResponseDTO>> proveedoresMasFiables() {
        return ResponseEntity.ok(proyeccionesService.consultarProveedoresMasFiables());
    }

    @GetMapping("/clientes-fieles")
    public ResponseEntity<List<ProyeccionesResponseDTO>> clientesMasFileles() {
        return ResponseEntity.ok(proyeccionesService.consultarClientesMasFileles());
    }

    @GetMapping("/precios-mercado")
    public ResponseEntity<List<ProyeccionesResponseDTO>> preciosMercado() {
        return ResponseEntity.ok(proyeccionesService.consultarPreciosMercado());
    }
}
