package com.sena.meciccolombia.mediccolombia.service;

import java.util.Map;

public interface ConfiguracionUsuarioService {
    void guardarPreferenciaUsuario(Long usuarioId, String clave, String valor);
    Map<String, String> obtenerPreferenciasUsuario(Long usuarioId);
    String obtenerPreferenciaUsuario(Long usuarioId, String clave, String defaultVal);
     void guardarTodasLasPreferencias(Long usuarioId, Map<String, String> preferencias);
    
}
