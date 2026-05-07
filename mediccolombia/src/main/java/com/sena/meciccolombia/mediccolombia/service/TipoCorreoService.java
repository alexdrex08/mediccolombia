package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoCorreoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoCorreoResponseDTO;

public interface TipoCorreoService {

    TipoCorreoResponseDTO crear(TipoCorreoRequestDTO dto);
    TipoCorreoResponseDTO actualizar(Long id, TipoCorreoRequestDTO dto);
    void eliminar(Long id);
    TipoCorreoResponseDTO obtenerPorId(Long id);
    List<TipoCorreoResponseDTO> listar();
}
