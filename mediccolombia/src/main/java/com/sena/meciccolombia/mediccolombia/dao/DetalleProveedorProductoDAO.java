package com.sena.meciccolombia.mediccolombia.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.DetalleId;
import com.sena.meciccolombia.mediccolombia.domain.DetalleProveedorProducto;

@Repository
public interface DetalleProveedorProductoDAO extends JpaRepository<DetalleProveedorProducto, DetalleId>{
    
    List<DetalleProveedorProducto> findByProveedorId(Long proveedorId);
    List<DetalleProveedorProducto> findByProductoId(Long productoId);
}
