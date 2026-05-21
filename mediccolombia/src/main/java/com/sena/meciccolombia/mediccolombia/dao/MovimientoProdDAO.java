package com.sena.meciccolombia.mediccolombia.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.MovimientoProd;

@Repository
public interface MovimientoProdDAO extends JpaRepository<MovimientoProd, Long>{

    List<MovimientoProd> findByProductoId(Long productoId);
    List<MovimientoProd> findByUsuarioId(Long usuarioId);
    List<MovimientoProd> findByTipoMovimientoId(Long tipoMovimientoId);
    boolean existsByPickerChecker(String pickerChecker);

    
}
