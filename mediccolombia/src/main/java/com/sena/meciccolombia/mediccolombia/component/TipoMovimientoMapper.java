package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;
import com.sena.meciccolombia.mediccolombia.domain.TipoMovimiento;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoMovimientoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoMovimientoResponseDTO;

@Component
public class TipoMovimientoMapper {

    public TipoMovimiento toEntity(TipoMovimientoRequestDTO dto) {
        if (dto == null) return null;
        return TipoMovimiento.builder()
                .nombreMovimiento(dto.getNombreMovimiento())
                .descripcion(dto.getDescripcion())
                .build();
    }

    public TipoMovimientoResponseDTO toResponseDTO(TipoMovimiento entity) {
        if (entity == null) return null;
        return TipoMovimientoResponseDTO.builder()
                .id(entity.getId())
                .nombreMovimiento(entity.getNombreMovimiento())
                .descripcion(entity.getDescripcion())
                .build();
    }
}
