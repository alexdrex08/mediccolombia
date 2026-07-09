package com.sena.meciccolombia.mediccolombia.web.dto.response;

import java.time.LocalDateTime;

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

    private LocalDateTime fechaExpiracion;

}