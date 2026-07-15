package com.sena.meciccolombia.mediccolombia.service;

import java.time.LocalDateTime;
import java.util.List;

import com.sena.meciccolombia.mediccolombia.web.dto.response.ProyeccionesResponseDTO;

public interface ProyeccionesService {
    List<ProyeccionesResponseDTO> listar();

    List<ProyeccionesResponseDTO> listarPorTipo(Long idTipoProyeccion);

    List<ProyeccionesResponseDTO> listarPorProducto(Long idProducto);

    List<ProyeccionesResponseDTO> listarPorCategoria(Long idCategoria);

    // Consultas manuales en tiempo real — no se guardan
    List<ProyeccionesResponseDTO> consultarProveedoresMasFiables();

    List<ProyeccionesResponseDTO> consultarClientesMasFileles();

    List<ProyeccionesResponseDTO> consultarPreciosMercado();

    void generarManual(Long idTipoProyeccion, LocalDateTime inicio, LocalDateTime fin);
}
