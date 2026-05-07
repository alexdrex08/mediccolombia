package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;
import com.sena.meciccolombia.mediccolombia.web.dto.request.EstadoUsuarioRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.EstadoUsuarioResponseDTO;

public interface EstadoUsuarioService {

    EstadoUsuarioResponseDTO crear(EstadoUsuarioRequestDTO dto);
    EstadoUsuarioResponseDTO actualizar(Long id, EstadoUsuarioRequestDTO dto);
    void eliminar(Long id);
    EstadoUsuarioResponseDTO obtenerPorId(Long id);
    List<EstadoUsuarioResponseDTO> listar();
}
