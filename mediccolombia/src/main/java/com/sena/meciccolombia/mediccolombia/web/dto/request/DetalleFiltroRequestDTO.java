package com.sena.meciccolombia.mediccolombia.web.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleFiltroRequestDTO {

    private String campoFiltro;
    private String tipoDato;
    private String valorFiltro;
    private Long idFiltroBusqueda;
}
