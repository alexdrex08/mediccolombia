package com.sena.meciccolombia.mediccolombia.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.TipoEstadoMapper;
import com.sena.meciccolombia.mediccolombia.dao.TipoEstadoDAO;
import com.sena.meciccolombia.mediccolombia.domain.TipoEstado;
import com.sena.meciccolombia.mediccolombia.service.TipoEstadoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoEstadoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoEstadoResponseDTO;

@Service
@RequiredArgsConstructor
public class TipoEstadoServiceImpl implements TipoEstadoService {

    private final TipoEstadoDAO tipo_estadoDAO;
    private final TipoEstadoMapper tipo_estadoMapper;

    @Override
    @Transactional
    public TipoEstadoResponseDTO crear(TipoEstadoRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoEstado entity = tipo_estadoMapper.toEntity(dto);
        return tipo_estadoMapper.toResponseDTO(tipo_estadoDAO.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoEstadoResponseDTO> listar() {
        return tipo_estadoDAO.findAll().stream()
                .map(tipo_estadoMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoEstadoResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return tipo_estadoDAO.findById(id)
                .map(tipo_estadoMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("TipoEstado con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!tipo_estadoDAO.existsById(id)) throw new RuntimeException("TipoEstado con ID " + id + " no encontrado");
        tipo_estadoDAO.deleteById(id);
    }

    @Override
    @Transactional
    public TipoEstadoResponseDTO actualizar(Long id, TipoEstadoRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoEstado entity = tipo_estadoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoEstado con ID " + id + " no encontrado"));
        entity.setNombreTipo(dto.getNombreTipo());
        return tipo_estadoMapper.toResponseDTO(tipo_estadoDAO.save(entity));
    }
}
