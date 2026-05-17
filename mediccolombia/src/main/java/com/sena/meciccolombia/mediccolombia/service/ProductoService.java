package com.sena.meciccolombia.mediccolombia.service;


import java.util.List;

import com.sena.meciccolombia.mediccolombia.web.dto.request.ProductoCreateRequestDto;
import com.sena.meciccolombia.mediccolombia.web.dto.request.ProductoUpdateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProductoDetalleDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProductoHistorialDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProductoResumenDTO;

public interface ProductoService {

    ProductoDetalleDTO crearProducto(ProductoCreateRequestDto dto);
    
    ProductoDetalleDTO actualizarProducto(Long id, ProductoUpdateRequestDTO dto);

    void eliminarProducto(Long id);

    ProductoDetalleDTO obtenerPorId(Long id);

    List<ProductoResumenDTO> listarProductos();

    List<ProductoResumenDTO> buscarPorNombre(String nombre);

    
    List<ProductoResumenDTO> filtrarPorCategoria(Long idCategoria);

    List<ProductoResumenDTO> productosConStockBajo();

    List<ProductoResumenDTO> productosProximosAVencer(int dias);

    ProductoHistorialDTO productoHistorial(Long idProducto);
}