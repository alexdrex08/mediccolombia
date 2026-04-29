package com.sena.meciccolombia.mediccolombia.service;


import java.util.List;

import com.sena.meciccolombia.mediccolombia.web.dto.ProductoCreateRequestDto;
import com.sena.meciccolombia.mediccolombia.web.dto.ProductoDetalleDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.ProductoResumenDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.ProductoUpdateRequestDTO;

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
}