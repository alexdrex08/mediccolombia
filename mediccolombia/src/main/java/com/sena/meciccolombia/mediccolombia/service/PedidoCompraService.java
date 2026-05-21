package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;

import com.sena.meciccolombia.mediccolombia.web.dto.request.CambiarEstadoPedidoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.PedidoCompraRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.PedidoCompraResponseDTO;

public interface PedidoCompraService {

    PedidoCompraResponseDTO crearPedido(PedidoCompraRequestDTO dto);
    PedidoCompraResponseDTO obtenerPorId(Long id);
    PedidoCompraResponseDTO cambiarEstado(Long id, CambiarEstadoPedidoRequestDTO dto);
    List<PedidoCompraResponseDTO> listar();
    List<PedidoCompraResponseDTO> listarPorProveedor(Long idProveedor);
    List<PedidoCompraResponseDTO> listarPorEstado(Long idEstado);

}
