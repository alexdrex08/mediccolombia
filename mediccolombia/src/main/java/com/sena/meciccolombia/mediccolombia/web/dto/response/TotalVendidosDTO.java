package com.sena.meciccolombia.mediccolombia.web.dto.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TotalVendidosDTO {
    
    private List<ProductosVendidosResponseDTO> productos;
    private BigDecimal totalVenta;
}
