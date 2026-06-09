package com.sena.meciccolombia.mediccolombia.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.VentaRegistro;

@Repository
public interface VentaRegistroDAO extends JpaRepository<VentaRegistro, Long>{

    List<VentaRegistro> findByClienteId(Long idCliente);

    List<VentaRegistro> findByUsuarioId(Long idUsuario);

    List<VentaRegistro> findByFechaVentaBetween(LocalDateTime inicio, LocalDateTime fin);
}
