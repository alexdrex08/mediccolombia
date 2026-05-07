package com.sena.meciccolombia.mediccolombia.web.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DireccionResponseDTO {

    private Long id;
    private String direccion;
    private String complemento;
    private String barrio;
    private Long idCliente;
    private Long idProveedor;
    private String tipoDireccion;
}
