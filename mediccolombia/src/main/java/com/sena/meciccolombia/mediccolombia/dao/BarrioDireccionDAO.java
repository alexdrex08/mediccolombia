package com.sena.meciccolombia.mediccolombia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.meciccolombia.mediccolombia.domain.BarrioDireccion;

@Repository
public interface BarrioDireccionDAO extends JpaRepository<BarrioDireccion, Long>{

}
