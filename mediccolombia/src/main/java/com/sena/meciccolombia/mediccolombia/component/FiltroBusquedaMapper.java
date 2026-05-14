package com.sena.meciccolombia.mediccolombia.component;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;
import com.sena.meciccolombia.mediccolombia.domain.*;
import com.sena.meciccolombia.mediccolombia.web.dto.request.FiltroBusquedaRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DetalleFiltroResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.FiltroBusquedaDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.FiltroBusquedaResponseDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FiltroBusquedaMapper {

    public FiltroBusqueda toEntity(FiltroBusquedaRequestDTO dto){
        if (dto == null) return null;
        return FiltroBusqueda.builder()
                            .descripcion(dto.getDescripcion())
                            .fechaCreacion(LocalDateTime.now())
                            .build();
    }

    public FiltroBusquedaResponseDTO toResponseDTO(FiltroBusqueda entity) {
        if (entity == null) return null;
        return FiltroBusquedaResponseDTO.builder()
                .id(entity.getId())
                .descripcion(entity.getDescripcion())
                .fechaCreacion(entity.getFechaCreacion())
                .build();
    }

    public FiltroBusquedaDetalleResponseDTO toDetalleResponseDTO(FiltroBusqueda entity, List<DetalleFiltroResponseDTO> criterios){
        if (entity == null) return null;
        return FiltroBusquedaDetalleResponseDTO.builder()
                                        .id(entity.getId())
                                        .descripcion(entity.getDescripcion())
                                        .fechaCreacion(entity.getFechaCreacion())
                                        .criterios(criterios)
                                        .build();
    }
}
