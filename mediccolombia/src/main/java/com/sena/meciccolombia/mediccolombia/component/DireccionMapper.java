package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;
import com.sena.meciccolombia.mediccolombia.dao.*;
import com.sena.meciccolombia.mediccolombia.domain.*;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DireccionRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DireccionResponseClienteDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DireccionResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DireccionResponseProveedorDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DireccionMapper {

    public Direccion toEntity(DireccionRequestDTO dto, BarrioDireccion barrioDireccion, Cliente cliente, Proveedor proveedor, TipoDireccion tipoDireccion ){
        if(dto == null) return null; 

        return Direccion.builder()
                .direccion(dto.getDireccion())
                .complemento(dto.getComplemento())
                .barrio(barrioDireccion)
                .cliente(cliente)
                .proveedor(proveedor)
                .tipoDireccion(tipoDireccion)
                .build();
    }

    public DireccionResponseDTO toResponseDTO(Direccion entity) {
        if (entity == null) return null;
        return DireccionResponseDTO.builder()
                .id(entity.getId())
                .direccion(entity.getDireccion())
                .complemento(entity.getComplemento())
                .barrio(entity.getBarrio() != null ? entity.getBarrio().getNombreBarrio() : null)
                .idCliente(entity.getCliente() != null ? entity.getCliente().getId() : null)
                .idProveedor(entity.getProveedor() != null ? entity.getProveedor().getId() : null)
                .tipoDireccion(entity.getTipoDireccion() != null ? entity.getTipoDireccion().getNombreTipo() : null)
                .build();
    }

    public DireccionResponseClienteDTO toResponseClienteDTO(Direccion entity) {
        if (entity == null) return null;
        return DireccionResponseClienteDTO.builder()
                .id(entity.getId())
                .direccion(entity.getDireccion())
                .complemento(entity.getComplemento())
                .barrio(entity.getBarrio() != null ? entity.getBarrio().getNombreBarrio() : null)
                .idCliente(entity.getCliente() != null ? entity.getCliente().getId() : null)
                .tipoDireccion(entity.getTipoDireccion() != null ? entity.getTipoDireccion().getNombreTipo() : null)
                .build();
    }

     public DireccionResponseProveedorDTO toResponseProveedorDTO(Direccion entity) {
        if (entity == null) return null;
        return DireccionResponseProveedorDTO.builder()
                .id(entity.getId())
                .direccion(entity.getDireccion())
                .complemento(entity.getComplemento())
                .barrio(entity.getBarrio() != null ? entity.getBarrio().getNombreBarrio() : null)
                .idProveedor(entity.getProveedor() != null ? entity.getProveedor().getId() : null)
                .tipoDireccion(entity.getTipoDireccion() != null ? entity.getTipoDireccion().getNombreTipo() : null)
                .build();
    }
}
