package com.sena.meciccolombia.mediccolombia.web.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CorreoRequestDTO {

    private String correoElectronico;
    private Long idCliente;
    private Long idProveedor;
    private Long idTipoCorreo;
}
