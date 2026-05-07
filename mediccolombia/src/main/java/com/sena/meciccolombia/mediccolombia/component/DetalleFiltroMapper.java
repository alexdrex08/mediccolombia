package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;
import com.sena.meciccolombia.mediccolombia.dao.*;
import com.sena.meciccolombia.mediccolombia.domain.*;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DetalleFiltroRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DetalleFiltroResponseDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DetalleFiltroMapper {

    // DAOs needed to resolve FK relationships injected here as needed per module
    // toEntity and toResponseDTO implemented per module below

    public DetalleFiltroResponseDTO toResponseDTO(DetalleFiltro entity) {
        if (entity == null) return null;
        return DetalleFiltroResponseDTO.builder()
                .id(entity.getId())
                // TODO: map remaining fields
                .build();
    }
}
