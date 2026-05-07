package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;
import com.sena.meciccolombia.mediccolombia.dao.*;
import com.sena.meciccolombia.mediccolombia.domain.*;
import com.sena.meciccolombia.mediccolombia.web.dto.request.CorreoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.CorreoResponseDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CorreoMapper {

   public Correo toEntity(CorreoRequestDTO dto, TipoCorreo tipoCorreo, Cliente cliente, Proveedor proveedor){
    if (dto == null) return null;
    return Correo.builder()
            .correoElectronico(dto.getCorreoElectronico())
            .tipoCorreo(tipoCorreo)
            .cliente(cliente)
            .proveedor(proveedor)
            .build();
   }
    public CorreoResponseDTO toResponseDTO(Correo entity) {
        if (entity == null) return null;
        return CorreoResponseDTO.builder()
                .id(entity.getId())
                .correoElectronico(entity.getCorreoElectronico())
                .tipoCorreo(entity.getTipoCorreo() != null ? entity.getTipoCorreo().getNombreTipo() : null)
                .idCliente(entity.getCliente() != null ? entity.getCliente().getId() : null)
                .idProveedor(entity.getProveedor() != null ? entity.getProveedor().getId() : null)
                .build();
    }
}
