package com.sena.meciccolombia.mediccolombia.web.dto.response;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDetalleMovimientosResponseDto {
    

    private Long id;
    private String nombre;
    private String correo;
    private String rol;
    private List<MovimientoProdResponseDTO> movimientos;
    
}