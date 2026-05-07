package com.sena.meciccolombia.mediccolombia.web.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CorreoResponseDTO {

    private Long id;
    private String correoElectronico;
    private String tipoCorreo;
    private Long idCliente;
    private Long idProveedor;
}
