package com.sena.meciccolombia.mediccolombia.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.Proveedor;

@Repository
public interface ProveedorDAO extends JpaRepository<Proveedor, Long>{

    Optional<Proveedor> findByNit(String nit);
}
