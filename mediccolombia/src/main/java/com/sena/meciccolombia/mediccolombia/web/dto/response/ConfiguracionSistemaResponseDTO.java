package com.sena.meciccolombia.mediccolombia.web.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiguracionSistemaResponseDTO {
    private Long id;
    private String clave;
    private String valor;
    private String descripcion;
    private String categoria;
}