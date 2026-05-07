package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;
import com.sena.meciccolombia.mediccolombia.domain.BarrioDireccion;
import com.sena.meciccolombia.mediccolombia.web.dto.request.BarrioDireccionRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.BarrioDireccionResponseDTO;

@Component
public class BarrioDireccionMapper {

    public BarrioDireccion toEntity(BarrioDireccionRequestDTO dto) {
        if (dto == null) return null;
        return BarrioDireccion.builder()
                .nombreBarrio(dto.getNombreBarrio())
                .build();
    }

    public BarrioDireccionResponseDTO toResponseDTO(BarrioDireccion entity) {
        if (entity == null) return null;
        return BarrioDireccionResponseDTO.builder()
                .id(entity.getId())
                .nombreBarrio(entity.getNombreBarrio())
                .build();
    }
}
