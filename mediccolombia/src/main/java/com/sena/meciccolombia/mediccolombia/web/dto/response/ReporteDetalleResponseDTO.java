package com.sena.meciccolombia.mediccolombia.web.dto.response;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteDetalleResponseDTO {
    
    private Long idReporte;
    private String tipoReporte;
    private LocalDateTime fechaGeneracion;
    private String nombreUsuario;
    private String urlResultado;

    private Object contenido;
}
