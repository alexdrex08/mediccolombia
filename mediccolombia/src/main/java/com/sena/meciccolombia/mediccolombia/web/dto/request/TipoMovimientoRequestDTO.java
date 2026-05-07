package com.sena.meciccolombia.mediccolombia.web.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoMovimientoRequestDTO {

    private String nombreMovimiento;
    private String descripcion;
}
