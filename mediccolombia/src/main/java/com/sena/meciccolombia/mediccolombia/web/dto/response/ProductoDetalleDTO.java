package com.sena.meciccolombia.mediccolombia.web.dto.response;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDetalleDTO {

    private Long id;

    private String usuarioIngresado;

    private String nombreProducto;

    private String categoria;

    private BigDecimal precioVenta;

    private Integer stock;

    private Integer stockMinimo;

    private Integer stockMaximo;

    private String estadoStock;

    private String lote;

    private LocalDateTime fechaIngreso;

    private LocalDateTime fechaModificacion;

    private LocalDateTime fechaExpiracion;


}

