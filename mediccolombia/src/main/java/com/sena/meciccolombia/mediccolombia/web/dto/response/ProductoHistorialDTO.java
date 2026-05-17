package com.sena.meciccolombia.mediccolombia.web.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoHistorialDTO {

    private Long idProducto;
    private String nombreProducto;
    private  Integer stock;
    private LocalDateTime fechaCreacion;

    List<MovimientoProdResponseDTO> movimientos;

}