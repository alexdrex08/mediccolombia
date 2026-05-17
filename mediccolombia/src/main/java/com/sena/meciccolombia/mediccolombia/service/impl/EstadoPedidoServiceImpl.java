package com.sena.meciccolombia.mediccolombia.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.EstadoPedidoMapper;
import com.sena.meciccolombia.mediccolombia.dao.EstadoPedidoDAO;
import com.sena.meciccolombia.mediccolombia.domain.EstadoPedido;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.EstadoPedidoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.EstadoPedidoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.EstadoPedidoResponseDTO;

@Service
@RequiredArgsConstructor
public class EstadoPedidoServiceImpl implements EstadoPedidoService {

    private final EstadoPedidoDAO estadoPedidoDAO;
    private final EstadoPedidoMapper estadoPedidoMapper;

    @Override
    @Transactional
    public EstadoPedidoResponseDTO crear(EstadoPedidoRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        EstadoPedido entity = estadoPedidoMapper.toEntity(dto);
        return estadoPedidoMapper.toResponseDTO(estadoPedidoDAO.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoPedidoResponseDTO> listar() {
        return estadoPedidoDAO.findAll().stream()
                .map(estadoPedidoMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoPedidoResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return estadoPedidoDAO.findById(id)
                .map(estadoPedidoMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("EstadoPedido con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!estadoPedidoDAO.existsById(id)) throw new ResourceNotFoundException("EstadoPedido con ID " + id + " no encontrado");
        estadoPedidoDAO.deleteById(id);
    }

    @Override
    @Transactional
    public EstadoPedidoResponseDTO actualizar(Long id, EstadoPedidoRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        EstadoPedido entity = estadoPedidoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("EstadoPedido con ID " + id + " no encontrado"));
        entity.setDescripcion(dto.getDescripcion());
        return estadoPedidoMapper.toResponseDTO(estadoPedidoDAO.save(entity));
    }
}
