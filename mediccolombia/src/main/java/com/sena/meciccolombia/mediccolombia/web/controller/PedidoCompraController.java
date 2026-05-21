package com.sena.meciccolombia.mediccolombia.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.meciccolombia.mediccolombia.service.PedidoCompraService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.CambiarEstadoPedidoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.PedidoCompraRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.PedidoCompraResponseDTO;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoCompraController {

    private final PedidoCompraService pedidoCompraService;

    @PostMapping
    public ResponseEntity<PedidoCompraResponseDTO> crearPedido(@RequestBody PedidoCompraRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED) 
                        .body(pedidoCompraService.crearPedido(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoCompraResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoCompraService.obtenerPorId(id));

    }
    @GetMapping
        public ResponseEntity<List<PedidoCompraResponseDTO>> listarPedidos(){
            return ResponseEntity.ok(pedidoCompraService.listar());
    }
    @GetMapping("/proveedor/{idProveedor}")
    public ResponseEntity<List<PedidoCompraResponseDTO>> listarPorProveedor(@PathVariable Long idProveedor) {
        return ResponseEntity.ok(pedidoCompraService.listarPorProveedor(idProveedor));
    }

    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<List<PedidoCompraResponseDTO>> listarPorEstadoPedido(@PathVariable Long idEstado) {
        return ResponseEntity.ok(pedidoCompraService.listarPorEstado(idEstado));
    }

    @PatchMapping("/cambiar_estado/{idPedido}")
    public ResponseEntity<PedidoCompraResponseDTO> cambiarEstadoPedido(
                    @PathVariable Long idPedido, @RequestBody CambiarEstadoPedidoRequestDTO dto
    ){
        return ResponseEntity.ok(pedidoCompraService.cambiarEstado(idPedido, dto));
    }

    
    
    
    
}
