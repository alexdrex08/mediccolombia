package com.sena.meciccolombia.mediccolombia.component;

import org.springframework.stereotype.Component;

import com.sena.meciccolombia.mediccolombia.domain.DetalleId;
import com.sena.meciccolombia.mediccolombia.domain.DetalleProveedorProducto;
import com.sena.meciccolombia.mediccolombia.domain.Producto;
import com.sena.meciccolombia.mediccolombia.domain.Proveedor;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DetalleProveedorProductoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DetalleProveedorProductoResponseDTO;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Component
public class DetalleProveedorProductoMapper {
    
    public DetalleProveedorProducto toEntity(DetalleProveedorProductoRequestDTO dto,
                    Proveedor proveedor, Producto producto
    ){
        if (dto == null ) return null;
        return DetalleProveedorProducto.builder()
                                    .id(new DetalleId(dto.getIdProveedor(), dto.getIdProducto()))
                                    .precioUnitario(dto.getPrecioUnitario())
                                    .proveedor(proveedor)
                                    .producto(producto)
                                    .build();
    }
    public DetalleProveedorProductoResponseDTO toResponseDTO(DetalleProveedorProducto detalle) {
        if(detalle == null) return null;
        return DetalleProveedorProductoResponseDTO.builder()
                                    .idProveedor(detalle.getProveedor().getId())
                                    .nombreProveedor(detalle.getProveedor().getNombreProv())
                                    .idProducto(detalle.getProducto().getId())
                                    .nombreProducto(detalle.getProducto().getNombreProducto())
                                    .precioUnitario(detalle.getPrecioUnitario())
                                    .build();
    }
}
