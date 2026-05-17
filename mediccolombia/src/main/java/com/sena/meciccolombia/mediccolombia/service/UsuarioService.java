package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;

import com.sena.meciccolombia.mediccolombia.web.dto.request.UsuarioCambiarContrasenaDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.UsuarioCreateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.UsuarioUpdateRequest;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioDetalleMovimientosResponseDto;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioResponseDTO;

public interface UsuarioService {

    UsuarioResponseDTO crear(UsuarioCreateRequestDTO dto);
    UsuarioResponseDTO actualizar(Long id, UsuarioUpdateRequest dto);
    void eliminar(Long id);
    UsuarioResponseDTO obtenerPorId(Long id);
    List<UsuarioResponseDTO> listar();
    UsuarioResponseDTO buscarPorCorreo(String correo);
    void cambiarContrasena(Long id, UsuarioCambiarContrasenaDTO dto);
    UsuarioDetalleResponseDTO obtenerDetalle(Long id);

    UsuarioDetalleMovimientosResponseDto obtenerDetalleMovimiento(Long id);


    
}
