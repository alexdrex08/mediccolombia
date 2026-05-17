package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;

import com.sena.meciccolombia.mediccolombia.web.dto.request.MovimientoProdRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.MovimientoProdResponseDTO;

public interface MovimientoProdService {
    
    MovimientoProdResponseDTO registrarMovimiento(MovimientoProdRequestDTO dto);
    MovimientoProdResponseDTO obtenerPorId(Long id);
    List<MovimientoProdResponseDTO> listar();
    List<MovimientoProdResponseDTO> listarPorProducto(Long idProducto);
    List<MovimientoProdResponseDTO> listarPorUsuario(Long idUsuario);
    List<MovimientoProdResponseDTO> listarPorTipoMovimiento(Long idTipoMovimiento);

}
