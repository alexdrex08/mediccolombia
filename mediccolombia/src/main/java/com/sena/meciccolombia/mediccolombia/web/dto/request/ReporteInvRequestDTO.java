package com.sena.meciccolombia.mediccolombia.web.dto.request;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteInvRequestDTO {

    private String tipoReporte;
    private Long idUsuario;
    private Long idFiltroBusqueda;
    private Long idReferencia;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    
}
