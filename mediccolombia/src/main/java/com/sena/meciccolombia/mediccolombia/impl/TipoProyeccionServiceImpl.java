package com.sena.meciccolombia.mediccolombia.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.TipoProyeccionMapper;
import com.sena.meciccolombia.mediccolombia.dao.TipoProyeccionDAO;
import com.sena.meciccolombia.mediccolombia.domain.TipoProyeccion;
import com.sena.meciccolombia.mediccolombia.service.TipoProyeccionService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoProyeccionRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoProyeccionResponseDTO;

@Service
@RequiredArgsConstructor
public class TipoProyeccionServiceImpl implements TipoProyeccionService {

    private final TipoProyeccionDAO tipo_proyeccionDAO;
    private final TipoProyeccionMapper tipo_proyeccionMapper;

    @Override
    @Transactional
    public TipoProyeccionResponseDTO crear(TipoProyeccionRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoProyeccion entity = tipo_proyeccionMapper.toEntity(dto);
        return tipo_proyeccionMapper.toResponseDTO(tipo_proyeccionDAO.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoProyeccionResponseDTO> listar() {
        return tipo_proyeccionDAO.findAll().stream()
                .map(tipo_proyeccionMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoProyeccionResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return tipo_proyeccionDAO.findById(id)
                .map(tipo_proyeccionMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("TipoProyeccion con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!tipo_proyeccionDAO.existsById(id)) throw new RuntimeException("TipoProyeccion con ID " + id + " no encontrado");
        tipo_proyeccionDAO.deleteById(id);
    }

    @Override
    @Transactional
    public TipoProyeccionResponseDTO actualizar(Long id, TipoProyeccionRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoProyeccion entity = tipo_proyeccionDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoProyeccion con ID " + id + " no encontrado"));
        entity.setNombreProyeccion(dto.getNombreProyeccion());
        return tipo_proyeccionMapper.toResponseDTO(tipo_proyeccionDAO.save(entity));
    }
}
