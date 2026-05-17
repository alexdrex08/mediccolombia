package com.sena.meciccolombia.mediccolombia.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.meciccolombia.mediccolombia.component.EstadoUsuarioMapper;
import com.sena.meciccolombia.mediccolombia.component.MovimientoProdMapper;
import com.sena.meciccolombia.mediccolombia.component.UsuarioMapper;
import com.sena.meciccolombia.mediccolombia.dao.EstadoUsuarioDAO;
import com.sena.meciccolombia.mediccolombia.dao.MovimientoProdDAO;
import com.sena.meciccolombia.mediccolombia.dao.UsuarioDAO;
import com.sena.meciccolombia.mediccolombia.domain.Usuario;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.UsuarioService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.UsuarioCambiarContrasenaDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.UsuarioCreateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.UsuarioUpdateRequest;
import com.sena.meciccolombia.mediccolombia.web.dto.response.EstadoUsuarioResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.MovimientoProdResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioDetalleMovimientosResponseDto;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioDAO usuarioDAO;
    private final EstadoUsuarioDAO estadoUsuarioDAO;
    private final MovimientoProdDAO movimientoProdDAO;
    private final UsuarioMapper usuarioMapper;
    private final EstadoUsuarioMapper estadoUsuarioMapper;
    private final MovimientoProdMapper movimientoProdMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UsuarioResponseDTO crear(UsuarioCreateRequestDTO dto) {
        if(dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        if(usuarioDAO.existsByCorreo(dto.getCorreo())){
            throw new ResourceNotFoundException("Ya existe un usuario con este correo");
        }

        String contrasenaCodificada = passwordEncoder.encode(dto.getContrasena());
        Usuario usuario = usuarioMapper.toEntity(dto, contrasenaCodificada);
        return usuarioMapper.toResponseDTO(usuarioDAO.save(usuario));
    }


    @Override
    @Transactional
    public UsuarioResponseDTO actualizar(Long id, UsuarioUpdateRequest dto) {
        if(dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");

        if(id  == null) throw new IllegalArgumentException("El ID no puede ser nulo");

        Usuario usuario = usuarioDAO.findById(id)
                                    .orElseThrow(() -> new ResourceNotFoundException("El Usuario con el ID:"+ id + " no fue encontrado o no existe"));
        if(!usuario.getCorreo().equalsIgnoreCase(dto.getCorreo()) && usuarioDAO.existsByCorreo(dto.getCorreo())){
            throw new ResourceNotFoundException("Ya existe un usuario con ese correo");
        }

        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setRol(dto.getRol());

        return usuarioMapper.toResponseDTO(usuarioDAO.save(usuario));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if(id == null)  throw new IllegalArgumentException("El ID no puede ser nulo");
        if(!usuarioDAO.existsById(id)) throw new ResourceNotFoundException("Usuario con ID" + id + " no fue encontrado");
        usuarioDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO obtenerPorId(Long id) {
        if(id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return usuarioDAO.findById(id)
                        .map(usuarioMapper::toResponseDTO)
                        .orElseThrow(() -> new ResourceNotFoundException("Usuario con el ID: "+ id + " no fue encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listar() {
        return usuarioDAO.findAll()
                        .stream()
                        .map(usuarioMapper::toResponseDTO)
                        .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorCorreo(String correo) {
        if(correo == null) throw new IllegalArgumentException("El correo no puede ser nulo");
        return usuarioDAO.findByCorreo(correo)
                        .map(usuarioMapper::toResponseDTO)
                        .orElseThrow(() -> new ResourceNotFoundException("Usuario con correo: " +correo+ " no fue encontrado"));
    }

    @Override
    @Transactional
    public void cambiarContrasena(Long id, UsuarioCambiarContrasenaDTO dto) {
        if(id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if(dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");

        Usuario usuario = usuarioDAO.findById(id)
                                    .orElseThrow(() -> new ResourceNotFoundException("El Usuario con el ID:" + id + " no encontrado o no existe"));
        if (!passwordEncoder.matches(dto.getContrasenaActual(), usuario.getContrasena())){
            throw new IllegalArgumentException("La contraseña actual es incorrecta");
        }

        usuario.setContrasena(passwordEncoder.encode(dto.getNuevaContrasena()));
        usuarioDAO.save(usuario);
    }


    @Override
    @Transactional(readOnly = true)
    public UsuarioDetalleResponseDTO obtenerDetalle(Long id) {
       if(id == null) throw new IllegalArgumentException("El ID no puede ser nulo");

       Usuario usuario = usuarioDAO.findById(id)    
                                .orElseThrow(() -> new ResourceNotFoundException("El usuario con el ID:" + id + " no encontrado"));
        List<EstadoUsuarioResponseDTO> estados = estadoUsuarioDAO.findByUsuarioId(id)
                                .stream()
                                .map(estadoUsuarioMapper::toResponseDTO)
                                .toList();

        return usuarioMapper.toDetalleDTO(usuario, estados);
    }
    @Override
    @Transactional(readOnly = true)
     public UsuarioDetalleMovimientosResponseDto obtenerDetalleMovimiento(Long id) {
       if(id == null) throw new IllegalArgumentException("El ID no puede ser nulo");

       Usuario usuario = usuarioDAO.findById(id)    
                                .orElseThrow(() -> new ResourceNotFoundException("El usuario con el ID:" + id + " no encontrado"));
        List<MovimientoProdResponseDTO> movimientos = movimientoProdDAO.findByUsuarioId(id)
                                .stream()
                                .map(movimientoProdMapper::toResponseDTO)
                                .toList();

        return usuarioMapper.toDetalleMovimientoDTO(usuario, movimientos);
    }
    
}
