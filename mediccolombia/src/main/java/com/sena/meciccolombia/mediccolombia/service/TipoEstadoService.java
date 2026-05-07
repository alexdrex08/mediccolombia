package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoEstadoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoEstadoResponseDTO;

public interface TipoEstadoService {

    TipoEstadoResponseDTO crear(TipoEstadoRequestDTO dto);
    TipoEstadoResponseDTO actualizar(Long id, TipoEstadoRequestDTO dto);
    void eliminar(Long id);
    TipoEstadoResponseDTO obtenerPorId(Long id);
    List<TipoEstadoResponseDTO> listar();
}
