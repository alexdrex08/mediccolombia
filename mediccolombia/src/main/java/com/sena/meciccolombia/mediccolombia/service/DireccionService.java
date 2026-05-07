package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DireccionRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DireccionUpdateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DireccionResponseDTO;

public interface DireccionService {

    DireccionResponseDTO crear(DireccionRequestDTO dto);
    DireccionResponseDTO actualizar(Long id, DireccionUpdateRequestDTO dto);
    void eliminar(Long id);
    DireccionResponseDTO obtenerPorId(Long id);
    List<DireccionResponseDTO> listar();
}
