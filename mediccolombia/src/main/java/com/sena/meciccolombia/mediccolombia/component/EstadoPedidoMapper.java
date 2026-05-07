package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;
import com.sena.meciccolombia.mediccolombia.domain.EstadoPedido;
import com.sena.meciccolombia.mediccolombia.web.dto.request.EstadoPedidoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.EstadoPedidoResponseDTO;

@Component
public class EstadoPedidoMapper {

    public EstadoPedido toEntity(EstadoPedidoRequestDTO dto) {
        if (dto == null) return null;
        return EstadoPedido.builder()
                .descripcion(dto.getDescripcion())
                .build();
    }

    public EstadoPedidoResponseDTO toResponseDTO(EstadoPedido entity) {
        if (entity == null) return null;
        return EstadoPedidoResponseDTO.builder()
                .id(entity.getId())
                .descripcion(entity.getDescripcion())
                .build();
    }
}
