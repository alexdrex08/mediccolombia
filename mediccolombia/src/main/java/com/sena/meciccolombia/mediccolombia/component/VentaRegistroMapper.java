package com.sena.meciccolombia.mediccolombia.component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sena.meciccolombia.mediccolombia.domain.Cliente;
import com.sena.meciccolombia.mediccolombia.domain.DetalleVenta;
import com.sena.meciccolombia.mediccolombia.domain.Producto;
import com.sena.meciccolombia.mediccolombia.domain.Usuario;
import com.sena.meciccolombia.mediccolombia.domain.VentaRegistro;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DetalleVentaRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.VentaRegistroRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DetalleVentaResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.VentaRegistroResponseDTO;

@Component
public class VentaRegistroMapper {
    
    public DetalleVenta toDetalleEntity( DetalleVentaRequestDTO dto, Producto producto, VentaRegistro ventaRegistro){
        if( dto == null) return null;
        return DetalleVenta.builder()
                            .cantidad(dto.getCantidad())
                            .precioUnitario(dto.getPrecioUnitario())
                            .producto(producto)
                            .venta(ventaRegistro)
                            .build();
    }
    public DetalleVentaResponseDTO toDetalleResponse(DetalleVenta detalleVenta){
        if (detalleVenta == null) return null;
        return DetalleVentaResponseDTO.builder()
                                        .id(detalleVenta.getId())
                                        .nombreProducto(detalleVenta.getProducto().getNombreProducto())
                                        .cantidad(detalleVenta.getCantidad())
                                        .precioUnitario(detalleVenta.getPrecioUnitario())
                                        .subtotal(detalleVenta.getPrecioUnitario()
                                                            .multiply(BigDecimal.valueOf(detalleVenta.getCantidad())))
                                        .build();
    }
    public VentaRegistro toEntity(VentaRegistroRequestDTO dto, Cliente cliente, Usuario usuario){
        if(dto == null) return null;
        return VentaRegistro.builder()
                                .fechaVenta(LocalDateTime.now())
                                .cliente(cliente)
                                .usuario(usuario)
                                .totalVenta(BigDecimal.ZERO)
                            .build();
    }
    public VentaRegistroResponseDTO toResponseDTO(VentaRegistro ventaRegistro, List<DetalleVentaResponseDTO> detalles){
        if(ventaRegistro == null) return null;
        return VentaRegistroResponseDTO.builder()
                                        .id(ventaRegistro.getId())
                                        .fechaVenta(ventaRegistro.getFechaVenta())
                                        .nombreCliente(ventaRegistro.getCliente().getNombreCliente())
                                        .nombreUsuario(ventaRegistro.getUsuario().getNombre())
                                        .totalVenta(ventaRegistro.getTotalVenta())
                                        .detalles(detalles)
                                        .build();
    }
}
