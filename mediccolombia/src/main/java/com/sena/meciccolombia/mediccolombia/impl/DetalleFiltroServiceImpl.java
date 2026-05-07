package com.sena.meciccolombia.mediccolombia.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.DetalleFiltroMapper;
import com.sena.meciccolombia.mediccolombia.dao.DetalleFiltroDAO;
import com.sena.meciccolombia.mediccolombia.domain.*;
import com.sena.meciccolombia.mediccolombia.service.DetalleFiltroService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DetalleFiltroRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DetalleFiltroResponseDTO;

@Service
@RequiredArgsConstructor
public class DetalleFiltroServiceImpl implements DetalleFiltroService {

    private final DetalleFiltroDAO detalle_filtroDAO;
    private final DetalleFiltroMapper detalle_filtroMapper;
    // Inject additional DAOs here for FK resolution

    @Override
    @Transactional
    public DetalleFiltroResponseDTO crear(DetalleFiltroRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        // TODO: resolve FK entities from DAOs before building entity
        throw new UnsupportedOperationException("Implementar resolución de FKs");
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleFiltroResponseDTO> listar() {
        return detalle_filtroDAO.findAll().stream()
                .map(detalle_filtroMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleFiltroResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return detalle_filtroDAO.findById(id)
                .map(detalle_filtroMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("DetalleFiltro con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!detalle_filtroDAO.existsById(id)) throw new RuntimeException("DetalleFiltro con ID " + id + " no encontrado");
        detalle_filtroDAO.deleteById(id);
    }

    @Override
    @Transactional
    public DetalleFiltroResponseDTO actualizar(Long id, DetalleFiltroRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        // TODO: resolve FK entities and apply setters
        throw new UnsupportedOperationException("Implementar resolución de FKs");
    }
}
