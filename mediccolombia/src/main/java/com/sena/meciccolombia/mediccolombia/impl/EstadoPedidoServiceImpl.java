package com.sena.meciccolombia.mediccolombia.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.EstadoPedidoMapper;
import com.sena.meciccolombia.mediccolombia.dao.EstadoPedidoDAO;
import com.sena.meciccolombia.mediccolombia.domain.EstadoPedido;
import com.sena.meciccolombia.mediccolombia.service.EstadoPedidoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.EstadoPedidoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.EstadoPedidoResponseDTO;

@Service
@RequiredArgsConstructor
public class EstadoPedidoServiceImpl implements EstadoPedidoService {

    private final EstadoPedidoDAO estado_pedidoDAO;
    private final EstadoPedidoMapper estado_pedidoMapper;

    @Override
    @Transactional
    public EstadoPedidoResponseDTO crear(EstadoPedidoRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        EstadoPedido entity = estado_pedidoMapper.toEntity(dto);
        return estado_pedidoMapper.toResponseDTO(estado_pedidoDAO.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoPedidoResponseDTO> listar() {
        return estado_pedidoDAO.findAll().stream()
                .map(estado_pedidoMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoPedidoResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return estado_pedidoDAO.findById(id)
                .map(estado_pedidoMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("EstadoPedido con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!estado_pedidoDAO.existsById(id)) throw new RuntimeException("EstadoPedido con ID " + id + " no encontrado");
        estado_pedidoDAO.deleteById(id);
    }

    @Override
    @Transactional
    public EstadoPedidoResponseDTO actualizar(Long id, EstadoPedidoRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        EstadoPedido entity = estado_pedidoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("EstadoPedido con ID " + id + " no encontrado"));
        entity.setDescripcion(dto.getDescripcion());
        return estado_pedidoMapper.toResponseDTO(estado_pedidoDAO.save(entity));
    }
}
