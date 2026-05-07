package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoDireccionRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoDireccionResponseDTO;

public interface TipoDireccionService {

    TipoDireccionResponseDTO crear(TipoDireccionRequestDTO dto);
    TipoDireccionResponseDTO actualizar(Long id, TipoDireccionRequestDTO dto);
    void eliminar(Long id);
    TipoDireccionResponseDTO obtenerPorId(Long id);
    List<TipoDireccionResponseDTO> listar();
}
