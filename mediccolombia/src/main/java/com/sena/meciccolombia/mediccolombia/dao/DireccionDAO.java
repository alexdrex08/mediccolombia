package com.sena.meciccolombia.mediccolombia.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.Direccion;

@Repository
public interface DireccionDAO extends JpaRepository<Direccion, Long>{

    List<Direccion> findByProveedorId(Long proveedorId);
    List<Direccion> findByClienteId(Long clienteId);
}
