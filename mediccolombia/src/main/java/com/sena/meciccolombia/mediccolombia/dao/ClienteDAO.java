package com.sena.meciccolombia.mediccolombia.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.Cliente;

@Repository
public interface ClienteDAO extends JpaRepository<Cliente, Long>{

    Optional<Cliente> findByIdentificacion(String identificacion);

    
}
