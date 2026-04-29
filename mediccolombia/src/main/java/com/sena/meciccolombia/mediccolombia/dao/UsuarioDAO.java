package com.sena.meciccolombia.mediccolombia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);
}