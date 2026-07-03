package com.sena.meciccolombia.mediccolombia.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.meciccolombia.mediccolombia.component.VentaRegistroMapper;
import com.sena.meciccolombia.mediccolombia.dao.ClienteDAO;
import com.sena.meciccolombia.mediccolombia.dao.DetalleVentaDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProductoDAO;
import com.sena.meciccolombia.mediccolombia.dao.UsuarioDAO;
import com.sena.meciccolombia.mediccolombia.dao.VentaRegistroDAO;
import com.sena.meciccolombia.mediccolombia.domain.Cliente;
import com.sena.meciccolombia.mediccolombia.domain.DetalleVenta;
import com.sena.meciccolombia.mediccolombia.domain.Producto;
import com.sena.meciccolombia.mediccolombia.domain.Usuario;
import com.sena.meciccolombia.mediccolombia.domain.VentaRegistro;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.IVentaRegistroService;
import com.sena.meciccolombia.mediccolombia.service.MovimientoProdService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.MovimientoProdRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.VentaRegistroRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DetalleVentaResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProductoMasVendidoResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProductosVendidosResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TotalVendidosDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.VentaRegistroResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentaRegistroServiceImpl implements IVentaRegistroService {

        private final VentaRegistroDAO ventaRegistroDAO;
        private final DetalleVentaDAO detalleVentaDAO;
        private final ClienteDAO clienteDAO;
        private final UsuarioDAO usuarioDAO;
        private final ProductoDAO productoDAO;
        private final VentaRegistroMapper ventaRegistroMapper;
        private final MovimientoProdService movimientoProdService;

        @Override
        @Transactional
        public VentaRegistroResponseDTO registrarVenta(VentaRegistroRequestDTO dto) {
                if (dto == null)
                        throw new IllegalArgumentException("El DTO no puede ser nulo");
                if (dto.getDetalles() == null || dto.getDetalles().isEmpty()) {
                        throw new IllegalArgumentException("La venta debe tener al menos un producto");
                }

                Cliente cliente = clienteDAO.findById(dto.getIdCliente())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "El cliente con el ID:" + dto.getIdCliente()
                                                                + " no ha sido encontrado"));
                Usuario usuario = usuarioDAO.findById(dto.getIdUsuario())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "El usuario con el ID:" + dto.getIdUsuario() + " no fue encontrado"));
                VentaRegistro venta = ventaRegistroMapper.toEntity(dto, cliente, usuario);
                VentaRegistro ventaGuardada = ventaRegistroDAO.save(venta);

                List<DetalleVenta> detallesGuardados = new ArrayList<>();
                BigDecimal totalVenta = BigDecimal.ZERO;

                for (var detalleDTO : dto.getDetalles()) {
                        Producto producto = productoDAO.findById(detalleDTO.getIdProducto())
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "El producto con el ID:" + detalleDTO.getIdProducto()
                                                                        + " no fue encontrado"));
                        if (producto.getStock() < detalleDTO.getCantidad()) {
                                throw new ResourceNotFoundException("Stock insuficiente para el producto: "
                                                + producto.getNombreProducto()
                                                + ", Stock disponible: " + producto.getStock()
                                                + " , Cantidad solicitada: " + detalleDTO.getCantidad());
                        }
                        BigDecimal subtotal = detalleDTO.getPrecioUnitario()
                                        .multiply(BigDecimal.valueOf(detalleDTO.getCantidad()));
                        totalVenta = totalVenta.add(subtotal);

                        DetalleVenta detalle = ventaRegistroMapper.toDetalleEntity(detalleDTO, producto, ventaGuardada);
                        detallesGuardados.add(detalleVentaDAO.save(detalle));

                        MovimientoProdRequestDTO movimientoDTO = MovimientoProdRequestDTO.builder()
                                        .idProducto(producto.getId())
                                        .idUsuario(dto.getIdUsuario())
                                        .cantidad(detalleDTO.getCantidad())
                                        .pickerChecker("VENTA-" +ventaGuardada.getId())
                                        .idTipoMovimiento(6L)
                                        .movimiento("Salida por venta #" + ventaGuardada.getId())
                                        .build();
                        movimientoProdService.registrarMovimiento(movimientoDTO);
                }
                ventaGuardada.setTotalVenta(totalVenta);
                ventaRegistroDAO.save(ventaGuardada);

                List<DetalleVentaResponseDTO> detallesResponse = detallesGuardados.stream()
                                .map(ventaRegistroMapper::toDetalleResponse)
                                .toList();
                return ventaRegistroMapper.toResponseDTO(ventaGuardada, detallesResponse);
        }

        @Override
        @Transactional(readOnly = true)
        public VentaRegistroResponseDTO obtenerVentaPorId(Long id) {
                if (id == null)
                        throw new IllegalArgumentException("El ID no puede ser nulo");

                VentaRegistro venta = ventaRegistroDAO.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "La venta con el ID:" + id + " no fue encontrada"));
                List<DetalleVentaResponseDTO> detalles = detalleVentaDAO.findByVentaId(id)
                                .stream()
                                .map(ventaRegistroMapper::toDetalleResponse)
                                .toList();
                return ventaRegistroMapper.toResponseDTO(venta, detalles);
        }

        @Override
        @Transactional(readOnly = true)
        public List<VentaRegistroResponseDTO> listarVentas() {
                return ventaRegistroDAO.findAll().stream()
                                .map(venta -> {
                                        List<DetalleVentaResponseDTO> detalles = detalleVentaDAO
                                                        .findByVentaId(venta.getId())
                                                        .stream()
                                                        .map(ventaRegistroMapper::toDetalleResponse)
                                                        .toList();
                                        return ventaRegistroMapper.toResponseDTO(venta, detalles);
                                })
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public List<VentaRegistroResponseDTO> listarVentasPorCliente(Long idCliente) {
                if (idCliente == null)
                        throw new IllegalArgumentException("El idCliente no puede ser nulo");
                return ventaRegistroDAO.findByClienteId(idCliente).stream()
                                .map(venta -> {
                                        List<DetalleVentaResponseDTO> detalles = detalleVentaDAO
                                                        .findByVentaId(venta.getId())
                                                        .stream()
                                                        .map(ventaRegistroMapper::toDetalleResponse)
                                                        .toList();
                                        return ventaRegistroMapper.toResponseDTO(venta, detalles);
                                })
                                .toList();
        }

        @Override
        public List<VentaRegistroResponseDTO> listarVentasPorUsuario(Long idUsuario) {
                if (idUsuario == null)
                        throw new IllegalArgumentException("El idUsuario no puede ser nulo");
                return ventaRegistroDAO.findByUsuarioId(idUsuario).stream()
                                .map(venta -> {
                                        List<DetalleVentaResponseDTO> detalles = detalleVentaDAO
                                                        .findByVentaId(venta.getId())
                                                        .stream()
                                                        .map(ventaRegistroMapper::toDetalleResponse)
                                                        .toList();
                                        return ventaRegistroMapper.toResponseDTO(venta, detalles);
                                })
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public TotalVendidosDTO obtenerTotalVendidoEnElDia(LocalDateTime inicio, LocalDateTime fin) {
                if (inicio == null || fin == null) {
                        throw new IllegalArgumentException("La fecha de Inicio o la fecha Fin no pueden ser Nulas");
                }

                List<VentaRegistro> ventas = ventaRegistroDAO.findByFechaVentaBetween(inicio, fin);
                List<ProductosVendidosResponseDTO> productoDetalle = ventas.stream()
                                .flatMap(venta -> venta.getDetalles().stream())
                                .map(detalle -> new ProductosVendidosResponseDTO(
                                                detalle.getProducto().getId(),
                                                detalle.getProducto().getNombreProducto(),
                                                detalle.getPrecioUnitario(),
                                                detalle.getCantidad(),
                                                detalle.getPrecioUnitario()
                                                                .multiply(BigDecimal.valueOf(detalle.getCantidad()))))
                                .toList();

                BigDecimal totalVenta = productoDetalle.stream()
                                .map(ProductosVendidosResponseDTO::getSubtotal)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                return TotalVendidosDTO.builder()
                                .productos(productoDetalle)
                                .totalVenta(totalVenta)
                                .build();
        }

        @Override
        @Transactional(readOnly = true)
        public List<ProductoMasVendidoResponseDTO> obtenerProductosMasVendidos() {
                return detalleVentaDAO.findAll().stream()
                                .collect(Collectors.groupingBy(
                                                d -> d.getProducto(),
                                                Collectors.summingInt(DetalleVenta::getCantidad)))
                                .entrySet().stream()
                                .sorted(Map.Entry.<Producto, Integer>comparingByValue(Comparator.reverseOrder()))
                                .map(entry -> ProductoMasVendidoResponseDTO.builder()
                                                .idProducto(entry.getKey().getId())
                                                .nombreProducto(entry.getKey().getNombreProducto())
                                                .totalUnidadesVendidas(entry.getValue())
                                                .build())
                                .toList();
        }

        

}
