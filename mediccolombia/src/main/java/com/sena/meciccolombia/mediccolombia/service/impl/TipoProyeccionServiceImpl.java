package com.sena.meciccolombia.mediccolombia.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.TipoProyeccionMapper;
import com.sena.meciccolombia.mediccolombia.dao.TipoProyeccionDAO;
import com.sena.meciccolombia.mediccolombia.domain.TipoProyeccion;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.TipoProyeccionService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoProyeccionRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoProyeccionResponseDTO;

@Service
@RequiredArgsConstructor
public class TipoProyeccionServiceImpl implements TipoProyeccionService {

    private final TipoProyeccionDAO tipoProyeccionDAO;
    private final TipoProyeccionMapper tipoProyeccionMapper;

    @Override
    @Transactional
    public TipoProyeccionResponseDTO crear(TipoProyeccionRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoProyeccion entity = tipoProyeccionMapper.toEntity(dto);
        return tipoProyeccionMapper.toResponseDTO(tipoProyeccionDAO.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoProyeccionResponseDTO> listar() {
        return tipoProyeccionDAO.findAll().stream()
                .map(tipoProyeccionMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoProyeccionResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return tipoProyeccionDAO.findById(id)
                .map(tipoProyeccionMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("TipoProyeccion con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!tipoProyeccionDAO.existsById(id)) throw new ResourceNotFoundException("TipoProyeccion con ID " + id + " no encontrado");
        tipoProyeccionDAO.deleteById(id);
    }

    @Override
    @Transactional
    public TipoProyeccionResponseDTO actualizar(Long id, TipoProyeccionRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoProyeccion entity = tipoProyeccionDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoProyeccion con ID " + id + " no encontrado"));
        entity.setNombreProyeccion(dto.getNombreProyeccion());
        return tipoProyeccionMapper.toResponseDTO(tipoProyeccionDAO.save(entity));
    }
}
