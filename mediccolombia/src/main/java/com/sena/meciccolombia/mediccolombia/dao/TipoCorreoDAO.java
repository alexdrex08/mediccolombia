package com.sena.meciccolombia.mediccolombia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.TipoCorreo;

@Repository
public interface TipoCorreoDAO extends JpaRepository<TipoCorreo, Long> {
    
}
