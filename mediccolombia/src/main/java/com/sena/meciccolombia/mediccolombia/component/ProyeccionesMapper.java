package com.sena.meciccolombia.mediccolombia.component;

import com.sena.meciccolombia.mediccolombia.domain.Proyecciones;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProyeccionesResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ProyeccionesMapper {

    public ProyeccionesResponseDTO toResponseDTO(Proyecciones proyeccion) {
        if (proyeccion == null) return null;
        return ProyeccionesResponseDTO.builder()
                .id(proyeccion.getId())
                .tipoProyeccion(proyeccion.getTipoProyeccion() != null
                        ? proyeccion.getTipoProyeccion().getNombreProyeccion() : null)
                .metodoProyeccion(proyeccion.getMetodoProyeccion() != null
                        ? proyeccion.getMetodoProyeccion().getNombreMetodo() : null)
                .resultadoProyeccion(proyeccion.getResultadoProyeccion())
                .referenciaTipo(proyeccion.getReferenciaTipo())
                .pedidosEstimados(proyeccion.getPedidosEstimados())
                .fechaGeneracion(proyeccion.getFechaGeneracion())
                .fechaInicio(proyeccion.getFechaInicio())
                .fechaFin(proyeccion.getFechaFin())
                .unidadMedida(proyeccion.getUnidadMedida())
                .nombreProducto(proyeccion.getProducto() != null
                        ? proyeccion.getProducto().getNombreProducto() : null)
                .nombreCategoria(proyeccion.getCategoria() != null
                        ? proyeccion.getCategoria().getNombre() : null)
                .build();
    }
}