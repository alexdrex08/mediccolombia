package com.sena.meciccolombia.mediccolombia.web.dto.response;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDetalleResponseDTO {

    private Long id;
    private String nombre;
    private String correo;
    private String identificacion;
    private String rol;
    private String fotoPerfil;
    private List<EstadoUsuarioResponseDTO> estadoUsuario;
    
}
