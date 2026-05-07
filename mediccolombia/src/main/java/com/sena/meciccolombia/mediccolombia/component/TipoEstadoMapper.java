package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;
import com.sena.meciccolombia.mediccolombia.domain.TipoEstado;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoEstadoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoEstadoResponseDTO;

@Component
public class TipoEstadoMapper {

    public TipoEstado toEntity(TipoEstadoRequestDTO dto) {
        if (dto == null) return null;
        return TipoEstado.builder()
                .nombreTipo(dto.getNombreTipo())
                .build();
    }

    public TipoEstadoResponseDTO toResponseDTO(TipoEstado entity) {
        if (entity == null) return null;
        return TipoEstadoResponseDTO.builder()
                .id(entity.getId())
                .nombreTipo(entity.getNombreTipo())
                .build();
    }
}
