package com.sena.meciccolombia.mediccolombia.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.EstadoUsuarioMapper;
import com.sena.meciccolombia.mediccolombia.dao.EstadoUsuarioDAO;
import com.sena.meciccolombia.mediccolombia.dao.TipoEstadoDAO;
import com.sena.meciccolombia.mediccolombia.dao.UsuarioDAO;
import com.sena.meciccolombia.mediccolombia.domain.EstadoUsuario;
import com.sena.meciccolombia.mediccolombia.domain.TipoEstado;
import com.sena.meciccolombia.mediccolombia.domain.Usuario;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.EstadoUsuarioService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.EstadoUsuarioRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.EstadoUsuarioResponseDTO;

@Service
@RequiredArgsConstructor
public class EstadoUsuarioServiceImpl implements EstadoUsuarioService {

    private final EstadoUsuarioDAO estadoUsuarioDAO;
    private final UsuarioDAO usuarioDAO;
    private final TipoEstadoDAO tipoEstadoDAO;
    private final EstadoUsuarioMapper estadoUsuarioMapper;


    @Override
    @Transactional
    public EstadoUsuarioResponseDTO crear(EstadoUsuarioRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        
        Usuario usuario = usuarioDAO.findById(dto.getIdUsuario())
                                    .orElseThrow(() -> new ResourceNotFoundException("Usuario con el ID: "+ dto.getIdUsuario() + " no fue encontrado o no existe"));
        TipoEstado tipoEstado = tipoEstadoDAO.findById(dto.getIdTipoEstado())
                                    .orElseThrow(() -> new ResourceNotFoundException("TipoEstado con el ID: "+ dto.getIdTipoEstado() + " no ha sido encontrado"));
        EstadoUsuario estadoUsuario = estadoUsuarioMapper.toEntity(dto, usuario, tipoEstado);
        return estadoUsuarioMapper.toResponseDTO(estadoUsuarioDAO.save(estadoUsuario));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoUsuarioResponseDTO> listar() {
        return estadoUsuarioDAO.findAll().stream()
                .map(estadoUsuarioMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoUsuarioResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return estadoUsuarioDAO.findById(id)
                .map(estadoUsuarioMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("EstadoUsuario con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!estadoUsuarioDAO.existsById(id)) throw new ResourceNotFoundException("EstadoUsuario con ID " + id + " no encontrado");
        estadoUsuarioDAO.deleteById(id);
    }

    @Override
    @Transactional
    public EstadoUsuarioResponseDTO actualizar(Long id, EstadoUsuarioRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");

        EstadoUsuario estadoUsuario = estadoUsuarioDAO.findById(id)
                                                        .orElseThrow(() -> new ResourceNotFoundException("El EstadoUsuario con el ID:" + id + " no fue encontrado"));
        TipoEstado tipoEstado = tipoEstadoDAO.findById(dto.getIdTipoEstado())
                                            .orElseThrow(() -> new ResourceNotFoundException("El TipoEstado con el ID:" + dto.getIdTipoEstado() + " no fue encontrado"));
        estadoUsuario.setObservacion(dto.getObservacion());
        estadoUsuario.setFechaInicio(dto.getFechaInicio());
        estadoUsuario.setFechaFin(dto.getFechaFin());
        estadoUsuario.setTipoEstado(tipoEstado);
    
        return estadoUsuarioMapper.toResponseDTO(estadoUsuarioDAO.save(estadoUsuario));
    }

    @Override
    public List<EstadoUsuarioResponseDTO> listarPorUsuario(Long idUsuario) {
       if (idUsuario == null) throw new IllegalArgumentException("El ID no puede ser nulo");
       return estadoUsuarioDAO.findByUsuarioId(idUsuario)
                                .stream()
                                .map(estadoUsuarioMapper::toResponseDTO)
                                .toList();
    }
}
