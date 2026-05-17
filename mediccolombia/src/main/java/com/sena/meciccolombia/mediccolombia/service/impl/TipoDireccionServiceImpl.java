package com.sena.meciccolombia.mediccolombia.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.TipoDireccionMapper;
import com.sena.meciccolombia.mediccolombia.dao.TipoDireccionDAO;
import com.sena.meciccolombia.mediccolombia.domain.TipoDireccion;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.TipoDireccionService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoDireccionRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoDireccionResponseDTO;

@Service
@RequiredArgsConstructor
public class TipoDireccionServiceImpl implements TipoDireccionService {

    private final TipoDireccionDAO tipoDireccionDAO;
    private final TipoDireccionMapper tipoDireccionMapper;

    @Override
    @Transactional
    public TipoDireccionResponseDTO crear(TipoDireccionRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoDireccion entity = tipoDireccionMapper.toEntity(dto);
        return tipoDireccionMapper.toResponseDTO(tipoDireccionDAO.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoDireccionResponseDTO> listar() {
        return tipoDireccionDAO.findAll().stream()
                .map(tipoDireccionMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoDireccionResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return tipoDireccionDAO.findById(id)
                .map(tipoDireccionMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("TipoDireccion con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!tipoDireccionDAO.existsById(id)) throw new ResourceNotFoundException("TipoDireccion con ID " + id + " no encontrado o no existe");
        tipoDireccionDAO.deleteById(id);
    }

    @Override
    @Transactional
    public TipoDireccionResponseDTO actualizar(Long id, TipoDireccionRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoDireccion entity = tipoDireccionDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoDireccion con ID " + id + " no encontrado"));
        entity.setNombreTipo(dto.getNombreTipo());
        return tipoDireccionMapper.toResponseDTO(tipoDireccionDAO.save(entity));
    }
}
