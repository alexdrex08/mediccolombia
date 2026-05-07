package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoTelefonoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoTelefonoResponseDTO;

public interface TipoTelefonoService {

    TipoTelefonoResponseDTO crear(TipoTelefonoRequestDTO dto);
    TipoTelefonoResponseDTO actualizar(Long id, TipoTelefonoRequestDTO dto);
    void eliminar(Long id);
    TipoTelefonoResponseDTO obtenerPorId(Long id);
    List<TipoTelefonoResponseDTO> listar();
}
