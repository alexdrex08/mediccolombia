package com.sena.meciccolombia.mediccolombia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.Correo;
import java.util.List;


@Repository
public interface CorreoDAO extends JpaRepository<Correo, Long> {

    List<Correo> findByProveedorId(Long proveedorId);
    List<Correo> findByClienteId(Long clienteId);
    
}