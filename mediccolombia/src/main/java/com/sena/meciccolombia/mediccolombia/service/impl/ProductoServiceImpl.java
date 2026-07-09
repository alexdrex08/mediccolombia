package com.sena.meciccolombia.mediccolombia.service.impl;

import com.sena.meciccolombia.mediccolombia.dao.CategoriaDAO;
import com.sena.meciccolombia.mediccolombia.dao.MovimientoProdDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProductoDAO;
import com.sena.meciccolombia.mediccolombia.dao.UsuarioDAO;
import com.sena.meciccolombia.mediccolombia.domain.Categoria;
import com.sena.meciccolombia.mediccolombia.domain.Producto;
import com.sena.meciccolombia.mediccolombia.domain.Usuario;
import com.sena.meciccolombia.mediccolombia.web.dto.request.ProductoCreateRequestDto;
import com.sena.meciccolombia.mediccolombia.web.dto.request.ProductoUpdateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.MovimientoProdResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProductoDetalleDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProductoHistorialDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProductoResumenDTO;

import jakarta.persistence.EntityNotFoundException;

import com.sena.meciccolombia.mediccolombia.component.MovimientoProdMapper;
import com.sena.meciccolombia.mediccolombia.component.ProductoMapper;
import com.sena.meciccolombia.mediccolombia.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

        private final ProductoDAO productoDAO;
        private final UsuarioDAO usuarioDAO;
        private final MovimientoProdDAO movimientoProdDAO;
        private final CategoriaDAO categoriaDAO;
        private final ProductoMapper productoMapper;
        private final MovimientoProdMapper movimientoProdMapper;

        @Override
        @Transactional
        public ProductoDetalleDTO crearProducto(ProductoCreateRequestDto dto) {
                Categoria categoria = categoriaDAO.findById(dto.getIdCategoria())
                                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

                Usuario usuario = usuarioDAO.findById(dto.getIdUsuario())
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                Producto producto = productoMapper.toEntity(dto, categoria, usuario);
                producto.setActivo(true);
                Producto guardado = productoDAO.save(producto);
                return productoMapper.toDetalleDTO(guardado);
        }

        @Override
        @Transactional
        public ProductoDetalleDTO actualizarProducto(Long id, ProductoUpdateRequestDTO dto) {
                Producto producto = productoDAO.findById(id)
                                .orElseThrow(() -> new RuntimeException("Producto no encontrado o no existe"));

                Categoria categoria = categoriaDAO.findById(dto.getIdCategoria())
                                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

                producto.setNombreProducto(dto.getNombreProducto());
                producto.setFechaExpiracion(dto.getFechaExpiracion());
                producto.setLote(dto.getLote());
                
                if(dto.getStock() != null) {
                        producto.setStock(dto.getStock());
                }

                producto.setStockMinimo(dto.getStockMinimo());
                producto.setStockMaximo(dto.getStockMaximo());
                producto.setCategoria(categoria);

                Producto actualizado = productoDAO.save(producto);
                return productoMapper.toDetalleDTO(actualizado);
        }

        @Transactional
        public void eliminarProducto(Long idProducto) {
                // 1. Buscamos el producto en la base de datos
                Producto producto = productoDAO.findById(idProducto)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "El producto con ID " + idProducto + " no existe."));
                producto.setActivo(false);
                productoDAO.save(producto);
        }

        @Override
        @Transactional(readOnly = true)
        public ProductoDetalleDTO obtenerPorId(Long id) {
                Producto producto = productoDAO.findById(id)
                                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                return productoMapper.toDetalleDTO(producto);
        }

        @Override
        @Transactional(readOnly = true)
        public ProductoHistorialDTO productoHistorial(Long idProducto) {
                Producto producto = productoDAO.findById(idProducto)
                                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                List<MovimientoProdResponseDTO> movimientos = movimientoProdDAO.findByProductoId(idProducto)
                                .stream()
                                .map(movimientoProdMapper::toResponseDTO)
                                .toList();
                return productoMapper.toDetalleHistoriaDTO(producto, movimientos);

        }

        @Override
        @Transactional(readOnly = true)
        public List<ProductoResumenDTO> listarProductos() {
                return productoDAO.findByActivoTrue()
                                .stream()
                                .map(productoMapper::toResumenDTO)
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public List<ProductoResumenDTO> buscarPorNombre(String nombre) {
                return productoDAO.findByNombreProductoContainingIgnoreCaseAndActivoTrue(nombre)
                                .stream()
                                .map(productoMapper::toResumenDTO)
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public List<ProductoResumenDTO> filtrarPorCategoria(Long idCategoria) {
                return productoDAO.findBycategoriaId(idCategoria)
                                .stream()
                                .map(productoMapper::toResumenDTO)
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public List<ProductoResumenDTO> productosConStockBajo() {
                return productoDAO.findByStockLessThanEqualAndStockMinimoGreaterThan(0, 0)
                                .stream()
                                .map(productoMapper::toResumenDTO)
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public List<ProductoResumenDTO> productosProximosAVencer(int dias) {
                LocalDateTime inicio = LocalDateTime.now();
                LocalDateTime fin = inicio.plusDays(dias);
                return productoDAO.findByFechaExpiracionBetween(inicio, fin)
                                .stream()
                                .map(productoMapper::toResumenDTO)
                                .toList();
        }
}