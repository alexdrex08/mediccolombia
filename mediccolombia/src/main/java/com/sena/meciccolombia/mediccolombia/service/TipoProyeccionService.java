package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoProyeccionRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoProyeccionResponseDTO;

public interface TipoProyeccionService {

    TipoProyeccionResponseDTO crear(TipoProyeccionRequestDTO dto);
    TipoProyeccionResponseDTO actualizar(Long id, TipoProyeccionRequestDTO dto);
    void eliminar(Long id);
    TipoProyeccionResponseDTO obtenerPorId(Long id);
    List<TipoProyeccionResponseDTO> listar();
}
