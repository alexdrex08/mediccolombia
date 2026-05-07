package com.sena.meciccolombia.mediccolombia.web.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelefonoRequestDTO {

    private String numero;
    private String complemento;
    private Long idCliente;
    private Long idProveedor;
    private Long idTipoTelefono;
}
