package com.sena.meciccolombia.mediccolombia.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.DetalleFiltro;

@Repository
public interface DetalleFiltroDAO extends JpaRepository<DetalleFiltro, Long>{

    List<DetalleFiltro> findByFiltroBusquedaId(Long idFiltroBusqueda);
    
}
