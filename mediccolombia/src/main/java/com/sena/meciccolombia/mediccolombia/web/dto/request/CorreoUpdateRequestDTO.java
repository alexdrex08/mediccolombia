package com.sena.meciccolombia.mediccolombia.web.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CorreoUpdateRequestDTO {

    private String correoElectronico;
    private Long idTipoCorreo;
}
