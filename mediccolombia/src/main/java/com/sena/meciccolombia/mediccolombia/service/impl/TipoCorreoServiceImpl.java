package com.sena.meciccolombia.mediccolombia.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.TipoCorreoMapper;
import com.sena.meciccolombia.mediccolombia.dao.TipoCorreoDAO;
import com.sena.meciccolombia.mediccolombia.domain.TipoCorreo;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.TipoCorreoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoCorreoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoCorreoResponseDTO;

@Service
@RequiredArgsConstructor
public class TipoCorreoServiceImpl implements TipoCorreoService {

    private final TipoCorreoDAO tipoCorreoDAO;
    private final TipoCorreoMapper tipoCorreoMapper;

    @Override
    @Transactional
    public TipoCorreoResponseDTO crear(TipoCorreoRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoCorreo entity = tipoCorreoMapper.toEntity(dto);
        return tipoCorreoMapper.toResponseDTO(tipoCorreoDAO.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoCorreoResponseDTO> listar() {
        return tipoCorreoDAO.findAll().stream()
                .map(tipoCorreoMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoCorreoResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return tipoCorreoDAO.findById(id)
                .map(tipoCorreoMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("TipoCorreo con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!tipoCorreoDAO.existsById(id)) throw new ResourceNotFoundException("TipoCorreo con ID " + id + " no encontrado");
        tipoCorreoDAO.deleteById(id);
    }

    @Override
    @Transactional
    public TipoCorreoResponseDTO actualizar(Long id, TipoCorreoRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoCorreo entity = tipoCorreoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoCorreo con ID " + id + " no encontrado"));
        entity.setNombreTipo(dto.getNombreTipo());
        return tipoCorreoMapper.toResponseDTO(tipoCorreoDAO.save(entity));
    }
}
