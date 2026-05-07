package com.sena.meciccolombia.mediccolombia.web.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DireccionRequestDTO {

    private String direccion;
    private String complemento;
    private Long idCliente;
    private Long idProveedor;
    private Long idBarrio;
    private Long idTipoDireccion;
}
