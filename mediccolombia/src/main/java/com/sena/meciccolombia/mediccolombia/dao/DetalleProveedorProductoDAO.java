package com.sena.meciccolombia.mediccolombia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.DetalleId;
import com.sena.meciccolombia.mediccolombia.domain.DetalleProveedorProducto;

@Repository
public interface DetalleProveedorProductoDAO extends JpaRepository<DetalleProveedorProducto, DetalleId>{
    
}
