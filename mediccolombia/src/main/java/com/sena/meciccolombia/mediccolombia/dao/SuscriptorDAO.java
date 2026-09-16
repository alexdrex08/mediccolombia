package com.sena.meciccolombia.mediccolombia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.Suscriptor;

import java.util.Optional;

@Repository
public interface SuscriptorDAO extends JpaRepository<Suscriptor, Long> {
    Optional<Suscriptor> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}