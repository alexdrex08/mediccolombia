package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;
import com.sena.meciccolombia.mediccolombia.web.dto.request.EstadoPedidoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.EstadoPedidoResponseDTO;

public interface EstadoPedidoService {

    EstadoPedidoResponseDTO crear(EstadoPedidoRequestDTO dto);
    EstadoPedidoResponseDTO actualizar(Long id, EstadoPedidoRequestDTO dto);
    void eliminar(Long id);
    EstadoPedidoResponseDTO obtenerPorId(Long id);
    List<EstadoPedidoResponseDTO> listar();
}
