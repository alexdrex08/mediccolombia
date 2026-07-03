package com.sena.meciccolombia.mediccolombia.web.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioCreateRequestDTO {
    
    private Long id;
    private String nombre;
    private String correo;
    private String identificacion;
    private String contrasena;
    private String rol;
}
