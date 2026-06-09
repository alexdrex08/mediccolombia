package com.sena.meciccolombia.mediccolombia.web.dto.response;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProyeccionesResponseDTO {
    private Long id;
    private String tipoProyeccion;
    private String metodoProyeccion;
    private String resultadoProyeccion;
    private String referenciaTipo;
    private Integer pedidosEstimados;
    private LocalDateTime fechaGeneracion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String unidadMedida;
    private String nombreProducto;
    private String nombreCategoria;
}
