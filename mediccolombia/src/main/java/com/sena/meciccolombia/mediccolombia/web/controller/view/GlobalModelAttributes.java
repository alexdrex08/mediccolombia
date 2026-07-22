package com.sena.meciccolombia.mediccolombia.web.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sena.meciccolombia.mediccolombia.service.ConfiguracionSistemaService;


@ControllerAdvice(basePackages =
    "com.sena.meciccolombia.mediccolombia.web.controller.view")
public class GlobalModelAttributes {

    @Autowired
    private ConfiguracionSistemaService configuracionService;

    @ModelAttribute("nombreEmpresa")
    public String nombreEmpresa() {
        return leer("nombre_empresa", "Asuras Col");
    }

    @ModelAttribute("nombreSistema")
    public String nombreSistema() {
        return leer("nombre_sistema", "MedicColombia");
    }

    @ModelAttribute("sloganEmpresa")
    public String sloganEmpresa() {
        return leer("slogan_empresa", "Control y confianza en cada registro");
    }

    @ModelAttribute("versionSistema")
    public String versionSistema() {
        return leer("version_sistema", "1.0.0");
    }

    @ModelAttribute("itemsPorPagina")
    public int itemsPorPagina() {
        return leerInt("items_por_pagina", 5);
    }

    @ModelAttribute("filasDashboard")
    public int filasDashboard() {
        return leerInt("filas_dashboard", 5);
    }

    // ── helpers privados ──────────────────────────────────────────────

    private String leer(String clave, String fallback) {
        try {
            String valor = configuracionService.obtenerValor(clave);
            return (valor != null && !valor.isBlank()) ? valor.trim() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    private int leerInt(String clave, int fallback) {
        try {
            String valor = configuracionService.obtenerValor(clave);
            return (valor != null && !valor.isBlank()) ? Integer.parseInt(valor.trim()) : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }
}