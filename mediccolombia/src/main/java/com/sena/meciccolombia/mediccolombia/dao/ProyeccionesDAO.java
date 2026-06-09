package com.sena.meciccolombia.mediccolombia.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.Proyecciones;

@Repository
public interface ProyeccionesDAO extends JpaRepository<Proyecciones, Long>{
    List<Proyecciones> findByTipoProyeccionId(Long tipoProyeccionId);
    
    List<Proyecciones> findByProductoId(Long productoId);
    List<Proyecciones> findByCategoriaId(Long categoriaId);
}
