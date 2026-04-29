package com.sena.meciccolombia.mediccolombia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.DetalleVenta;

@Repository
public interface DetalleVentaDAO extends JpaRepository<DetalleVenta, Long>{
    
}
