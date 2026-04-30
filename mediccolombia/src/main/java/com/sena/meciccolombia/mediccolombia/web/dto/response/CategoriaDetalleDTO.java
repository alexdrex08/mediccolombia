package com.sena.meciccolombia.mediccolombia.web.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaDetalleDTO {

    private Long idCategoria;

    private String nombreCategoria;

    private String descripcion;
    
}
