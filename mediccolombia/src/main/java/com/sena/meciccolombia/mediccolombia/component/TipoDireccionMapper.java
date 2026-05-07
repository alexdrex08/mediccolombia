package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;
import com.sena.meciccolombia.mediccolombia.domain.TipoDireccion;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoDireccionRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoDireccionResponseDTO;

@Component
public class TipoDireccionMapper {

    public TipoDireccion toEntity(TipoDireccionRequestDTO dto) {
        if (dto == null) return null;
        return TipoDireccion.builder()
                .nombreTipo(dto.getNombreTipo())
                .build();
    }

    public TipoDireccionResponseDTO toResponseDTO(TipoDireccion entity) {
        if (entity == null) return null;
        return TipoDireccionResponseDTO.builder()
                .id(entity.getId())
                .nombreTipo(entity.getNombreTipo())
                .build();
    }
}
