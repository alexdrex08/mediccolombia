package com.sena.meciccolombia.mediccolombia.service.impl;

import com.sena.meciccolombia.mediccolombia.dao.MovimientoProdDAO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.meciccolombia.mediccolombia.component.PedidoCompraMapper;
import com.sena.meciccolombia.mediccolombia.dao.DetallePedidoDAO;
import com.sena.meciccolombia.mediccolombia.dao.EstadoPedidoDAO;
import com.sena.meciccolombia.mediccolombia.dao.PedidoCompraDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProductoDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProveedorDAO;
import com.sena.meciccolombia.mediccolombia.domain.DetallePedido;
import com.sena.meciccolombia.mediccolombia.domain.EstadoPedido;
import com.sena.meciccolombia.mediccolombia.domain.PedidoCompra;
import com.sena.meciccolombia.mediccolombia.domain.Producto;
import com.sena.meciccolombia.mediccolombia.domain.Proveedor;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.security.MyUserDetails;
import com.sena.meciccolombia.mediccolombia.service.MovimientoProdService;
import com.sena.meciccolombia.mediccolombia.service.PedidoCompraService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.CambiarEstadoPedidoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.MovimientoProdRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.PedidoCompraRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DetallePedidoResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.PedidoCompraResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoCompraServiceImpl implements PedidoCompraService {

    private final MovimientoProdDAO movimientoProdDAO;
    private final PedidoCompraDAO pedidoCompraDAO;
    private final DetallePedidoDAO detallePedidoDAO;
    private final ProveedorDAO proveedorDAO;
    private final EstadoPedidoDAO estadoPedidoDAO;
    private final ProductoDAO productoDAO;
    private final PedidoCompraMapper pedidoCompraMapper;
    private final MovimientoProdService movimientoProdService;

    private static final Long TIPO_MOVIMIENTO_COMPRA = 1L;
    private static final Long TIPO_MOVIMIENTO_DEVOLUCION_PROVEEDOR = 12L;

    private static final Long ESTADO_BORRADOR = 1L;
    private static final Long ESTADO_COMPLETADO = 6L;
    private static final Long ESTADO_DEVULEVO = 9L;

    private static final Long USUARIO_ADMIN = 1L;

    @Override
    @Transactional
    public PedidoCompraResponseDTO crearPedido(PedidoCompraRequestDTO dto) {
        if (dto == null)
            throw new IllegalArgumentException("El DTO no puede ser nulo");
        if (dto.getDetalles() == null || dto.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("El pedido debe tener al menos un producto");
        }
        Proveedor proveedor = proveedorDAO.findById(dto.getIdProveedor())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "El proveedor con el ID:" + dto.getIdProveedor() + " no  encontrado"));
        EstadoPedido estadoInicial = estadoPedidoDAO.findById(ESTADO_BORRADOR)
                .orElseThrow(
                        () -> new ResourceNotFoundException("El Estado con el ID:" + ESTADO_BORRADOR + " no existe"));
        PedidoCompra pedido = pedidoCompraMapper.toEntity(dto, proveedor, estadoInicial);
        PedidoCompra pedidoGuardado = pedidoCompraDAO.save(pedido);

        List<DetallePedido> detallesGuardados = new ArrayList<>();
        BigDecimal totalPedido = BigDecimal.ZERO;

        for (var detalleDTO : dto.getDetalles()) {
            Producto producto = productoDAO.findById(detalleDTO.getIdProducto())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "El Producto con el ID:" + detalleDTO.getIdProducto() + " no ha sido encontrado"));
            DetallePedido detalle = pedidoCompraMapper.toDetalleEntity(detalleDTO, producto, pedidoGuardado);
            detallesGuardados.add(detallePedidoDAO.save(detalle));
            totalPedido = totalPedido.add(detalle.getSubtotal());
        }
        pedidoGuardado.setTotalPedido(totalPedido);
        pedidoCompraDAO.save(pedidoGuardado);

        List<DetallePedidoResponseDTO> detallesResponse = detallesGuardados.stream()
                .map(pedidoCompraMapper::toDetalleResponseDTO)
                .toList();
        return pedidoCompraMapper.toResponseDTO(pedidoGuardado, detallesResponse);

    }

    @Override
    public PedidoCompraResponseDTO obtenerPorId(Long id) {
        if (id == null)
            throw new IllegalArgumentException("El DTO no puede ser nulo");

        PedidoCompra pedido = pedidoCompraDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El Pedido con el ID:" + id + " no encontrado"));

        List<DetallePedidoResponseDTO> detalles = detallePedidoDAO.findByPedidoId(id)
                .stream()
                .map(pedidoCompraMapper::toDetalleResponseDTO)
                .toList();
        return pedidoCompraMapper.toResponseDTO(pedido, detalles);
    }

    @Override
    @Transactional
    public PedidoCompraResponseDTO cambiarEstado(Long id, CambiarEstadoPedidoRequestDTO dto) {
        if (dto == null)
            throw new IllegalArgumentException("El DTO no puede ser nulo");
        if (id == null)
            throw new IllegalArgumentException("El ID no puede ser nulo");

        PedidoCompra pedido = pedidoCompraDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El pedido con el ID:" + id + " no fue encontrado"));

        Long estadoActualId = pedido.getEstadoPedido().getId();
        Long nuevoEstadoId = dto.getIdEstadoPedido();

        if (estadoActualId >= 4 && nuevoEstadoId <= 3) {
            throw new IllegalStateException("Ruptura de flujo: El pedido ya inició su proceso de despacho ("
                    + pedido.getEstadoPedido().getNombreEstado()
                    + "). No se puede regresar a estados de preparación.");
        }

        if (dto.getIdEstadoPedido().equals(ESTADO_COMPLETADO)
                || pedido.getEstadoPedido().getId().equals(ESTADO_COMPLETADO)) {

            String firmaPedido = generarFirmaPickerChecker(pedido);

            if (movimientoProdDAO.existsByPickerChecker(firmaPedido)
                    && dto.getIdEstadoPedido().equals(ESTADO_COMPLETADO)) {
                throw new IllegalStateException(
                        "Ruptura de flujo: Este pedido ya fue RECIBIDO previamente. La firma de inventario ya existe.");
            }
        }

        if (pedido.getEstadoPedido().getId().equals(ESTADO_DEVULEVO)) {
            throw new IllegalStateException(
                    "Ruptura de flujo: El pedido ya fue DEVUELTO y su inventario ya fue afectado. No se puede retomar una acción de Devolución");
        }

        EstadoPedido nuevoEstado = estadoPedidoDAO.findById(dto.getIdEstadoPedido())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "El estado con el ID:" + dto.getIdEstadoPedido() + " no fue encontrado"));
        if (pedido.getEstadoPedido().getId().equals(dto.getIdEstadoPedido())) {
            throw new IllegalArgumentException(
                    "El pedido ya se encuentra en el estado: " + nuevoEstado.getNombreEstado());
        }
        if (dto.getIdEstadoPedido().equals(ESTADO_COMPLETADO)) {
            procesarRecepcionPedido(pedido);
        }
        if (dto.getIdEstadoPedido().equals(ESTADO_DEVULEVO)) {
            procesarDevolucionPedido(pedido);
        }
        pedido.setEstadoPedido(nuevoEstado);
        if (dto.getObservacion() != null) {
            pedido.setObservacion(dto.getObservacion());
        }

        pedidoCompraDAO.save(pedido);

        List<DetallePedidoResponseDTO> detallesResponse = detallePedidoDAO.findByPedidoId(id)
                .stream()
                .map(pedidoCompraMapper::toDetalleResponseDTO)
                .toList();

        return pedidoCompraMapper.toResponseDTO(pedido, detallesResponse);

    }

    private String generarFirmaPickerChecker(PedidoCompra pedido) {
        List<DetallePedido> detalles = detallePedidoDAO.findByPedidoId(pedido.getId());

        String ids = detalles.stream()
                .map(d -> String.valueOf(d.getProducto().getId()))
                .collect(Collectors.joining("-"));
        String cantidades = detalles.stream()
                .map(d -> String.valueOf(d.getCantidad()))
                .collect(Collectors.joining("-"));

        return "PRES" + pedido.getId()
                + "P" + ids
                + "-A1"
                + "-C" + cantidades;
    }

    private void procesarRecepcionPedido(PedidoCompra pedidoCompra) {
        List<DetallePedido> detalles = detallePedidoDAO.findByPedidoId(pedidoCompra.getId());
        String pickerChecker = generarFirmaPickerChecker(pedidoCompra);

        for (DetallePedido detalle : detalles) {
            MovimientoProdRequestDTO movimientoDTO = MovimientoProdRequestDTO.builder()
                    .idProducto(detalle.getProducto().getId())
                    .idUsuario(obtenerIdUsuarioActual())
                    .cantidad(detalle.getCantidad())
                    .idTipoMovimiento(TIPO_MOVIMIENTO_COMPRA)
                    .movimiento("Entrada por pedido #" + pedidoCompra.getId())
                    .pickerChecker(pickerChecker)
                    .build();
            movimientoProdService.registrarMovimiento(movimientoDTO);
        }
    }

    private void procesarDevolucionPedido(PedidoCompra pedido) {
        List<DetallePedido> detalles = detallePedidoDAO.findByPedidoId(pedido.getId());

        for (DetallePedido detalle : detalles) {
            MovimientoProdRequestDTO movimientoDTO = MovimientoProdRequestDTO.builder()
                    .idProducto(detalle.getProducto().getId())
                    .idUsuario(obtenerIdUsuarioActual())
                    .cantidad(detalle.getCantidad())
                    .idTipoMovimiento(TIPO_MOVIMIENTO_DEVOLUCION_PROVEEDOR)
                    .movimiento("Devolucion a proveedor por pedido #" + pedido.getId())
                    .build();
            movimientoProdService.registrarMovimiento(movimientoDTO);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoCompraResponseDTO> listar() {
        return pedidoCompraDAO.findAll().stream()
                .map(pedido -> {
                    List<DetallePedidoResponseDTO> detalles = detallePedidoDAO.findByPedidoId(pedido.getId())
                            .stream()
                            .map(pedidoCompraMapper::toDetalleResponseDTO)
                            .toList();
                    return pedidoCompraMapper.toResponseDTO(pedido, detalles);
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoCompraResponseDTO> listarPorProveedor(Long idProveedor) {
        if (idProveedor == null)
            throw new IllegalArgumentException("El ID no puede ser nulo");
        return pedidoCompraDAO.findByProveedorId(idProveedor).stream()
                .map(pedido -> {
                    List<DetallePedidoResponseDTO> detalles = detallePedidoDAO.findByPedidoId(pedido.getId())
                            .stream()
                            .map(pedidoCompraMapper::toDetalleResponseDTO)
                            .toList();
                    return pedidoCompraMapper.toResponseDTO(pedido, detalles);
                })
                .toList();
    }

    @Override
    public List<PedidoCompraResponseDTO> listarPorEstado(Long idEstado) {
        if (idEstado == null)
            throw new IllegalArgumentException("El ID no puede ser nulo");
        return pedidoCompraDAO.findByEstadoPedidoId(idEstado).stream()
                .map(pedido -> {
                    List<DetallePedidoResponseDTO> detalles = detallePedidoDAO.findByPedidoId(pedido.getId())
                            .stream()
                            .map(pedidoCompraMapper::toDetalleResponseDTO)
                            .toList();
                    return pedidoCompraMapper.toResponseDTO(pedido, detalles);
                })
                .toList();
    }

    private Long obtenerIdUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null ||
                !(authentication.getPrincipal() instanceof MyUserDetails user)) {
            throw new IllegalStateException("No existe un usuario autenticado.");
        }
        return user.getId();
    }

}
