package com.sena.meciccolombia.mediccolombia.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.meciccolombia.mediccolombia.domain.ConfiguracionSistema;

public interface ConfiguracionSistemaDAO
        extends JpaRepository<ConfiguracionSistema, Long> {

    Optional<ConfiguracionSistema> findByClave(String clave);

    List<ConfiguracionSistema> findByCategoria(String categoria);

    List<ConfiguracionSistema> findByCategoriaOrderByClaveAsc(String categoria);
    List<ConfiguracionSistema>findAllByOrderByCategoriaAscClaveAsc();
}