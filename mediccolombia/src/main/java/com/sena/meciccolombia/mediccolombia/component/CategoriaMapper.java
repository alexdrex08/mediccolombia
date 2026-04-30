package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;

import com.sena.meciccolombia.mediccolombia.domain.Categoria;
import com.sena.meciccolombia.mediccolombia.web.dto.request.CategoriaCreateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.CategoriaDetalleDTO;

import lombok.Builder;

@Component
@Builder
public class CategoriaMapper { 

    public Categoria toEntity(CategoriaCreateRequestDTO dto){
        
        if(dto == null){
            return null;
        }
        return Categoria.builder()
                .nombre(dto.getNombreCategoria())
                .descripcion(dto.getDescripcion())
                .build();
    }

    public CategoriaDetalleDTO toDetalleDTO(Categoria categoria){
        if(categoria == null){
            return null;
        }
        return CategoriaDetalleDTO.builder()
            .idCategoria(categoria.getId())
            .nombreCategoria(categoria.getNombre())
            .descripcion(categoria.getDescripcion())
            .build();
    }
    


}
