package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoMovimientoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoMovimientoResponseDTO;

public interface TipoMovimientoService {

    TipoMovimientoResponseDTO crear(TipoMovimientoRequestDTO dto);
    TipoMovimientoResponseDTO actualizar(Long id, TipoMovimientoRequestDTO dto);
    void eliminar(Long id);
    TipoMovimientoResponseDTO obtenerPorId(Long id);
    List<TipoMovimientoResponseDTO> listar();
}
