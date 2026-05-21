package com.sena.meciccolombia.mediccolombia.web.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CambiarEstadoPedidoRequestDTO {
    
    private Long idEstadoPedido;
    private String observacion;
}
