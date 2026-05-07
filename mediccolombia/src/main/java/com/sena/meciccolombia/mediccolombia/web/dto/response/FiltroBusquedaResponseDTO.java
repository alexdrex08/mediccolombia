package com.sena.meciccolombia.mediccolombia.web.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FiltroBusquedaResponseDTO {

    private Long id;
    private String descripcion;
    private LocalDateTime fechaCreacion;
}
