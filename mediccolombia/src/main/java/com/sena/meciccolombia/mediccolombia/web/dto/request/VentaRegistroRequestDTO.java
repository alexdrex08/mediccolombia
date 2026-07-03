package com.sena.meciccolombia.mediccolombia.web.dto.request;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaRegistroRequestDTO {

    private Long idCliente;
    private Long idUsuario;
    private String medioPago;
    private List<DetalleVentaRequestDTO> detalles;
    
}
