package com.sena.meciccolombia.mediccolombia.web.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelefonoResponseDTO {

    private Long id;
    private String numero;
    private String complemento;
    private String tipoTelefono;
    private Long idCliente;
    private Long idProveedor;
}
