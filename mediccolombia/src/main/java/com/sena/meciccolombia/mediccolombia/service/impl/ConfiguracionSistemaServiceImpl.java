package com.sena.meciccolombia.mediccolombia.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.meciccolombia.mediccolombia.dao.ConfiguracionSistemaDAO;
import com.sena.meciccolombia.mediccolombia.domain.ConfiguracionSistema;
import com.sena.meciccolombia.mediccolombia.service.ConfiguracionSistemaService;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ConfiguracionSistemaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConfiguracionSistemaServiceImpl implements ConfiguracionSistemaService {

    private final ConfiguracionSistemaDAO configuracionDAO;

    @Override
    @Transactional(readOnly = true)
    public List<ConfiguracionSistemaResponseDTO> listar() {
        return configuracionDAO.findAllByOrderByCategoriaAscClaveAsc().stream()
                .map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConfiguracionSistemaResponseDTO> listarPorCategoria(String categoria) {
        return configuracionDAO.findByCategoriaOrderByClaveAsc(categoria).stream()
                .map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public String obtenerValor(String clave) {
        return configuracionDAO.findByClave(clave)
                .map(ConfiguracionSistema::getValor)
                .orElse(null);
    }

    @Override
    @Transactional
    public void actualizar(String clave, String valor) {
        ConfiguracionSistema config = configuracionDAO.findByClave(clave)
                .orElseThrow(() -> new RuntimeException("Parámetro no encontrado: " + clave));
        config.setValor(valor.trim());
        configuracionDAO.save(config);
    }

    @Override
    @Transactional
    public void actualizarLote(Map<String, String> valores) {
        valores.forEach((clave, valor) -> {
            if (valor != null && !valor.isBlank()) {
                configuracionDAO.findByClave(clave).ifPresent(config -> {
                    config.setValor(valor.trim());
                    configuracionDAO.save(config);
                });
            }
        });
    }

    private ConfiguracionSistemaResponseDTO toDTO(ConfiguracionSistema c) {
        return ConfiguracionSistemaResponseDTO.builder()
                .id(c.getId())
                .clave(c.getClave())
                .valor(c.getValor())
                .descripcion(c.getDescripcion())
                .categoria(c.getCategoria())
                .build();
    }
}