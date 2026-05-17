package com.sena.meciccolombia.mediccolombia.web.dto.response;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoProdResponseDTO {
    
    private Long id;
    private LocalDateTime fechaMovimiento;
    private Integer cantidad;
    private String movimiento;
    private String pickerChecker;
    private String tipoMovimiento;
    private String nombreProducto;
    private Integer stockResultante;
    private String nombreUsuario;
}
