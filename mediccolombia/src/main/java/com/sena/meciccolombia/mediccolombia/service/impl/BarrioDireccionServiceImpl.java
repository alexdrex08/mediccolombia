package com.sena.meciccolombia.mediccolombia.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.BarrioDireccionMapper;
import com.sena.meciccolombia.mediccolombia.dao.BarrioDireccionDAO;
import com.sena.meciccolombia.mediccolombia.domain.BarrioDireccion;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.BarrioDireccionService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.BarrioDireccionRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.BarrioDireccionResponseDTO;

@Service
@RequiredArgsConstructor
public class BarrioDireccionServiceImpl implements BarrioDireccionService {

    private final BarrioDireccionDAO barrioDireccionDAO;
    private final BarrioDireccionMapper barrioDireccionMapper;

    @Override
    @Transactional
    public BarrioDireccionResponseDTO crear(BarrioDireccionRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        BarrioDireccion entity = barrioDireccionMapper.toEntity(dto);
        return barrioDireccionMapper.toResponseDTO(barrioDireccionDAO.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BarrioDireccionResponseDTO> listar() {
        return barrioDireccionDAO.findAll().stream()
                .map(barrioDireccionMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BarrioDireccionResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return barrioDireccionDAO.findById(id)
                .map(barrioDireccionMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("BarrioDireccion con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!barrioDireccionDAO.existsById(id)) throw new ResourceNotFoundException("BarrioDireccion con ID " + id + " no encontrado");
        barrioDireccionDAO.deleteById(id);
    }

    @Override
    @Transactional
    public BarrioDireccionResponseDTO actualizar(Long id, BarrioDireccionRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        BarrioDireccion entity = barrioDireccionDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("BarrioDireccion con ID " + id + " no encontrado"));
        entity.setNombreBarrio(dto.getNombreBarrio());
        return barrioDireccionMapper.toResponseDTO(barrioDireccionDAO.save(entity));
    }
}
