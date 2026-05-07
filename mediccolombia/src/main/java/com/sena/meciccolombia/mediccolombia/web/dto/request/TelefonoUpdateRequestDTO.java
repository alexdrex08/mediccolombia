package com.sena.meciccolombia.mediccolombia.web.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelefonoUpdateRequestDTO {

    private String numero;
    private String complemento;
    private Long idTipoTelefono;
    
}
