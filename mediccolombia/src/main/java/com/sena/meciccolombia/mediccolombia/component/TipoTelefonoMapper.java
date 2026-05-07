package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;
import com.sena.meciccolombia.mediccolombia.domain.TipoTelefono;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoTelefonoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoTelefonoResponseDTO;

@Component
public class TipoTelefonoMapper {

    public TipoTelefono toEntity(TipoTelefonoRequestDTO dto) {
        if (dto == null) return null;
        return TipoTelefono.builder()
                .nombreTipo(dto.getNombreTipo())
                .build();
    }

    public TipoTelefonoResponseDTO toResponseDTO(TipoTelefono entity) {
        if (entity == null) return null;
        return TipoTelefonoResponseDTO.builder()
                .id(entity.getId())
                .nombreTipo(entity.getNombreTipo())
                .build();
    }
}
