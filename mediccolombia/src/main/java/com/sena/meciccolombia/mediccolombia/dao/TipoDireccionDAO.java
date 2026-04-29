package com.sena.meciccolombia.mediccolombia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.TipoDireccion;

@Repository
public interface TipoDireccionDAO extends JpaRepository<TipoDireccion, Long>{

}
