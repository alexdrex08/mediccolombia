package com.sena.meciccolombia.mediccolombia.web.dto.response;

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