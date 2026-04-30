package com.sena.meciccolombia.mediccolombia.component;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.sena.meciccolombia.mediccolombia.domain.Categoria;
import com.sena.meciccolombia.mediccolombia.domain.Producto;
import com.sena.meciccolombia.mediccolombia.domain.Usuario;
import com.sena.meciccolombia.mediccolombia.web.dto.ProductoCreateRequestDto;
import com.sena.meciccolombia.mediccolombia.web.dto.ProductoDetalleDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.ProductoResumenDTO;

import lombok.Builder;

@Component
@Builder 
public class ProductoMapper {

    public Producto toEntity(ProductoCreateRequestDto dto, Categoria categoria, Usuario usuario) {
        if (dto == null) {
            return null;
        }
        return Producto.builder()
                .nombreProducto(dto.getNombreProducto())
                .fechaExpiracion(dto.getFechaExpiracion())
                .stock(dto.getStock())
                .lote(dto.getLote())
                .stockMinimo(dto.getStockMinimo())
                .stockMaximo(dto.getStockMaximo())
                .fechaIngreso(LocalDateTime.now())
                .categoria(categoria)
                .usuario(usuario)
                .build();
    }

    public ProductoResumenDTO toResumenDTO(Producto producto){
        if (producto == null){
            return null;
        }
        return ProductoResumenDTO.builder()
                .id(producto.getId())
                .nombreProducto(producto.getNombreProducto())
                .categoria(producto.getCategoria().getNombre())
                .stock(producto.getStock())
                .estadoStock(calcularEstadoStock(producto))
                .build();                
    }

    public ProductoDetalleDTO toDetalleDTO(Producto producto){
        if (producto == null){
            return null;
        }
        return ProductoDetalleDTO.builder()
                .id(producto.getId())
                .usuarioIngresado(producto.getUsuario().getNombre())
                .nombreProducto(producto.getNombreProducto())
                .categoria(producto.getCategoria().getNombre())
                .stock(producto.getStock())
                .stockMinimo(producto.getStockMinimo())
                .stockMaximo(producto.getStockMaximo())
                .estadoStock(calcularEstadoStock(producto))
                .lote(producto.getLote())
                .fechaIngreso(producto.getFechaIngreso())
                .fechaModificacion(producto.getFechaModificacion())
                .fechaExpiracion(producto.getFechaExpiracion())
                .build();
    }

    private String calcularEstadoStock(Producto producto) {
        if (producto.getStock() <= producto.getStockMinimo()) {
            return "Crítico";
        } else if (producto.getStock() > producto.getStockMinimo() && producto.getStock() <= producto.getStockMaximo()) {
            return "Suficiente";
        } else {
            return "Exceso";
        }
    }
    
}
