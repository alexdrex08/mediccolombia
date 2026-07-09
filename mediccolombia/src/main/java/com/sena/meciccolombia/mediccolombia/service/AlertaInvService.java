package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;

import com.sena.meciccolombia.mediccolombia.web.dto.response.AlertaInvResponseDTO;

public interface AlertaInvService {
    AlertaInvResponseDTO obtenerPorId(Long id);
    List<AlertaInvResponseDTO> listarIsResueltaFalse();
    List<AlertaInvResponseDTO> listarIsResueltaTrue();
    List<AlertaInvResponseDTO> listarPorProducto(Long idProducto);
    List<AlertaInvResponseDTO> listarPorTipoYEstado(String tipoAlerta, Boolean isResuelta);
    void resolverAlerta(Long id);

}
