package com.sena.meciccolombia.mediccolombia.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.PedidoCompra;

@Repository
public interface PedidoCompraDAO extends JpaRepository<PedidoCompra, Long>{
    
    List<PedidoCompra> findByProveedorId(Long proveedorId);
    List<PedidoCompra> findByEstadoPedidoId(Long estadoPedidoId);
    List<PedidoCompra> findByFechaPedidoBetween(LocalDateTime inicio, LocalDateTime fin);
}
