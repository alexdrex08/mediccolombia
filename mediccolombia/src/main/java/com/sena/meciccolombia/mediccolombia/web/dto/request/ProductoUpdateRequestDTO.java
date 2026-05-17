package com.sena.meciccolombia.mediccolombia.web.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoUpdateRequestDTO {

    private String nombreProducto;

    private LocalDateTime fechaExpiracion;

    private String lote;

    private Integer stock;

    private Integer stockMinimo;

    private Integer stockMaximo;

    private Long idCategoria;
}