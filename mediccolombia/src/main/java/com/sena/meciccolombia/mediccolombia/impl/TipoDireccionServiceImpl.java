package com.sena.meciccolombia.mediccolombia.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.TipoDireccionMapper;
import com.sena.meciccolombia.mediccolombia.dao.TipoDireccionDAO;
import com.sena.meciccolombia.mediccolombia.domain.TipoDireccion;
import com.sena.meciccolombia.mediccolombia.service.TipoDireccionService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoDireccionRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoDireccionResponseDTO;

@Service
@RequiredArgsConstructor
public class TipoDireccionServiceImpl implements TipoDireccionService {

    private final TipoDireccionDAO tipo_direccionDAO;
    private final TipoDireccionMapper tipo_direccionMapper;

    @Override
    @Transactional
    public TipoDireccionResponseDTO crear(TipoDireccionRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoDireccion entity = tipo_direccionMapper.toEntity(dto);
        return tipo_direccionMapper.toResponseDTO(tipo_direccionDAO.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoDireccionResponseDTO> listar() {
        return tipo_direccionDAO.findAll().stream()
                .map(tipo_direccionMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoDireccionResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return tipo_direccionDAO.findById(id)
                .map(tipo_direccionMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("TipoDireccion con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!tipo_direccionDAO.existsById(id)) throw new RuntimeException("TipoDireccion con ID " + id + " no encontrado");
        tipo_direccionDAO.deleteById(id);
    }

    @Override
    @Transactional
    public TipoDireccionResponseDTO actualizar(Long id, TipoDireccionRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoDireccion entity = tipo_direccionDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoDireccion con ID " + id + " no encontrado"));
        entity.setNombreTipo(dto.getNombreTipo());
        return tipo_direccionMapper.toResponseDTO(tipo_direccionDAO.save(entity));
    }
}
