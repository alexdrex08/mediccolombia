package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;
import com.sena.meciccolombia.mediccolombia.domain.TipoProyeccion;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TipoProyeccionRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TipoProyeccionResponseDTO;

@Component
public class TipoProyeccionMapper {

    public TipoProyeccion toEntity(TipoProyeccionRequestDTO dto) {
        if (dto == null) return null;
        return TipoProyeccion.builder()
                .nombreProyeccion(dto.getNombreProyeccion())
                .build();
    }

    public TipoProyeccionResponseDTO toResponseDTO(TipoProyeccion entity) {
        if (entity == null) return null;
        return TipoProyeccionResponseDTO.builder()
                .id(entity.getId())
                .nombreProyeccion(entity.getNombreProyeccion())
                .build();
    }
}
