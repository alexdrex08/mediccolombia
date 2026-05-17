package com.sena.meciccolombia.mediccolombia.component;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.sena.meciccolombia.mediccolombia.domain.MovimientoProd;
import com.sena.meciccolombia.mediccolombia.domain.Producto;
import com.sena.meciccolombia.mediccolombia.domain.TipoMovimiento;
import com.sena.meciccolombia.mediccolombia.domain.Usuario;
import com.sena.meciccolombia.mediccolombia.web.dto.request.MovimientoProdRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.MovimientoProdResponseDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MovimientoProdMapper {

    public MovimientoProd toEntity(MovimientoProdRequestDTO dto, Producto producto,
                                    TipoMovimiento tipoMovimiento, Usuario usuario
    ){
        if (dto == null) return null;
        return MovimientoProd.builder()
                            .fechaMovimiento(LocalDateTime.now())
                            .cantidad(dto.getCantidad())
                            .movimiento(dto.getMovimiento())
                            .pickerChecker(dto.getPickerChecker())
                            .tipoMovimiento(tipoMovimiento)
                            .producto(producto)
                            .usuario(usuario)
                            .build();
    }
    public MovimientoProdResponseDTO toResponseDTO(MovimientoProd movimientoProd){
        if(movimientoProd == null) return null;
        return MovimientoProdResponseDTO.builder()
                                        .id(movimientoProd.getId())
                                        .cantidad(movimientoProd.getCantidad())
                                        .movimiento(movimientoProd.getMovimiento())
                                        .pickerChecker(movimientoProd.getPickerChecker())
                                        .tipoMovimiento(movimientoProd.getTipoMovimiento().getNombreMovimiento())
                                        .nombreProducto(movimientoProd.getProducto().getNombreProducto())
                                        .stockResultante(movimientoProd.getProducto().getStock())
                                        .nombreUsuario(movimientoProd.getUsuario().getNombre())
                                        .fechaMovimiento(movimientoProd.getFechaMovimiento())
                                        .build();
    }
    
}
