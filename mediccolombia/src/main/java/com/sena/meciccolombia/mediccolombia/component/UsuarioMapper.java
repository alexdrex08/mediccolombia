package com.sena.meciccolombia.mediccolombia.component;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sena.meciccolombia.mediccolombia.domain.Usuario;
import com.sena.meciccolombia.mediccolombia.web.dto.request.UsuarioCreateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.EstadoUsuarioResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.MovimientoProdResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioDetalleMovimientosResponseDto;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioResponseDTO;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioCreateRequestDTO dto, String contrasenaCodificada ){
        if (dto == null) return null;
        return Usuario.builder()
                        .nombre(dto.getNombre())
                        .correo(dto.getCorreo())
                        .contrasena(contrasenaCodificada)
                        .identificacion(dto.getIdentificacion())
                        .rol(dto.getRol())
                        .build();
    }

    public UsuarioResponseDTO toResponseDTO(Usuario entity){
        if (entity == null ) return null;
        return UsuarioResponseDTO.builder()
                                .id(entity.getId())
                                .nombre(entity.getNombre())
                                .correo(entity.getCorreo())
                                .identificacion(entity.getIdentificacion())
                                .rol(entity.getRol())
                                .build();
    }
    public UsuarioDetalleResponseDTO toDetalleDTO(Usuario entity, List<EstadoUsuarioResponseDTO> estados){
        if(entity == null)return null ;
        return UsuarioDetalleResponseDTO.builder()
                                        .id(entity.getId())
                                        .nombre(entity.getNombre())
                                        .correo(entity.getCorreo())
                                        .rol(entity.getRol())
                                        .estadoUsuario(estados)
                                        .build();
    }
        public UsuarioDetalleMovimientosResponseDto toDetalleMovimientoDTO(Usuario entity, List<MovimientoProdResponseDTO> movimientos){
        if(entity == null)return null ;
        return UsuarioDetalleMovimientosResponseDto.builder()
                                        .id(entity.getId())
                                        .nombre(entity.getNombre())
                                        .correo(entity.getCorreo())
                                        .rol(entity.getRol())
                                        .movimientos(movimientos)
                                        .build();
    }
    
}
