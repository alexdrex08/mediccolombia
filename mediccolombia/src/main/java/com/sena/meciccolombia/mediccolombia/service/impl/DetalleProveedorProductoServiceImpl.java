package com.sena.meciccolombia.mediccolombia.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.meciccolombia.mediccolombia.component.DetalleProveedorProductoMapper;
import com.sena.meciccolombia.mediccolombia.dao.DetalleProveedorProductoDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProductoDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProveedorDAO;
import com.sena.meciccolombia.mediccolombia.domain.DetalleId;
import com.sena.meciccolombia.mediccolombia.domain.DetalleProveedorProducto;
import com.sena.meciccolombia.mediccolombia.domain.Producto;
import com.sena.meciccolombia.mediccolombia.domain.Proveedor;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.IDetalleProveedorProductoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DetalleProveedorProductoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DetalleProveedorProductoResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetalleProveedorProductoServiceImpl implements IDetalleProveedorProductoService {

    private final DetalleProveedorProductoDAO detalleProveedorProductoDAO;
    private final ProveedorDAO proveedorDAO;
    private final ProductoDAO productoDAO;
    private final DetalleProveedorProductoMapper detalleProveedorProductoMapper;

    @Override
    @Transactional
    public DetalleProveedorProductoResponseDTO asignar(DetalleProveedorProductoRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");

        DetalleId detalleId = new DetalleId(dto.getIdProveedor(), dto.getIdProducto());

        if (detalleProveedorProductoDAO.existsById(detalleId)) {
            throw new ResourceNotFoundException("Ya existe una relación entre ese proveedor y producto");
        }

        Proveedor proveedor = proveedorDAO.findById(dto.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor con ID " + dto.getIdProveedor() + " no encontrado"));

        Producto producto = productoDAO.findById(dto.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto con ID " + dto.getIdProducto() + " no encontrado"));

        DetalleProveedorProducto detalle = detalleProveedorProductoMapper.toEntity(dto, proveedor, producto);
        return detalleProveedorProductoMapper.toResponseDTO(detalleProveedorProductoDAO.save(detalle));
    }

    @Override
    @Transactional
    public DetalleProveedorProductoResponseDTO actualizarPrecio(Long idProveedor, Long idProducto, BigDecimal nuevoPrecio) {
        if (idProveedor == null || idProducto == null) throw new IllegalArgumentException("Los IDs no pueden ser nulos");
        if (nuevoPrecio == null || nuevoPrecio.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
        }

        DetalleId detalleId = new DetalleId(idProveedor, idProducto);
        DetalleProveedorProducto detalle = detalleProveedorProductoDAO.findById(detalleId)
                .orElseThrow(() -> new RuntimeException("Relación proveedor-producto no encontrada"));

        detalle.setPrecioUnitario(nuevoPrecio);
        return detalleProveedorProductoMapper.toResponseDTO(detalleProveedorProductoDAO.save(detalle));
    }

    @Override
    @Transactional
    public void eliminar(Long idProveedor, Long idProducto) {
        if (idProveedor == null || idProducto == null) throw new IllegalArgumentException("Los IDs no pueden ser nulos");

        DetalleId detalleId = new DetalleId(idProveedor, idProducto);
        if (!detalleProveedorProductoDAO.existsById(detalleId)) {
            throw new ResourceNotFoundException("Relación proveedor-producto no encontrada");
        }
        detalleProveedorProductoDAO.deleteById(detalleId);
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleProveedorProductoResponseDTO obtenerPorId(Long idProveedor, Long idProducto) {
        if (idProveedor == null || idProducto == null) throw new IllegalArgumentException("Los IDs no pueden ser nulos");

        DetalleId detalleId = new DetalleId(idProveedor, idProducto);
        return detalleProveedorProductoDAO.findById(detalleId)
                .map(detalleProveedorProductoMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Relación proveedor-producto no encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleProveedorProductoResponseDTO> listarPorProveedor(Long idProveedor) {
        if (idProveedor == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return detalleProveedorProductoDAO.findByProveedorId(idProveedor)
                .stream()
                .map(detalleProveedorProductoMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleProveedorProductoResponseDTO> listarPorProducto(Long idProducto) {
        if (idProducto == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return detalleProveedorProductoDAO.findByProductoId(idProducto)
                .stream()
                .map(detalleProveedorProductoMapper::toResponseDTO)
                .toList();
    }
}