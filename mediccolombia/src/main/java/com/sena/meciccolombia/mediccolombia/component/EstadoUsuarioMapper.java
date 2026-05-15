package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;
import com.sena.meciccolombia.mediccolombia.domain.*;
import com.sena.meciccolombia.mediccolombia.web.dto.request.EstadoUsuarioRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.EstadoUsuarioResponseDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EstadoUsuarioMapper {

    public EstadoUsuario toEntity(EstadoUsuarioRequestDTO dto, Usuario usuario, TipoEstado tipoEstado){
        if (dto == null) return null;
        return EstadoUsuario.builder()
                            .observacion(dto.getObservacion())
                            .fechaInicio(dto.getFechaInicio())
                            .fechaFin(dto.getFechaFin())
                            .usuario(usuario)
                            .tipoEstado(tipoEstado)
                            .build();
    }

    public EstadoUsuarioResponseDTO toResponseDTO(EstadoUsuario entity) {
        if (entity == null) return null;
        return EstadoUsuarioResponseDTO.builder()
                .id(entity.getId())
                .observacion(entity.getObservacion())
                .fechaInicio(entity.getFechaInicio())
                .fechaFin(entity.getFechaFin())
                .nombreUsuario(entity.getUsuario().getNombre())
                .tipoEstado(entity.getTipoEstado().getNombreTipo())
                .build();
    }
}
