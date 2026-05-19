package com.sena.meciccolombia.mediccolombia.service;

import java.math.BigDecimal;
import java.util.List;

import com.sena.meciccolombia.mediccolombia.web.dto.request.DetalleProveedorProductoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DetalleProveedorProductoResponseDTO;

public interface IDetalleProveedorProductoService {

    DetalleProveedorProductoResponseDTO asignar (DetalleProveedorProductoRequestDTO dto);
    DetalleProveedorProductoResponseDTO actualizarPrecio(Long idProveedor, Long idProducto, BigDecimal nuevoPrecio );
    void eliminar ( Long idProveedor, Long idProducto);
    DetalleProveedorProductoResponseDTO obtenerPorId(Long idProveedor, Long idProducto);
    List<DetalleProveedorProductoResponseDTO> listarPorProducto(Long idProducto);
    List<DetalleProveedorProductoResponseDTO> listarPorProveedor(Long idProveedor);



    
}
