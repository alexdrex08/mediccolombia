package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;

import com.sena.meciccolombia.mediccolombia.web.dto.request.ReporteInvRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ReporteDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ReporteInvResponseDTO;

public interface ReporteInvService {
    ReporteDetalleResponseDTO generarReporte(ReporteInvRequestDTO dto);
    ReporteInvResponseDTO obtenerPorId(Long id);
    List<ReporteInvResponseDTO> listar();
    List<ReporteInvResponseDTO> listarPorUsuario(Long idUsuario);
    List<ReporteInvResponseDTO> listarPorTipo(String tipoReporte);
}
