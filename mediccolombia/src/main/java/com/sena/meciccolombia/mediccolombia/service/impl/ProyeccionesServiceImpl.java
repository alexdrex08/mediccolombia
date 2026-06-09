package com.sena.meciccolombia.mediccolombia.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.meciccolombia.mediccolombia.component.ProyeccionesMapper;
import com.sena.meciccolombia.mediccolombia.dao.DetalleProveedorProductoDAO;
import com.sena.meciccolombia.mediccolombia.dao.DetalleVentaDAO;
import com.sena.meciccolombia.mediccolombia.dao.PedidoCompraDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProyeccionesDAO;
import com.sena.meciccolombia.mediccolombia.dao.VentaRegistroDAO;
import com.sena.meciccolombia.mediccolombia.domain.VentaRegistro;
import com.sena.meciccolombia.mediccolombia.service.ProyeccionesService;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProyeccionesResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProyeccionesServiceImpl implements ProyeccionesService {

    private final ProyeccionesDAO proyeccionesDAO;
    private final DetalleVentaDAO detalleVentaDAO;
    private final VentaRegistroDAO ventaRegistroDAO;
    private final PedidoCompraDAO pedidoCompraDAO;
    private final DetalleProveedorProductoDAO detalleProveedorProductoDAO;
    private final ProyeccionesMapper proyeccionesMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProyeccionesResponseDTO> listar() {
        return proyeccionesDAO.findAll().stream()
                .map(proyeccionesMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProyeccionesResponseDTO> listarPorTipo(Long idTipoProyeccion) {
        if (idTipoProyeccion == null)
            throw new IllegalArgumentException("El ID no puede ser nulo");
        return proyeccionesDAO.findByTipoProyeccionId(idTipoProyeccion).stream()
                .map(proyeccionesMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProyeccionesResponseDTO> listarPorProducto(Long idProducto) {
        if (idProducto == null)
            throw new IllegalArgumentException("El ID no puede ser nulo");
        return proyeccionesDAO.findByProductoId(idProducto).stream()
                .map(proyeccionesMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProyeccionesResponseDTO> listarPorCategoria(Long idCategoria) {
        if (idCategoria == null)
            throw new IllegalArgumentException("El ID no puede ser nulo");
        return proyeccionesDAO.findByCategoriaId(idCategoria).stream()
                .map(proyeccionesMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProyeccionesResponseDTO> consultarProveedoresMasFiables() {
        return pedidoCompraDAO.findByEstadoPedidoId(6L).stream()
                .collect(Collectors.groupingBy(
                        p -> p.getProveedor(),
                        Collectors.counting()))
                .entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .map(entry -> ProyeccionesResponseDTO.builder()
                        .referenciaTipo("PROVEEDOR")
                        .resultadoProyeccion(entry.getValue() + " pedidos completados")
                        .nombreProducto(entry.getKey().getNombreProv())
                        .build())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProyeccionesResponseDTO> consultarClientesMasFileles() {
        // Agrupa ventas por cliente y suma el total comprado
        return ventaRegistroDAO.findAll().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getCliente(),
                        Collectors.reducing(BigDecimal.ZERO,
                                VentaRegistro::getTotalVenta,
                                BigDecimal::add)))
                .entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .map(entry -> ProyeccionesResponseDTO.builder()
                        .referenciaTipo("CLIENTE")
                        .resultadoProyeccion("Total comprado: $" + entry.getValue())
                        .nombreProducto(entry.getKey().getNombreCliente())
                        .build())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProyeccionesResponseDTO> consultarPreciosMercado() {
        // Lista todos los productos con sus precios por proveedor
        return detalleProveedorProductoDAO.findAll().stream()
                .map(detalle -> ProyeccionesResponseDTO.builder()
                        .referenciaTipo("PRECIO_MERCADO")
                        .resultadoProyeccion("Precio: $" + detalle.getPrecioUnitario())
                        .nombreProducto(detalle.getProducto().getNombreProducto())
                        .nombreCategoria(detalle.getProveedor().getNombreProv())
                        .build())
                .toList();
    }

}
