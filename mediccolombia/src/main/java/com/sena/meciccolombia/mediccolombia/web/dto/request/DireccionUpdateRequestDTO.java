package com.sena.meciccolombia.mediccolombia.web.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DireccionUpdateRequestDTO {

    private String direccion;
    private String complemento;
    private Long idTipoDireccion;
    private Long idBarrioDireccion;
    
}
