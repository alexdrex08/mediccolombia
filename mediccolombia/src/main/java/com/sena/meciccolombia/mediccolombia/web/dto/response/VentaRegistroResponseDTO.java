package com.sena.meciccolombia.mediccolombia.web.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaRegistroResponseDTO {

    private Long id;
    private LocalDateTime fechaVenta;
    private String nombreCliente;
    private String nombreUsuario;
    private BigDecimal totalVenta;
    private List<DetalleVentaResponseDTO> detalles;
    
}
