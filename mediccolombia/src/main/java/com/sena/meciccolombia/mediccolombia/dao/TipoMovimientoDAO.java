package com.sena.meciccolombia.mediccolombia.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.TipoMovimiento;

@Repository
public interface TipoMovimientoDAO extends JpaRepository<TipoMovimiento, Long>{

    Optional<TipoMovimiento> findByNombreMovimiento(String nombreMovimiento);
}
