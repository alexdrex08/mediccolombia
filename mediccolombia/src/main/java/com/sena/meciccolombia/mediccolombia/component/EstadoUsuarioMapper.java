package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;
import com.sena.meciccolombia.mediccolombia.dao.*;
import com.sena.meciccolombia.mediccolombia.domain.*;
import com.sena.meciccolombia.mediccolombia.web.dto.request.EstadoUsuarioRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.EstadoUsuarioResponseDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EstadoUsuarioMapper {

    // DAOs needed to resolve FK relationships injected here as needed per module
    // toEntity and toResponseDTO implemented per module below

    public EstadoUsuarioResponseDTO toResponseDTO(EstadoUsuario entity) {
        if (entity == null) return null;
        return EstadoUsuarioResponseDTO.builder()
                .id(entity.getId())
                // TODO: map remaining fields
                .build();
    }
}
