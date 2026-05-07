package com.sena.meciccolombia.mediccolombia.web.dto.request;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoUsuarioRequestDTO {

    private String observacion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Long idUsuario;
    private Long idTipoEstado;
}
