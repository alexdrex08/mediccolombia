package com.sena.meciccolombia.mediccolombia.web.dto.response;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteInvResponseDTO {

    private Long id;
    private LocalDateTime fechaGeneracion;
    private String tipoReporte;
    private String tipoResultado;
    private String urlResultado;
    private String nombreUsuario;
    
}
