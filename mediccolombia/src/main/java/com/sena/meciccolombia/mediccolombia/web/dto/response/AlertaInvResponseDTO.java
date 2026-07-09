package com.sena.meciccolombia.mediccolombia.web.dto.response;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertaInvResponseDTO {
    
    private Long id;
    private LocalDateTime fechaCreacion;
    private String tipoAlerta;
    private String descripcion;
    private LocalDateTime fechaResolucion;
    private String nombreProducto;
}
