package com.sena.meciccolombia.mediccolombia.web.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleFiltroResponseDTO {

    private Long id;
    private String campoFiltro;
    private String tipoDato;
    private String valorFiltro;
}
