package com.sena.meciccolombia.mediccolombia.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.TipoMovimientoMapper;
import com.sena.meciccolombia.mediccolombia.dao.TipoMovimientoDAO;
import com.sena.meciccolombia.mediccolombia.domain.TipoMovimiento;
import com.sena.meciccolombia.mediccolombia.service.TipoMovimientoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoMovimientoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoMovimientoResponseDTO;

@Service
@RequiredArgsConstructor
public class TipoMovimientoServiceImpl implements TipoMovimientoService {

    private final TipoMovimientoDAO tipo_movimientoDAO;
    private final TipoMovimientoMapper tipo_movimientoMapper;

    @Override
    @Transactional
    public TipoMovimientoResponseDTO crear(TipoMovimientoRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoMovimiento entity = tipo_movimientoMapper.toEntity(dto);
        return tipo_movimientoMapper.toResponseDTO(tipo_movimientoDAO.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoMovimientoResponseDTO> listar() {
        return tipo_movimientoDAO.findAll().stream()
                .map(tipo_movimientoMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoMovimientoResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return tipo_movimientoDAO.findById(id)
                .map(tipo_movimientoMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("TipoMovimiento con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!tipo_movimientoDAO.existsById(id)) throw new RuntimeException("TipoMovimiento con ID " + id + " no encontrado");
        tipo_movimientoDAO.deleteById(id);
    }

    @Override
    @Transactional
    public TipoMovimientoResponseDTO actualizar(Long id, TipoMovimientoRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        TipoMovimiento entity = tipo_movimientoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoMovimiento con ID " + id + " no encontrado"));
        entity.setNombreMovimiento(dto.getNombreMovimiento());
        entity.setDescripcion(dto.getDescripcion());
        return tipo_movimientoMapper.toResponseDTO(tipo_movimientoDAO.save(entity));
    }
}
