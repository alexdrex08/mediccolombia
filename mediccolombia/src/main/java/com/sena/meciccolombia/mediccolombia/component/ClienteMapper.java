package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;

import com.sena.meciccolombia.mediccolombia.domain.Cliente;
import com.sena.meciccolombia.mediccolombia.web.dto.request.ClienteResquestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ClienteResponseDTO;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteResquestDTO dto){
        if(dto == null ) return null;

        return Cliente.builder()
                        .nombreCliente(dto.getNombreCliente())
                        .identificacion(dto.getIdentificacion())
                        .build();
    }

    public ClienteResponseDTO toResponseDTO(Cliente cliente){
        if (cliente == null) return null;

        return ClienteResponseDTO.builder() 
                                .id(cliente.getId())
                                .nombreCliente(cliente.getNombreCliente())
                                .identificacion(cliente.getIdentificacion())
                                .build();
    }
    
}
