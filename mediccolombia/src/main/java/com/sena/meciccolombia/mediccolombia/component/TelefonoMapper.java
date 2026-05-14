package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;
import com.sena.meciccolombia.mediccolombia.domain.*;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TelefonoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TelefonoResponseClienteDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TelefonoResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TelefonoResponseProveedorDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TelefonoMapper {

   public Telefono toEntity(TelefonoRequestDTO dto, TipoTelefono tipoTelefono, Cliente cliente, Proveedor proveedor){
    if(dto == null) return null;
    return Telefono.builder()
            .numero(dto.getNumero())
            .complemento(dto.getComplemento())
            .tipoTelefono(tipoTelefono)
            .cliente(cliente)
            .proveedor(proveedor)
            .build();
   }
    public TelefonoResponseDTO toResponseDTO(Telefono entity) {
        if (entity == null) return null;
        return TelefonoResponseDTO.builder()
                .id(entity.getId())
                .numero(entity.getNumero())
                .complemento(entity.getComplemento())
                .tipoTelefono(entity.getTipoTelefono() != null ? entity.getTipoTelefono().getNombreTipo() : null)
                .idCliente(entity.getCliente()!= null ? entity.getCliente().getId() : null)
                .idProveedor(entity.getProveedor() != null ? entity.getProveedor().getId() : null)
                .build();
    }

     public TelefonoResponseClienteDTO toResponseClienteDTO(Telefono entity) {
        if (entity == null) return null;
        return TelefonoResponseClienteDTO.builder()
                .id(entity.getId())
                .numero(entity.getNumero())
                .complemento(entity.getComplemento())
                .tipoTelefono(entity.getTipoTelefono() != null ? entity.getTipoTelefono().getNombreTipo() : null)
                .idCliente(entity.getCliente()!= null ? entity.getCliente().getId() : null)
                .build();
    }
         public TelefonoResponseProveedorDTO toResponseProveedorDTO(Telefono entity) {
        if (entity == null) return null;
        return TelefonoResponseProveedorDTO.builder()
                .id(entity.getId())
                .numero(entity.getNumero())
                .complemento(entity.getComplemento())
                .tipoTelefono(entity.getTipoTelefono() != null ? entity.getTipoTelefono().getNombreTipo() : null)
                .idProveedor(entity.getProveedor() != null ? entity.getProveedor().getId() : null)
                .build();
    }
}
