package com.sena.meciccolombia.mediccolombia.web.dto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDetalleDTO {

    private Long id;

    private String nombreProducto;

    private String categoria;

    private Integer stock;

    private Integer stockMinimo;

    private Integer stockMaximo;

    private String estadoStock;

    private String lote;

    private LocalDateTime fechaIngreso;

    private LocalDateTime fechaModificacion;

    private LocalDateTime fechaExpiracion;
}
