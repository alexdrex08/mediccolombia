package com.sena.meciccolombia.mediccolombia.web.dto.response;

import lombok.NoArgsConstructor;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoMasVendidoResponseDTO {
    
    private Long idProducto;
    private String nombreProducto;
    private Integer totalUnidadesVendidas;
}
