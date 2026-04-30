package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;

import com.sena.meciccolombia.mediccolombia.web.dto.request.CategoriaCreateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.CategoriaDetalleDTO;

public interface CategoriaService {

    CategoriaDetalleDTO crearCategoria(CategoriaCreateRequestDTO dto);
    List<CategoriaDetalleDTO> listarCategorias();

    void eliminarCategoria(Long id);

    CategoriaDetalleDTO obtenerPorId(Long id);

    CategoriaDetalleDTO actualizarCategoria(Long id, CategoriaCreateRequestDTO dto);
    
} 
