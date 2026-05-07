package com.sena.meciccolombia.mediccolombia.web.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoMovimientoResponseDTO {

    private Long id;

    private String nombreMovimiento;
    private String descripcion;
}
