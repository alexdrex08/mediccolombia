package com.sena.meciccolombia.mediccolombia.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.FiltroBusquedaMapper;
import com.sena.meciccolombia.mediccolombia.dao.FiltroBusquedaDAO;
import com.sena.meciccolombia.mediccolombia.domain.*;
import com.sena.meciccolombia.mediccolombia.service.FiltroBusquedaService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.FiltroBusquedaRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.FiltroBusquedaResponseDTO;

@Service
@RequiredArgsConstructor
public class FiltroBusquedaServiceImpl implements FiltroBusquedaService {

    private final FiltroBusquedaDAO filtro_busquedaDAO;
    private final FiltroBusquedaMapper filtro_busquedaMapper;
    // Inject additional DAOs here for FK resolution

    @Override
    @Transactional
    public FiltroBusquedaResponseDTO crear(FiltroBusquedaRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        // TODO: resolve FK entities from DAOs before building entity
        throw new UnsupportedOperationException("Implementar resolución de FKs");
    }

    @Override
    @Transactional(readOnly = true)
    public List<FiltroBusquedaResponseDTO> listar() {
        return filtro_busquedaDAO.findAll().stream()
                .map(filtro_busquedaMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FiltroBusquedaResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return filtro_busquedaDAO.findById(id)
                .map(filtro_busquedaMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("FiltroBusqueda con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!filtro_busquedaDAO.existsById(id)) throw new RuntimeException("FiltroBusqueda con ID " + id + " no encontrado");
        filtro_busquedaDAO.deleteById(id);
    }

    @Override
    @Transactional
    public FiltroBusquedaResponseDTO actualizar(Long id, FiltroBusquedaRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        // TODO: resolve FK entities and apply setters
        throw new UnsupportedOperationException("Implementar resolución de FKs");
    }
}
