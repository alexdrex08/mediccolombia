package com.sena.meciccolombia.mediccolombia.web.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoHistorialDTO {

    private Long idMovimiento;

    private String tipoMovimiento;

    private Integer cantidad;

    private String motivo;

    private LocalDateTime fechaMovimiento;
}