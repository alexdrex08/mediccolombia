package com.sena.meciccolombia.mediccolombia.web.dto.request;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoCompraRequestDTO {
    
    private Long idProveedor;
    private String observacion;
    private List<DetallePedidoRequestDTO> detalles;
}
