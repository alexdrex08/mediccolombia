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
public class PedidoCompraResponseDTO {
    private Long id;
    private LocalDateTime fechaPedido;
    private String nombreProveedor;
    private String estadoPedido;
    private String observacion;
    private BigDecimal totalPedido;
    private List<DetallePedidoResponseDTO> detalles;
}
