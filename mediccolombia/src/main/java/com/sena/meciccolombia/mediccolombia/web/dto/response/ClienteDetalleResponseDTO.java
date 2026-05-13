package com.sena.meciccolombia.mediccolombia.web.dto.response;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDetalleResponseDTO {
    
    private Long id;
    private String nombreCliente;
    private String identificacion;
    private List<CorreoResponseClienteDTO> correos;
    private List<DireccionResponseClienteDTO> direcciones;
    private List<TelefonoResponseClienteDTO> telefonos;
}
