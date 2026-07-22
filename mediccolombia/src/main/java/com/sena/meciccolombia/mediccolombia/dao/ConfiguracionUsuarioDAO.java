package com.sena.meciccolombia.mediccolombia.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.meciccolombia.mediccolombia.domain.ConfiguracionUsuario;

public interface ConfiguracionUsuarioDAO
        extends JpaRepository<ConfiguracionUsuario, Long> {

    List<ConfiguracionUsuario> findByUsuarioId(Long usuarioId);

    Optional<ConfiguracionUsuario> findByUsuarioIdAndClave(Long usuarioId, String clave);
}