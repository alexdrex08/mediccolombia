package com.sena.meciccolombia.mediccolombia.web.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoProdRequestDTO {
    
    private Integer cantidad;
    private String movimiento;
    private String pickerChecker;
    private Long idProducto;
    private Long idTipoMovimiento;
    private Long idUsuario;
}
