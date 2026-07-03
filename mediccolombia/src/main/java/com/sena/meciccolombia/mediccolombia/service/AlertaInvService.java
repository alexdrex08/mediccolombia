package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;

import com.sena.meciccolombia.mediccolombia.web.dto.response.AlertaInvResponseDTO;

public interface AlertaInvService {
    AlertaInvResponseDTO obtenerPorId(Long id);
    List<AlertaInvResponseDTO> listar();
    List<AlertaInvResponseDTO> listarPorProducto(Long idProducto);
    List<AlertaInvResponseDTO> listarPorTipo(String tipoAlerta);
    void eliminar(Long id);

}
