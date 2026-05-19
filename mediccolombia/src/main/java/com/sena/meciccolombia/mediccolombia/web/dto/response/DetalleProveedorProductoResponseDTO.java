package com.sena.meciccolombia.mediccolombia.web.dto.response;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleProveedorProductoResponseDTO {
    
    private Long idProveedor;
    private String nombreProveedor;
    private Long idProducto;
    private String nombreProducto;
    private BigDecimal precioUnitario;
    
}
