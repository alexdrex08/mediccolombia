package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;
import com.sena.meciccolombia.mediccolombia.domain.TipoCorreo;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoCorreoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoCorreoResponseDTO;

@Component
public class TipoCorreoMapper {

    public TipoCorreo toEntity(TipoCorreoRequestDTO dto) {
        if (dto == null) return null;
        return TipoCorreo.builder()
                .nombreTipo(dto.getNombreTipo())
                .build();
    }

    public TipoCorreoResponseDTO toResponseDTO(TipoCorreo entity) {
        if (entity == null) return null;
        return TipoCorreoResponseDTO.builder()
                .id(entity.getId())
                .nombreTipo(entity.getNombreTipo())
                .build();
    }
}
