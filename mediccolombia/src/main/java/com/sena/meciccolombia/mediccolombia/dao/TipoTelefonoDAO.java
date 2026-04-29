package com.sena.meciccolombia.mediccolombia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.TipoTelefono;

@Repository
public interface TipoTelefonoDAO extends JpaRepository<TipoTelefono, Long>{
    
}
