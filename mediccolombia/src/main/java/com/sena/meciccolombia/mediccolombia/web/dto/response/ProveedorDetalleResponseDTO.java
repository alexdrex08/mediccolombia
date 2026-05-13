package com.sena.meciccolombia.mediccolombia.web.dto.response;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorDetalleResponseDTO {

    private Long id;
    private String nombreProv;
    private String nit;
    private List<CorreoResponseProveedorDTO> correos;
    private List<TelefonoResponseProveedorDTO> telefonos;
    private List<DireccionResponseProveedorDTO> direcciones;

}
