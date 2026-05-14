package com.sena.meciccolombia.mediccolombia.web.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FiltroBusquedaDetalleResponseDTO {

    private Long id;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private List<DetalleFiltroResponseDTO> criterios;
    
    
}
