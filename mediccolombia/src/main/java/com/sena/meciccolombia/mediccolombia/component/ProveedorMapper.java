package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;

import com.sena.meciccolombia.mediccolombia.domain.Proveedor;
import com.sena.meciccolombia.mediccolombia.web.dto.request.ProveedorRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProveedorResponseDTO;

@Component
public class ProveedorMapper {

    public Proveedor toEntity(ProveedorRequestDTO dto){
        if(dto == null) return null;

        return Proveedor.builder()
                        .nombreProv(dto.getNombreProv())
                        .nit(dto.getNit())
                        .build();
    }

    public ProveedorResponseDTO toResponseDTO(Proveedor proveedor){
        if(proveedor == null) return null;

        return ProveedorResponseDTO.builder()
                                    .id(proveedor.getId())
                                    .nombreProv(proveedor.getNombreProv())
                                    .nit(proveedor.getNit())
                                    .build();
    }
    
}
