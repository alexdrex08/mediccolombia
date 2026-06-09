package com.sena.meciccolombia.mediccolombia.web.dto.request;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProyeccionManualRequestDTO {

    private Long idTipoProyeccion;
    private Long idMetodoProyeccion;
    private Long idProducto;
    private Long idCategoria;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}
