package com.sena.meciccolombia.mediccolombia.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.Producto;

@Repository
public interface ProductoDAO extends JpaRepository<Producto, Long>{

    List<Producto> findByNombreProductoContainsIgnoreCase(String nombre);

    List<Producto> findBycategoriaId(Long idCategoria);
    

    List<Producto> findByStockLessThanEqualAndStockMinimoGreaterThan(Integer stock, Integer stockMinimo);

    List<Producto> findByFechaExpiracionBetween(LocalDateTime inicio, LocalDateTime fin);
    
}
