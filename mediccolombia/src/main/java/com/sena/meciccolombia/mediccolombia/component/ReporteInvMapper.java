package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;

import com.sena.meciccolombia.mediccolombia.domain.ReporteInv;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ReporteInvResponseDTO;

@Component
public class ReporteInvMapper {

    public ReporteInvResponseDTO toResponseDTO(ReporteInv reporte) {
        if (reporte == null) return null;
        return ReporteInvResponseDTO.builder()
                .id(reporte.getId())
                .fechaGeneracion(reporte.getFechaGeneracion())
                .tipoReporte(reporte.getTipoReporte())
                .tipoResultado(reporte.getTipoResultado())
                .urlResultado(reporte.getResultado())
                .nombreUsuario(reporte.getUsuario() !=  null
                                ? reporte.getUsuario().getNombre(): null)
                .build();
    }
    
}
