package com.sena.meciccolombia.mediccolombia.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoResumenDTO {

    private Long id;

    private String nombreProducto;

    private String categoria;

    private Integer stock;

    private String estadoStock;
}