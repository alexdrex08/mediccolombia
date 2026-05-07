package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TelefonoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TelefonoUpdateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TelefonoResponseDTO;

public interface TelefonoService {

    TelefonoResponseDTO crear(TelefonoRequestDTO dto);
    TelefonoResponseDTO actualizar(Long id, TelefonoUpdateRequestDTO dto);
    void eliminar(Long id);
    TelefonoResponseDTO obtenerPorId(Long id);
    List<TelefonoResponseDTO> listar();
}
