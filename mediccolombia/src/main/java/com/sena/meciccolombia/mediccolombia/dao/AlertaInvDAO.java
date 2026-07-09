package com.sena.meciccolombia.mediccolombia.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.AlertaInv;

@Repository
public interface AlertaInvDAO extends JpaRepository<AlertaInv, Long> {
    List<AlertaInv> findByProductoId(Long productoId);

    List<AlertaInv> findByTipoAlerta(String tipoAlerta);

    boolean existsByProductoIdAndTipoAlerta(Long productoId, String tipoAlerta);
    List<AlertaInv> findByTipoAlertaAndIsResuelta(String tipoAlerta,Boolean isResuelta);
    List<AlertaInv> findByIsResueltaFalse();
    List<AlertaInv> findByIsResueltaTrue();
}
