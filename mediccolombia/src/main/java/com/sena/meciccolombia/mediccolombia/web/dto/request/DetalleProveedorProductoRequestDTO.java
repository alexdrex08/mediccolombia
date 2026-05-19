package com.sena.meciccolombia.mediccolombia.web.dto.request;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleProveedorProductoRequestDTO {
     private Long idProveedor;
     private Long idProducto;
     private BigDecimal precioUnitario;
    
}
