package com.sena.meciccolombia.mediccolombia.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.DetallePedido;

@Repository
public interface DetallePedidoDAO extends JpaRepository<DetallePedido, Long> {

    List<DetallePedido> findByPedidoId(Long pedidoId);

    // Al final, antes del cierre de la interfaz:
    List<DetallePedido> findByProductoId(Long productoId);
}
