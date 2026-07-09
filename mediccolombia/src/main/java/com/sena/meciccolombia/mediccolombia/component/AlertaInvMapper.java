package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;

import com.sena.meciccolombia.mediccolombia.domain.AlertaInv;
import com.sena.meciccolombia.mediccolombia.web.dto.response.AlertaInvResponseDTO;

@Component
public class AlertaInvMapper {
    public AlertaInvResponseDTO toResponseDTO(AlertaInv alerta){
        if (alerta == null) return null;
        return AlertaInvResponseDTO.builder()
            .id(alerta.getId())
            .fechaCreacion(alerta.getFechaCreacion())
            .tipoAlerta(alerta.getTipoAlerta())
            .fechaResolucion(alerta.getFechaResolucion())
            .descripcion(alerta.getDescripcion())
            .nombreProducto(alerta.getProducto().getNombreProducto())
            .build();
    }
}
