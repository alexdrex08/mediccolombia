package com.sena.meciccolombia.mediccolombia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.FiltroBusqueda;

@Repository
public interface FiltroBusquedaDAO extends JpaRepository<FiltroBusqueda, Long>{
    
}
