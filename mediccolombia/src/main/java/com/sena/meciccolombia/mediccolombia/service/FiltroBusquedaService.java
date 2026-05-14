package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;
import com.sena.meciccolombia.mediccolombia.web.dto.request.FiltroBusquedaRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.FiltroBusquedaDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.FiltroBusquedaResponseDTO;

public interface FiltroBusquedaService {

    FiltroBusquedaResponseDTO crear(FiltroBusquedaRequestDTO dto);
    FiltroBusquedaResponseDTO actualizar(Long id, FiltroBusquedaRequestDTO dto);
    void eliminar(Long id);
    FiltroBusquedaResponseDTO obtenerPorId(Long id);

    FiltroBusquedaDetalleResponseDTO obtenerDetalle(Long id);
    List<FiltroBusquedaResponseDTO> listar();
}
