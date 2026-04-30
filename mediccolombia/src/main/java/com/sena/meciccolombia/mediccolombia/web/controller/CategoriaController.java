package com.sena.meciccolombia.mediccolombia.web.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.meciccolombia.mediccolombia.service.CategoriaService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.CategoriaCreateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.CategoriaDetalleDTO;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaDetalleDTO> crearCategoria(@RequestBody CategoriaCreateRequestDTO dto) {
        
        CategoriaDetalleDTO creado = categoriaService.crearCategoria(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDetalleDTO> atualizarCategoria(
            @PathVariable Long id, 
            @RequestBody CategoriaCreateRequestDTO dto) {

            CategoriaDetalleDTO actualizado = categoriaService.actualizarCategoria(id, dto);
            return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id){
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDetalleDTO>> listarCategoriaS() {
        List<CategoriaDetalleDTO> categoria = categoriaService.listarCategorias();
        return ResponseEntity.ok(categoria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDetalleDTO> obtenerPorId(@PathVariable Long id) {
        CategoriaDetalleDTO categoria = categoriaService.obtenerPorId(id);
        return ResponseEntity.ok(categoria);
    }
    
    
    
    
}
