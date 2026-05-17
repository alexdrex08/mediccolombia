package com.sena.meciccolombia.mediccolombia.web.dto.request;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoCreateRequestDto {

    private String nombreProducto;

    private LocalDateTime fechaExpiracion;

    private Integer stock;

    private String lote;

    private Integer stockMinimo;

    private Integer stockMaximo;

    private Long idCategoria;

    private Long idUsuario;

}   
