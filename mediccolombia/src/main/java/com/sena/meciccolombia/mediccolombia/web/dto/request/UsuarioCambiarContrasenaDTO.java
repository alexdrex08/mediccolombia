package com.sena.meciccolombia.mediccolombia.web.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioCambiarContrasenaDTO {

    private String contrasenaActual;
    private String nuevaContrasena;
    
}
