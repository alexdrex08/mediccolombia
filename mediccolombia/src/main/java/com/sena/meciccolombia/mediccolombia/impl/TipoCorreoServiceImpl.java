package com.sena.meciccolombia.mediccolombia.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.TipoCorreoMapper;
import com.sena.meciccolombia.mediccolombia.dao.TipoCorreoDAO;
import com.sena.meciccolombia.mediccolombia.domain.TipoCorreo;
import com.sena.meciccolombia.mediccolombia.service.TipoCorreoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoCorreoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoCorreoResponseDTO;

@Service
@RequiredArgsConstructor
public class TipoCorreoServiceImpl implements TipoCorreoService {

    private final TipoCorreoDAO tipo_correoDAO;
    private final TipoCorreoMapper tipo_correoMapper;

    @Override
    @Transactional
    public TipoCorreoResponseDTO crear(TipoCorreoRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoCorreo entity = tipo_correoMapper.toEntity(dto);
        return tipo_correoMapper.toResponseDTO(tipo_correoDAO.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoCorreoResponseDTO> listar() {
        return tipo_correoDAO.findAll().stream()
                .map(tipo_correoMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoCorreoResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return tipo_correoDAO.findById(id)
                .map(tipo_correoMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("TipoCorreo con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!tipo_correoDAO.existsById(id)) throw new RuntimeException("TipoCorreo con ID " + id + " no encontrado");
        tipo_correoDAO.deleteById(id);
    }

    @Override
    @Transactional
    public TipoCorreoResponseDTO actualizar(Long id, TipoCorreoRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoCorreo entity = tipo_correoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoCorreo con ID " + id + " no encontrado"));
        entity.setNombreTipo(dto.getNombreTipo());
        return tipo_correoMapper.toResponseDTO(tipo_correoDAO.save(entity));
    }
}
