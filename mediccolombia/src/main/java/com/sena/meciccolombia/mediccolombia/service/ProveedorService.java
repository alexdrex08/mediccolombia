package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;

import com.sena.meciccolombia.mediccolombia.web.dto.request.ProveedorRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProveedorDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProveedorResponseDTO;

public interface ProveedorService {

    ProveedorResponseDTO crear(ProveedorRequestDTO dto);

    ProveedorResponseDTO actualizar(Long id, ProveedorRequestDTO dto);
    void eliminar(Long id);
    ProveedorResponseDTO buscarPorId(Long id);
    List<ProveedorResponseDTO> listar();
    ProveedorResponseDTO buscarPorNit(String nit);

    ProveedorDetalleResponseDTO obtenerDetalles(Long id);

    
}
