package com.sena.meciccolombia.mediccolombia.service.impl;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.meciccolombia.mediccolombia.dao.ConfiguracionUsuarioDAO;
import com.sena.meciccolombia.mediccolombia.domain.ConfiguracionUsuario;
import com.sena.meciccolombia.mediccolombia.service.ConfiguracionUsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConfiguracionUsuarioServiceImpl implements ConfiguracionUsuarioService {

    private final ConfiguracionUsuarioDAO usuarioDAO;

    @Transactional(readOnly = true)
    public Map<String, String> obtenerPreferenciasUsuario(Long usuarioId) {
        Map<String, String> preferencias = usuarioDAO.findByUsuarioId(usuarioId)
                .stream()
                .collect(Collectors.toMap(
                        ConfiguracionUsuario::getClave,
                        ConfiguracionUsuario::getValor));

        // Rellenar defaults si el usuario no tiene esa clave guardada
        preferencias.putIfAbsent("tema", "oscuro");
        preferencias.putIfAbsent("idioma", "es");
        preferencias.putIfAbsent("filas_por_pagina", "10");
        preferencias.putIfAbsent("notificaciones", "true");

        return preferencias;
    }

    /**
     * Devuelve el valor de una preferencia específica del usuario.
     */
    @Transactional(readOnly = true)
    public String obtenerPreferenciaUsuario(Long usuarioId, String clave, String defaultVal) {
        return usuarioDAO.findByUsuarioIdAndClave(usuarioId, clave)
                .map(ConfiguracionUsuario::getValor)
                .orElse(defaultVal);
    }

    /**
     * Guarda o actualiza una preferencia del usuario (upsert).
     * Si ya existe la clave para ese usuario, la actualiza.
     * Si no existe, la crea.
     */
    @Transactional
    public void guardarPreferenciaUsuario(Long usuarioId, String clave, String valor) {
        ConfiguracionUsuario config = usuarioDAO
                .findByUsuarioIdAndClave(usuarioId, clave)
                .orElse(ConfiguracionUsuario.builder()
                        .usuarioId(usuarioId)
                        .clave(clave)
                        .build());
        config.setValor(valor.trim());
        usuarioDAO.save(config);
    }

    /**
     * Guarda múltiples preferencias del usuario de una sola vez.
     * Recibe un Map<clave, valor> y hace upsert de cada una.
     */
    @Transactional
    public void guardarTodasLasPreferencias(Long usuarioId, Map<String, String> preferencias) {
        preferencias.forEach((clave, valor) -> guardarPreferenciaUsuario(usuarioId, clave, valor));
    }

}
