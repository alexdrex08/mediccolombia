package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;
import com.sena.meciccolombia.mediccolombia.web.dto.request.BarrioDireccionRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.BarrioDireccionResponseDTO;

public interface BarrioDireccionService {

    BarrioDireccionResponseDTO crear(BarrioDireccionRequestDTO dto);
    BarrioDireccionResponseDTO actualizar(Long id, BarrioDireccionRequestDTO dto);
    void eliminar(Long id);
    BarrioDireccionResponseDTO obtenerPorId(Long id);
    List<BarrioDireccionResponseDTO> listar();
}
