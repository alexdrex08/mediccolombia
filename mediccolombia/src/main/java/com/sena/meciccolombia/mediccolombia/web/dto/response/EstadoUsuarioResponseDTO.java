package com.sena.meciccolombia.mediccolombia.web.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoUsuarioResponseDTO {

    private Long id;
    private String observacion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String nombreUsuario;
    private String tipoEstado;
}
