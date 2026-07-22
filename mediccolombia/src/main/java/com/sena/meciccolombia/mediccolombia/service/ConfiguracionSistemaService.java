package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;
import java.util.Map;

import com.sena.meciccolombia.mediccolombia.web.dto.response.ConfiguracionSistemaResponseDTO;

public interface ConfiguracionSistemaService {
    List<ConfiguracionSistemaResponseDTO> listar();

    List<ConfiguracionSistemaResponseDTO> listarPorCategoria(String categoria);

    String obtenerValor(String clave);

    void actualizar(String clave, String valor);

    void actualizarLote(Map<String, String> valores);
}