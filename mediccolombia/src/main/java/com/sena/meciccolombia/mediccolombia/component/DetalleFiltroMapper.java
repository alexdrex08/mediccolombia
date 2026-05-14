package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;
import com.sena.meciccolombia.mediccolombia.domain.*;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DetalleFiltroRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DetalleFiltroResponseDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DetalleFiltroMapper {

    public DetalleFiltro toEntity(DetalleFiltroRequestDTO dto, FiltroBusqueda filtroBusqueda){
        if (dto == null) return null;
        return DetalleFiltro.builder()
                        .campoFiltro(dto.getCampoFiltro())
                        .tipoDato(dto.getTipoDato())
                        .valorFiltro(dto.getValorFiltro())
                        .filtroBusqueda(filtroBusqueda)
                        .build();
    }

    public DetalleFiltroResponseDTO toResponseDTO(DetalleFiltro entity) {
        if (entity == null) return null;
        return DetalleFiltroResponseDTO.builder()
                .id(entity.getId())
                .campoFiltro(entity.getCampoFiltro())
                .valorFiltro(entity.getValorFiltro())
                .tipoDato(entity.getTipoDato())
                .build();
    }
}
