package com.sena.meciccolombia.mediccolombia.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.EstadoUsuarioMapper;
import com.sena.meciccolombia.mediccolombia.dao.EstadoUsuarioDAO;
import com.sena.meciccolombia.mediccolombia.domain.*;
import com.sena.meciccolombia.mediccolombia.service.EstadoUsuarioService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.EstadoUsuarioRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.EstadoUsuarioResponseDTO;

@Service
@RequiredArgsConstructor
public class EstadoUsuarioServiceImpl implements EstadoUsuarioService {

    private final EstadoUsuarioDAO estado_usuarioDAO;
    private final EstadoUsuarioMapper estado_usuarioMapper;
    // Inject additional DAOs here for FK resolution

    @Override
    @Transactional
    public EstadoUsuarioResponseDTO crear(EstadoUsuarioRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        // TODO: resolve FK entities from DAOs before building entity
        throw new UnsupportedOperationException("Implementar resolución de FKs");
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoUsuarioResponseDTO> listar() {
        return estado_usuarioDAO.findAll().stream()
                .map(estado_usuarioMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoUsuarioResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return estado_usuarioDAO.findById(id)
                .map(estado_usuarioMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("EstadoUsuario con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!estado_usuarioDAO.existsById(id)) throw new RuntimeException("EstadoUsuario con ID " + id + " no encontrado");
        estado_usuarioDAO.deleteById(id);
    }

    @Override
    @Transactional
    public EstadoUsuarioResponseDTO actualizar(Long id, EstadoUsuarioRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        // TODO: resolve FK entities and apply setters
        throw new UnsupportedOperationException("Implementar resolución de FKs");
    }
}
