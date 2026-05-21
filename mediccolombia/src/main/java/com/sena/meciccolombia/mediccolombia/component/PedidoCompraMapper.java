package com.sena.meciccolombia.mediccolombia.component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sena.meciccolombia.mediccolombia.domain.DetallePedido;
import com.sena.meciccolombia.mediccolombia.domain.EstadoPedido;
import com.sena.meciccolombia.mediccolombia.domain.PedidoCompra;
import com.sena.meciccolombia.mediccolombia.domain.Producto;
import com.sena.meciccolombia.mediccolombia.domain.Proveedor;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DetallePedidoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.PedidoCompraRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DetallePedidoResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.PedidoCompraResponseDTO;

@Component
public class PedidoCompraMapper {
    public PedidoCompra toEntity(PedidoCompraRequestDTO dto, Proveedor proveedor, EstadoPedido estadoPedido) {
        if(dto == null) return null;
        return PedidoCompra.builder()
            .fechaPedido(LocalDateTime.now())
            .observacion(dto.getObservacion())
            .proveedor(proveedor)
            .estadoPedido(estadoPedido)
            .build();
    }
    public DetallePedido toDetalleEntity(DetallePedidoRequestDTO dto, Producto producto, PedidoCompra pedido){
        if(dto == null) return null;
        return DetallePedido.builder()
                        .cantidad(dto.getCantidad())
                        .precioUnitario(dto.getPrecioUnitario())
                        .subtotal(dto.getPrecioUnitario()
                                        .multiply(BigDecimal.valueOf(dto.getCantidad())))
                        .producto(producto)
                        .pedido(pedido)
                        .build();
    }
    public DetallePedidoResponseDTO toDetalleResponseDTO(DetallePedido detalle){
        if (detalle  == null) return null;
        return DetallePedidoResponseDTO.builder()
                            .id(detalle.getId())
                            .nombreProducto(detalle.getProducto().getNombreProducto())
                            .cantidad(detalle.getCantidad())
                            .precioUnitario(detalle.getPrecioUnitario())
                            .subtotal(detalle.getSubtotal())
                            .build();
    }
    public PedidoCompraResponseDTO toResponseDTO (PedidoCompra pedido, List<DetallePedidoResponseDTO> detalles) {
        if(pedido == null) return null;
        return PedidoCompraResponseDTO.builder()
                        .id(pedido.getId())
                        .fechaPedido(pedido.getFechaPedido())
                        .nombreProveedor(pedido.getProveedor().getNombreProv())
                        .estadoPedido(pedido.getEstadoPedido().getNombreEstado())
                        .observacion(pedido.getObservacion())
                        .totalPedido(pedido.getTotalPedido())
                        .detalles(detalles)
                        .build();
    }
}
