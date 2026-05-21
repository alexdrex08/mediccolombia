package com.sena.meciccolombia.mediccolombia.web.dto.request;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVentaRequestDTO {
    
    private Long idProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}
