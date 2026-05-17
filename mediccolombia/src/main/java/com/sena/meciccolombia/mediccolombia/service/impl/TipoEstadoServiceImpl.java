package com.sena.meciccolombia.mediccolombia.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.TipoEstadoMapper;
import com.sena.meciccolombia.mediccolombia.dao.TipoEstadoDAO;
import com.sena.meciccolombia.mediccolombia.domain.TipoEstado;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.TipoEstadoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoEstadoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoEstadoResponseDTO;

@Service
@RequiredArgsConstructor
public class TipoEstadoServiceImpl implements TipoEstadoService {

    private final TipoEstadoDAO tipoEstadoDAO;
    private final TipoEstadoMapper tipoEstadoMapper;

    @Override
    @Transactional
    public TipoEstadoResponseDTO crear(TipoEstadoRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoEstado entity = tipoEstadoMapper.toEntity(dto);
        return tipoEstadoMapper.toResponseDTO(tipoEstadoDAO.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoEstadoResponseDTO> listar() {
        return tipoEstadoDAO.findAll().stream()
                .map(tipoEstadoMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoEstadoResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return tipoEstadoDAO.findById(id)
                .map(tipoEstadoMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("TipoEstado con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!tipoEstadoDAO.existsById(id)) throw new ResourceNotFoundException("TipoEstado con ID " + id + " no encontrado");
        tipoEstadoDAO.deleteById(id);
    }

    @Override
    @Transactional
    public TipoEstadoResponseDTO actualizar(Long id, TipoEstadoRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoEstado entity = tipoEstadoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoEstado con ID " + id + " no encontrado"));
        entity.setNombreTipo(dto.getNombreTipo());
        return tipoEstadoMapper.toResponseDTO(tipoEstadoDAO.save(entity));
    }
}
