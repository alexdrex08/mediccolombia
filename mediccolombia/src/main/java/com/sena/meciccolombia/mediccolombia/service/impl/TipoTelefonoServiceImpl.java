package com.sena.meciccolombia.mediccolombia.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.TipoTelefonoMapper;
import com.sena.meciccolombia.mediccolombia.dao.TipoTelefonoDAO;
import com.sena.meciccolombia.mediccolombia.domain.TipoTelefono;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.TipoTelefonoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoTelefonoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoTelefonoResponseDTO;

@Service
@RequiredArgsConstructor
public class TipoTelefonoServiceImpl implements TipoTelefonoService {

    private final TipoTelefonoDAO tipoTelefonoDAO;
    private final TipoTelefonoMapper tipoTelefonoMapper;

    @Override
    @Transactional
    public TipoTelefonoResponseDTO crear(TipoTelefonoRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoTelefono entity = tipoTelefonoMapper.toEntity(dto);
        return tipoTelefonoMapper.toResponseDTO(tipoTelefonoDAO.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoTelefonoResponseDTO> listar() {
        return tipoTelefonoDAO.findAll().stream()
                .map(tipoTelefonoMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoTelefonoResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return tipoTelefonoDAO.findById(id)
                .map(tipoTelefonoMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("TipoTelefono con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!tipoTelefonoDAO.existsById(id)) throw new ResourceNotFoundException("TipoTelefono con ID " + id + " no encontrado");
        tipoTelefonoDAO.deleteById(id);
    }

    @Override
    @Transactional
    public TipoTelefonoResponseDTO actualizar(Long id, TipoTelefonoRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoTelefono entity = tipoTelefonoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoTelefono con ID " + id + " no encontrado"));
        entity.setNombreTipo(dto.getNombreTipo());
        return tipoTelefonoMapper.toResponseDTO(tipoTelefonoDAO.save(entity));
    }
}
