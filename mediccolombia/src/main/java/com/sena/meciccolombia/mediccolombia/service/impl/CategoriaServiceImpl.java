package com.sena.meciccolombia.mediccolombia.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sena.meciccolombia.mediccolombia.component.CategoriaMapper;
import com.sena.meciccolombia.mediccolombia.dao.CategoriaDAO;
import com.sena.meciccolombia.mediccolombia.domain.Categoria;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.CategoriaService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.CategoriaCreateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.CategoriaDetalleDTO;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaDAO categoriaDAO;
    private final CategoriaMapper categoriaMapper;

    @Override
    @Transactional
    public CategoriaDetalleDTO crearCategoria(CategoriaCreateRequestDTO dto) {
        if(dto == null){
            throw new IllegalArgumentException("El DTO de categoría no puede ser nulo");
        }
        Categoria categoria = categoriaMapper.toEntity(dto);
        Categoria guardada = categoriaDAO.save(categoria);
        return categoriaMapper.toDetalleDTO(guardada);
        
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDetalleDTO> listarCategorias() {
        return categoriaDAO.findAll().stream()
                .map(categoriaMapper::toDetalleDTO)
                .toList();
    }

    @Override
    @Transactional
    public void eliminarCategoria(Long id) {
        if(id == null){
            throw new IllegalArgumentException("El ID de categoría no puede ser nulo");
        }
        if(!categoriaDAO.existsById(id)){
            throw new ResourceNotFoundException("Categoría con ID " + id + " no encontrada");
        }
        categoriaDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDetalleDTO obtenerPorId(Long id) {
        if(id == null){
            throw new IllegalArgumentException("El Id no puede ser nulo");
        }
        return categoriaDAO.findById(id)
                .map(categoriaMapper::toDetalleDTO)
                .orElseThrow(() -> new RuntimeException("Categoría con ID " + id + " no encontrada"));
                
    }


    @Override
    @Transactional
    public CategoriaDetalleDTO actualizarCategoria(Long id, CategoriaCreateRequestDTO dto) {
        if(id == null){
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        if (dto == null){
            throw new IllegalArgumentException("El DTO de categoria no puede ser nulo");
        }
        Categoria categoria = categoriaDAO.findById(id)
                 .orElseThrow( () -> new RuntimeException("Categoria no encontrada"));

                 categoria.setNombre(dto.getNombreCategoria());
                 categoria.setDescripcion(dto.getDescripcion());

                 Categoria actualizada = categoriaDAO.save(categoria);
                 return categoriaMapper.toDetalleDTO(actualizada);
    }
    
}
