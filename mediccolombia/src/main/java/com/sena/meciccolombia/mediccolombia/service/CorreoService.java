package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;
import com.sena.meciccolombia.mediccolombia.web.dto.request.CorreoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.CorreoUpdateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.CorreoResponseDTO;

public interface CorreoService {

    CorreoResponseDTO crear(CorreoRequestDTO dto);
    CorreoResponseDTO actualizar(Long id, CorreoUpdateRequestDTO dto);
    void eliminar(Long id);
    CorreoResponseDTO obtenerPorId(Long id);
    List<CorreoResponseDTO> listar();
}
