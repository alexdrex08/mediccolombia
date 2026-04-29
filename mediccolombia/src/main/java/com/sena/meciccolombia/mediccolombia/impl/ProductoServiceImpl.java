package com.sena.meciccolombia.mediccolombia.impl;

import com.sena.meciccolombia.mediccolombia.dao.CategoriaDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProductoDAO;
import com.sena.meciccolombia.mediccolombia.domain.Categoria;
import com.sena.meciccolombia.mediccolombia.domain.Producto;
import com.sena.meciccolombia.mediccolombia.web.dto.ProductoCreateRequestDto;
import com.sena.meciccolombia.mediccolombia.web.dto.ProductoUpdateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.ProductoDetalleDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.ProductoResumenDTO;
import com.sena.meciccolombia.mediccolombia.component.ProductoMapper;
import com.sena.meciccolombia.mediccolombia.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoDAO productoDAO;
    private final CategoriaDAO categoriaDAO;
    private final ProductoMapper productoMapper;

    @Override
    public ProductoDetalleDTO crearProducto(ProductoCreateRequestDto dto) {
        Categoria categoria = categoriaDAO.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Producto producto = productoMapper.toEntity(dto, categoria);
        Producto guardado = productoDAO.save(producto);
        return productoMapper.toDetalleDTO(guardado);
    }

    @Override
    public ProductoDetalleDTO actualizarProducto(Long id, ProductoUpdateRequestDTO dto) {
        Producto producto = productoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Categoria categoria = categoriaDAO.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        producto.setNombreProducto(dto.getNombreProducto());
        producto.setFechaExpiracion(dto.getFechaExpiracion());
        producto.setLote(dto.getLote());
        producto.setStockMinimo(dto.getStockMinimo());
        producto.setStockMaximo(dto.getStockMaximo());
        producto.setCategoria(categoria);

        Producto actualizado = productoDAO.save(producto);
        return productoMapper.toDetalleDTO(actualizado);
    }

    @Override
    public void eliminarProducto(Long id) {
        Producto producto = productoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoDAO.delete(producto);
    }

    @Override
    public ProductoDetalleDTO obtenerPorId(Long id) {
        Producto producto = productoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return productoMapper.toDetalleDTO(producto);
    }

    @Override
    public List<ProductoResumenDTO> listarProductos() {
        return productoDAO.findAll()
                .stream()
                .map(productoMapper::toResumenDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoResumenDTO> buscarPorNombre(String nombre) {
        return productoDAO.findByNombreProductoContainsIgnoreCase(nombre)
                .stream()
                .map(productoMapper::toResumenDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoResumenDTO> filtrarPorCategoria(Long idCategoria) {
        return productoDAO.findBycategoriaId(idCategoria)
                .stream()
                .map(productoMapper::toResumenDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoResumenDTO> productosConStockBajo() {
        return productoDAO.findByStockLessThanEqualAndStockMinimoGreaterThan(0, 0)
                .stream()
                .map(productoMapper::toResumenDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoResumenDTO> productosProximosAVencer(int dias) {
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fin = inicio.plusDays(dias);
        return productoDAO.findByFechaExpiracionBetween(inicio, fin)
                .stream()
                .map(productoMapper::toResumenDTO)
                .collect(Collectors.toList());
    }
}