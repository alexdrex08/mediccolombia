package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;
import com.sena.meciccolombia.mediccolombia.dao.*;
import com.sena.meciccolombia.mediccolombia.domain.*;
import com.sena.meciccolombia.mediccolombia.web.dto.request.FiltroBusquedaRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.FiltroBusquedaResponseDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FiltroBusquedaMapper {

    // DAOs needed to resolve FK relationships injected here as needed per module
    // toEntity and toResponseDTO implemented per module below

    public FiltroBusquedaResponseDTO toResponseDTO(FiltroBusqueda entity) {
        if (entity == null) return null;
        return FiltroBusquedaResponseDTO.builder()
                .id(entity.getId())
                // TODO: map remaining fields
                .build();
    }
}
