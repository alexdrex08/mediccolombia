package com.sena.meciccolombia.mediccolombia.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoFiltroDTO {

    private String nombre;

    private Long idCategoria;

    private Boolean stockBajo;

    private Boolean proximosAVencer;
}