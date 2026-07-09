package com.sena.meciccolombia.mediccolombia.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.ReporteInv;

@Repository
public interface ReporteInvDAO extends JpaRepository<ReporteInv, Long>{

    List<ReporteInv>findByUsuarioId(Long usuarioId);
    List<ReporteInv> findByTipoReporte(String tipoReporte);
    long countByTipoReporte(String tipoReporte);
    List<ReporteInv> findTop5ByOrderByFechaGeneracionDesc();
    
}
