package com.sena.meciccolombia.mediccolombia.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import com.sena.meciccolombia.mediccolombia.component.DetalleFiltroMapper;
import com.sena.meciccolombia.mediccolombia.component.FiltroBusquedaMapper;
import com.sena.meciccolombia.mediccolombia.dao.DetalleFiltroDAO;
import com.sena.meciccolombia.mediccolombia.dao.FiltroBusquedaDAO;
import com.sena.meciccolombia.mediccolombia.domain.FiltroBusqueda;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.FiltroBusquedaService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.FiltroBusquedaRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DetalleFiltroResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.FiltroBusquedaDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.FiltroBusquedaResponseDTO;

@Service
@RequiredArgsConstructor
public class FiltroBusquedaServiceImpl implements FiltroBusquedaService {

    private final FiltroBusquedaDAO filtroBusquedaDAO;
    private final FiltroBusquedaMapper filtroBusquedaMapper;
    private final DetalleFiltroDAO detalleFiltroDAO;
    private final DetalleFiltroMapper detalleFiltroMapper;

    @Override
    @Transactional
    public FiltroBusquedaResponseDTO crear(FiltroBusquedaRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        FiltroBusqueda filtroBusqueda = filtroBusquedaMapper.toEntity(dto);
        return filtroBusquedaMapper.toResponseDTO(filtroBusquedaDAO.save(filtroBusqueda));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FiltroBusquedaResponseDTO> listar() {
        return filtroBusquedaDAO.findAll().stream()
                .map(filtroBusquedaMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FiltroBusquedaResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return filtroBusquedaDAO.findById(id)
                .map(filtroBusquedaMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("FiltroBusqueda con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!filtroBusquedaDAO.existsById(id)) throw new ResourceNotFoundException("FiltroBusqueda con ID " + id + " no encontrado");
        filtroBusquedaDAO.deleteById(id);
    }

    @Override
    @Transactional
    public FiltroBusquedaResponseDTO actualizar(Long id, FiltroBusquedaRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");

        FiltroBusqueda filtro = filtroBusquedaDAO.findById(id)
                                                .orElseThrow( () -> new ResourceNotFoundException("FiltroBusqueda con ID:" + id + " no fue encontrado o no existe"));
        filtro.setDescripcion(dto.getDescripcion());
        return filtroBusquedaMapper.toResponseDTO(filtroBusquedaDAO.save(filtro));
    }

    @Override
    public FiltroBusquedaDetalleResponseDTO obtenerDetalle(Long id) {
        if(id == null) throw new IllegalArgumentException("El ID no puede ser nulo");

        FiltroBusqueda filtroBusqueda = filtroBusquedaDAO.findById(id)
                                        .orElseThrow(() -> new ResourceNotFoundException("El filtroBusqueda con ID:" + id + " no fue encontrado o no existe"));
      
        List<DetalleFiltroResponseDTO> criterios = detalleFiltroDAO.findByFiltroBusquedaId(id)
                                                                    .stream()
                                                                    .map(detalleFiltroMapper::toResponseDTO)
                                                                    .toList();
    return filtroBusquedaMapper.toDetalleResponseDTO(filtroBusqueda, criterios);
    }
}
