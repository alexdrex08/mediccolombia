package com.sena.meciccolombia.mediccolombia.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.DetalleFiltroMapper;
import com.sena.meciccolombia.mediccolombia.dao.DetalleFiltroDAO;
import com.sena.meciccolombia.mediccolombia.dao.FiltroBusquedaDAO;
import com.sena.meciccolombia.mediccolombia.domain.*;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.DetalleFiltroService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DetalleFiltroRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DetalleFiltroResponseDTO;

@Service
@RequiredArgsConstructor
public class DetalleFiltroServiceImpl implements DetalleFiltroService {

    private final DetalleFiltroDAO detalleFiltroDAO;
    private final FiltroBusquedaDAO filtroBusquedaDAO;
    private final DetalleFiltroMapper detalleFiltroMapper;

    @Override
    @Transactional
    public DetalleFiltroResponseDTO crear(DetalleFiltroRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");

        FiltroBusqueda filtro = filtroBusquedaDAO.findById(dto.getIdFiltroBusqueda())
                                                .orElseThrow(() -> new ResourceNotFoundException("El FiltroBusqueda con ID:" + dto.getIdFiltroBusqueda() + " no fue encontrado o no existe"));
        DetalleFiltro detalleFiltro = detalleFiltroMapper.toEntity(dto, filtro);
        return detalleFiltroMapper.toResponseDTO(detalleFiltroDAO.save(detalleFiltro));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleFiltroResponseDTO> listarPorFiltro(Long idFiltroBusqueda) {
        if(idFiltroBusqueda == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return detalleFiltroDAO.findByFiltroBusquedaId(idFiltroBusqueda)
            .stream().map(detalleFiltroMapper::toResponseDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleFiltroResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return detalleFiltroDAO.findById(id)
                .map(detalleFiltroMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("DetalleFiltro con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!detalleFiltroDAO.existsById(id)) throw new ResourceNotFoundException("DetalleFiltro con ID " + id + " no encontrado");
        detalleFiltroDAO.deleteById(id);
    }

    @Override
    @Transactional
    public DetalleFiltroResponseDTO actualizar(Long id, DetalleFiltroRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");

        DetalleFiltro detalle = detalleFiltroDAO.findById(id)
                                                .orElseThrow(() -> new ResourceNotFoundException("DetalleFiltro con ID: " + id + " no encontrado o no existe"));
        detalle.setCampoFiltro(dto.getCampoFiltro());
        detalle.setTipoDato(dto.getTipoDato());
        detalle.setValorFiltro(dto.getValorFiltro());
        
        return detalleFiltroMapper.toResponseDTO(detalleFiltroDAO.save(detalle));
    }
}
