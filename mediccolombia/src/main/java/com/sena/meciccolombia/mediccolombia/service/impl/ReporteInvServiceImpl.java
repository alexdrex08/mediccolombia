package com.sena.meciccolombia.mediccolombia.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.meciccolombia.mediccolombia.component.ReporteInvMapper;
import com.sena.meciccolombia.mediccolombia.dao.AlertaInvDAO;
import com.sena.meciccolombia.mediccolombia.dao.CategoriaDAO;
import com.sena.meciccolombia.mediccolombia.dao.ClienteDAO;
import com.sena.meciccolombia.mediccolombia.dao.DetalleVentaDAO;
import com.sena.meciccolombia.mediccolombia.dao.FiltroBusquedaDAO;
import com.sena.meciccolombia.mediccolombia.dao.MovimientoProdDAO;
import com.sena.meciccolombia.mediccolombia.dao.PedidoCompraDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProductoDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProveedorDAO;
import com.sena.meciccolombia.mediccolombia.dao.ReporteInvDAO;
import com.sena.meciccolombia.mediccolombia.dao.UsuarioDAO;
import com.sena.meciccolombia.mediccolombia.dao.VentaRegistroDAO;
import com.sena.meciccolombia.mediccolombia.domain.Categoria;
import com.sena.meciccolombia.mediccolombia.domain.Cliente;
import com.sena.meciccolombia.mediccolombia.domain.Correo;
import com.sena.meciccolombia.mediccolombia.domain.DetallePedido;
import com.sena.meciccolombia.mediccolombia.domain.DetalleVenta;
import com.sena.meciccolombia.mediccolombia.domain.Direccion;
import com.sena.meciccolombia.mediccolombia.domain.EstadoPedido;
import com.sena.meciccolombia.mediccolombia.domain.FiltroBusqueda;
import com.sena.meciccolombia.mediccolombia.domain.PedidoCompra;
import com.sena.meciccolombia.mediccolombia.domain.Producto;
import com.sena.meciccolombia.mediccolombia.domain.Proveedor;
import com.sena.meciccolombia.mediccolombia.domain.ReporteInv;
import com.sena.meciccolombia.mediccolombia.domain.Telefono;
import com.sena.meciccolombia.mediccolombia.domain.Usuario;
import com.sena.meciccolombia.mediccolombia.domain.VentaRegistro;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.ReporteInvService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.ReporteInvRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ReporteDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ReporteInvResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@Slf4j
@RequiredArgsConstructor

public class ReporteInvServiceImpl implements ReporteInvService {

        private final ReporteInvDAO reporteInvDAO;
        private final UsuarioDAO usuarioDAO;
        private final FiltroBusquedaDAO filtroBusquedaDAO;
        private final ProductoDAO productoDAO;
        private final CategoriaDAO categoriaDAO;
        private final MovimientoProdDAO movimientoProdDAO;
        private final AlertaInvDAO alertaInvDAO;
        private final VentaRegistroDAO ventaRegistroDAO;
        private final DetalleVentaDAO detalleVentaDAO;
        private final PedidoCompraDAO pedidoCompraDAO;
        private final ClienteDAO clienteDAO;
        private final ProveedorDAO proveedorDAO;
        private final ReporteInvMapper reporteInvMapper;

        @Override
        @Transactional
        public ReporteDetalleResponseDTO generarReporte(ReporteInvRequestDTO dto) {
                if (dto == null)
                        throw new IllegalArgumentException("El DTO no puede ser nulo");
                if (dto.getTipoReporte() == null)
                        throw new IllegalArgumentException("El tipo de reporte no puede ser nulo");

                Usuario usuario = usuarioDAO.findById(dto.getIdUsuario())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "El Usuario con el ID" + dto.getIdUsuario() + " no fue encontrado"));
                FiltroBusqueda filtroBusqueda = null;
                if (dto.getIdFiltroBusqueda() != null) {
                        filtroBusqueda = filtroBusquedaDAO.findById(dto.getIdFiltroBusqueda()).orElse(null);
                }

                long cantidadReal = reporteInvDAO.countByTipoReporte(dto.getTipoReporte());
                long contador = cantidadReal + 1;

                String urlResultado = "reporte_" + dto.getTipoReporte() + "_" + contador + ".pdf";
                Object contenido = generarContenido(dto);

                ReporteInv reporte = ReporteInv.builder()
                                .fechaGeneracion(LocalDateTime.now())
                                .tipoReporte(dto.getTipoReporte())
                                .tipoResultado("PDF")
                                .resultado(urlResultado)
                                .usuario(usuario)
                                .filtroBusqueda(filtroBusqueda)
                                .build();
                ReporteInv reporteGuardado = reporteInvDAO.save(reporte);

                return ReporteDetalleResponseDTO.builder()
                                .idReporte(reporteGuardado.getId())
                                .tipoReporte(reporteGuardado.getTipoReporte())
                                .fechaGeneracion(reporteGuardado.getFechaGeneracion())
                                .nombreUsuario(usuario.getNombre())
                                .urlResultado(urlResultado)
                                .contenido(contenido)
                                .build();
        }

        private Object generarContenido(ReporteInvRequestDTO dto) {
                return switch (dto.getTipoReporte()) {
                        case "REPORTE_PRODUCTO" -> generarReporteProducto(dto.getIdReferencia());
                        case "REPORTE_CATEGORIA" -> generarReporteCategoria(dto.getIdReferencia());
                        case "REPORTE_VENTAS" -> generarReporteVentas();
                        case "REPORTE_PEDIDOS" -> generarReportePedidos();
                        case "REPORTE_STOCK" -> generarReporteStock();
                        case "REPORTE_VENCIMIENTOS" -> generarReporteVencimientos();
                        case "REPORTE_CLIENTES" -> generarReporteClientes();
                        case "REPORTE_PROVEEDORES" -> generarReporteProveedores();
                        case "REPORTE_GENERAL" -> generarReporteGeneral();
                        default -> throw new ResourceNotFoundException(
                                        "Tipo de Reporte no reconocido" + dto.getTipoReporte());
                };
        }
        // ===============================================================================================

        // REPORTE PARA PRODUCTOS
        // ===============================================================================================
        private Map<String, Object> generarReporteProducto(Long idProducto) {
                if (idProducto == null)
                        throw new IllegalArgumentException("Se requiere idReferencia para REPORTE_PRODUCTO");

                Producto producto = productoDAO.findById(idProducto)
                                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                Map<String, Object> contenido = new LinkedHashMap<>();
                contenido.put("producto", producto.getNombreProducto());
                contenido.put("stock", producto.getStock());
                contenido.put("stockMinimo", producto.getStockMinimo());
                contenido.put("stockMaximo", producto.getStockMaximo());
                contenido.put("lote", producto.getLote());
                contenido.put("fechaExpiracion", producto.getFechaExpiracion());

                contenido.put("movimientos", movimientoProdDAO.findByProductoId(idProducto)
                                .stream().map(m -> Map.of(
                                                "fecha", m.getFechaMovimiento(),
                                                "cantidad", m.getCantidad(),
                                                "tipo", m.getTipoMovimiento().getNombreMovimiento(),
                                                "motivo", m.getMovimiento() != null ? m.getMovimiento() : ""))
                                .toList());
                contenido.put("alertas", alertaInvDAO.findByProductoId(idProducto)
                                .stream().map(a -> Map.of(
                                                "tipo", a.getTipoAlerta(),
                                                "descripcion", a.getDescripcion(),
                                                "fecha", a.getFechaCreacion()))
                                .toList());

                return contenido;
        }

        private Map<String, Object> generarReporteCategoria(Long idCategoria) {

                if (idCategoria == null)
                        throw new IllegalArgumentException("Se requiere idReferencia para REPORTE_CATEGORIA");

                Categoria categoria = categoriaDAO.findById(idCategoria)
                                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
                List<Producto> productos = productoDAO.findBycategoriaId(idCategoria);

                Map<String, Object> contenido = new LinkedHashMap<>();
                contenido.put("categoria", categoria.getNombre());
                contenido.put("totalProductos", productos.size());
                contenido.put("productos", productos.stream().map(p -> {
                        Map<String, Object> prod = new LinkedHashMap<>();
                        prod.put("nombre", p.getNombreProducto());
                        prod.put("stock", p.getStock());
                        prod.put("stockMinimo", p.getStockMinimo());
                        prod.put("fechaExpiracion", p.getFechaExpiracion());
                        return prod;
                }).toList());
                return contenido;
        }

        private Map<String, Object> generarReporteVentas() {

                List<VentaRegistro> ventas = ventaRegistroDAO.findAll();

                Map<String, Object> contenido = new LinkedHashMap<>();

                contenido.put("totalVentas", ventas.size());

                contenido.put("montoTotalVendido",
                                ventas.stream()
                                                .map(VentaRegistro::getTotalVenta)
                                                .reduce(BigDecimal.ZERO, BigDecimal::add));

                contenido.put("ventas",
                                ventas.stream()
                                                .map(v -> {

                                                        Map<String, Object> ventaMap = new LinkedHashMap<>();

                                                        // DATOS DE LA VENTA
                                                        ventaMap.put("idVenta", v.getId());
                                                        ventaMap.put("fechaVenta", v.getFechaVenta());
                                                        ventaMap.put("totalVenta", v.getTotalVenta());

                                                        // CLIENTE
                                                        if (v.getCliente() != null) {

                                                                Map<String, Object> clienteMap = new LinkedHashMap<>();

                                                                clienteMap.put("idCliente",
                                                                                v.getCliente().getId());

                                                                clienteMap.put("nombre",
                                                                                v.getCliente().getNombreCliente());

                                                                clienteMap.put("identificacion",
                                                                                v.getCliente().getIdentificacion());

                                                                ventaMap.put("cliente", clienteMap);
                                                        }

                                                        // USUARIO
                                                        if (v.getUsuario() != null) {

                                                                Map<String, Object> usuarioMap = new LinkedHashMap<>();

                                                                usuarioMap.put("idUsuario",
                                                                                v.getUsuario().getId());

                                                                usuarioMap.put("nombre",
                                                                                v.getUsuario().getNombre());

                                                                ventaMap.put("usuario", usuarioMap);
                                                        }

                                                        // DETALLES
                                                        ventaMap.put("detalles",
                                                                        v.getDetalles()
                                                                                        .stream()
                                                                                        .map(d -> {

                                                                                                Map<String, Object> detalleMap = new LinkedHashMap<>();

                                                                                                detalleMap.put("idDetalle",
                                                                                                                d.getId());

                                                                                                detalleMap.put("cantidad",
                                                                                                                d.getCantidad());

                                                                                                detalleMap.put("precioUnitario",
                                                                                                                d.getPrecioUnitario());

                                                                                                detalleMap.put("subtotal",
                                                                                                                d.getPrecioUnitario()
                                                                                                                                .multiply(
                                                                                                                                                BigDecimal.valueOf(
                                                                                                                                                                d.getCantidad())));

                                                                                                if (d.getProducto() != null) {

                                                                                                        Map<String, Object> productoMap = new LinkedHashMap<>();

                                                                                                        productoMap.put("idProducto",
                                                                                                                        d.getProducto().getId());

                                                                                                        productoMap.put("nombreProducto",
                                                                                                                        d.getProducto().getNombreProducto());

                                                                                                        productoMap.put("stockActual",
                                                                                                                        d.getProducto().getStock());

                                                                                                        productoMap.put("lote",
                                                                                                                        d.getProducto().getLote());

                                                                                                        productoMap.put("fechaExpiracion",
                                                                                                                        d.getProducto().getFechaExpiracion());

                                                                                                        if (d.getProducto()
                                                                                                                        .getCategoria() != null) {
                                                                                                                productoMap.put(
                                                                                                                                "categoria",
                                                                                                                                d.getProducto()
                                                                                                                                                .getCategoria()
                                                                                                                                                .getNombre());
                                                                                                        }

                                                                                                        detalleMap.put("producto",
                                                                                                                        productoMap);
                                                                                                }

                                                                                                return detalleMap;

                                                                                        })
                                                                                        .toList());

                                                        return ventaMap;

                                                })
                                                .toList());

                return contenido;
        }

        // ===============================================================================================

        // REPORTE PARA PEDIDOS
        // ===============================================================================================

        private Map<String, Object> generarReportePedidos() {
                Map<String, Object> contenido = new LinkedHashMap<>();

                List<PedidoCompra> pedidos = pedidoCompraDAO.findAll();

                Map<EstadoPedido, List<PedidoCompra>> pedidosPorEstado = pedidos.stream()
                                .collect(Collectors.groupingBy(
                                                PedidoCompra::getEstadoPedido));
                contenido.put("totalPedidos", pedidos.size());

                contenido.put(
                                "valorTotalPedidos",
                                pedidos.stream()
                                                .map(PedidoCompra::getTotalPedido)
                                                .reduce(BigDecimal.ZERO, BigDecimal::add));

                contenido.put(
                                "cantidadEstados",
                                pedidos.stream()
                                                .map(p -> p.getEstadoPedido().getNombreEstado())
                                                .distinct()
                                                .count());

                List<Map<String, Object>> porEstado = pedidosPorEstado.entrySet().stream()
                                .map(entry -> {
                                        Map<String, Object> estadoMap = new LinkedHashMap<>();
                                        estadoMap.put("estado", entry.getKey().getNombreEstado());
                                        estadoMap.put("descripcion", entry.getKey().getDescripcion());
                                        estadoMap.put("cantidadPedidos", entry.getValue().size());
                                        estadoMap.put("valorTotal", entry.getValue().stream()
                                                        .map(PedidoCompra::getTotalPedido)
                                                        .reduce(BigDecimal.ZERO, BigDecimal::add));
                                        estadoMap.put(
                                                        "pedidos",
                                                        entry.getValue()
                                                                        .stream()
                                                                        .map(this::mapPedidoCompleto)
                                                                        .toList());
                                        return estadoMap;
                                }).toList();
                contenido.put("pedidosPorEstado", porEstado);
                return contenido;
        }

        private Map<String, Object> mapPedidoCompleto(PedidoCompra pedido) {

                Map<String, Object> pedidoMap = new LinkedHashMap<>();

                pedidoMap.put("idPedido", pedido.getId());
                pedidoMap.put("fechaPedido", pedido.getFechaPedido());
                pedidoMap.put("totalPedido", pedido.getTotalPedido());
                pedidoMap.put("observacion", pedido.getObservacion());

                if (pedido.getProveedor() != null) {

                        pedidoMap.put(
                                        "proveedor",
                                        Map.of(
                                                        "idProveedor",
                                                        pedido.getProveedor().getId(),

                                                        "nombre",
                                                        pedido.getProveedor().getNombreProv(),

                                                        "nit",
                                                        pedido.getProveedor().getNit()));
                }

                pedidoMap.put(
                                "detalles",
                                pedido.getDetallePedido() != null
                                                ? pedido.getDetallePedido()
                                                                .stream()
                                                                .map(this::mapDetallePedido)
                                                                .toList()
                                                : List.of());

                return pedidoMap;
        }

        private Map<String, Object> mapDetallePedido(DetallePedido detalle) {

                Map<String, Object> detalleMap = new LinkedHashMap<>();

                detalleMap.put("idDetalle", detalle.getId());

                detalleMap.put("cantidad", detalle.getCantidad());

                detalleMap.put("precioUnitario",
                                detalle.getPrecioUnitario());

                detalleMap.put("subtotal",
                                detalle.getSubtotal());

                Producto producto = detalle.getProducto();

                if (producto != null) {

                        Map<String, Object> productoMap = new LinkedHashMap<>();

                        productoMap.put(
                                        "idProducto",
                                        producto.getId());

                        productoMap.put(
                                        "nombreProducto",
                                        producto.getNombreProducto());

                        productoMap.put(
                                        "stockActual",
                                        producto.getStock());

                        productoMap.put(
                                        "stockMinimo",
                                        producto.getStockMinimo());

                        productoMap.put(
                                        "stockMaximo",
                                        producto.getStockMaximo());

                        productoMap.put(
                                        "lote",
                                        producto.getLote());

                        productoMap.put(
                                        "fechaExpiracion",
                                        producto.getFechaExpiracion());

                        productoMap.put(
                                        "fechaIngreso",
                                        producto.getFechaIngreso());

                        productoMap.put(
                                        "fechaModificacion",
                                        producto.getFechaModificacion());

                        if (producto.getCategoria() != null) {

                                productoMap.put(
                                                "categoria",
                                                producto.getCategoria().getNombre());
                        }

                        detalleMap.put(
                                        "producto",
                                        productoMap);
                }

                return detalleMap;
        }

        // ===============================================================================================

        // REPORTE PARA STOCKS
        // ===============================================================================================

        private Map<String, Object> generarReporteStock() {
                List<Producto> todos = productoDAO.findAll();

                List<Producto> critico = todos.stream()
                                .filter(p -> p.getStock() == 0 || p.getStock() <= p.getStockMinimo())
                                .toList();

                List<Producto> normal = todos.stream()
                                .filter(p -> p.getStock() > p.getStockMinimo()
                                                && p.getStock() <= p.getStockMaximo())
                                .toList();

                List<Producto> exceso = todos.stream()
                                .filter(p -> p.getStock() > p.getStockMaximo())
                                .toList();

                Map<String, Object> contenido = new LinkedHashMap<>();
                contenido.put("totalProductos", todos.size());
                contenido.put("critico", critico.stream().map(p -> Map.of(
                                "nombre", p.getNombreProducto(),
                                "stock", p.getStock(),
                                "stockMinimo", p.getStockMinimo())).toList());
                contenido.put("normal", normal.stream().map(p -> Map.of(
                                "nombre", p.getNombreProducto(),
                                "stock", p.getStock())).toList());
                contenido.put("exceso", exceso.stream().map(p -> Map.of(
                                "nombre", p.getNombreProducto(),
                                "stock", p.getStock(),
                                "stockMaximo", p.getStockMaximo())).toList());
                return contenido;
        }

        // ===============================================================================================

        // REPORTE PARA VENCIMIENTO
        // ===============================================================================================

        private Map<String, Object> generarReporteVencimientos() {
                LocalDateTime ahora = LocalDateTime.now();
                LocalDateTime limite = ahora.plusDays(7);

                List<Producto> todos = productoDAO.findAll();

                List<Producto> vencidos = todos.stream()
                                .filter(p -> p.getFechaExpiracion() != null
                                                && p.getFechaExpiracion().isBefore(ahora))
                                .toList();

                List<Producto> proximosAVencer = todos.stream()
                                .filter(p -> p.getFechaExpiracion() != null
                                                && p.getFechaExpiracion().isAfter(ahora)
                                                && p.getFechaExpiracion().isBefore(limite)
                                                && p.getStock() > 0)
                                .toList();

                Map<String, Object> contenido = new LinkedHashMap<>();
                contenido.put("cantidadVencidos", vencidos.size());
                contenido.put("cantidadProximosAVencer", proximosAVencer.size());

                contenido.put("vencidos", vencidos.stream().map(p -> Map.of(
                                "nombre", p.getNombreProducto(),
                                "stock", p.getStock(),
                                "fechaExpiracion", p.getFechaExpiracion())).toList());
                contenido.put("proximosAVencer", proximosAVencer.stream().map(p -> Map.of(
                                "nombre", p.getNombreProducto(),
                                "stock", p.getStock(),
                                "fechaExpiracion", p.getFechaExpiracion())).toList());
                return contenido;
        }

        // ===============================================================================================

        // REPORTE PARA CLIENTES
        // ===============================================================================================

        private Map<String, Object> generarReporteClientes() {
                List<Cliente> clientes = clienteDAO.findAll();
                List<VentaRegistro> todasLasVentas = ventaRegistroDAO.findAll();

                Map<Long, List<VentaRegistro>> ventasPorCliente = todasLasVentas.stream()
                                .filter(v -> v.getCliente() != null)
                                .collect(Collectors.groupingBy(
                                                v -> v.getCliente().getId()));

                Map<String, Object> contenido = new LinkedHashMap<>();
                contenido.put("totalClientes", clientes.size());

                contenido.put("clientesConCompras",
                                clientes.stream()
                                                .filter(c -> ventasPorCliente.containsKey(c.getId()))
                                                .count());

                contenido.put("clientesSinCompras",
                                clientes.stream()
                                                .filter(c -> !ventasPorCliente.containsKey(c.getId()))
                                                .count());

                contenido.put("totalFacturado",
                                todasLasVentas.stream()
                                                .map(VentaRegistro::getTotalVenta)
                                                .reduce(BigDecimal.ZERO, BigDecimal::add));
                contenido.put(
                                "clientes",
                                clientes.stream()
                                                .map(cliente -> mapClienteCompleto(
                                                                cliente,
                                                                ventasPorCliente))
                                                .toList());
                return contenido;
        }

        private Map<String, Object> mapClienteCompleto(Cliente cliente,
                        Map<Long, List<VentaRegistro>> ventasPorCliente) {
                Map<String, Object> clienteMap = new LinkedHashMap<>();
                clienteMap.put("idCliente", cliente.getId());

                clienteMap.put("nombre",
                                cliente.getNombreCliente());

                clienteMap.put("identificacion",
                                cliente.getIdentificacion());
                clienteMap.put(
                                "direcciones",
                                cliente.getDirecciones()
                                                .stream()
                                                .map(d -> Map.of(
                                                                "direccion", d.getDireccion(),
                                                                "complemento",
                                                                d.getComplemento() != null
                                                                                ? d.getComplemento()
                                                                                : "",
                                                                "tipo",
                                                                d.getTipoDireccion() != null
                                                                                ? d.getTipoDireccion().getNombreTipo()
                                                                                : "",
                                                                "barrio",
                                                                d.getBarrio() != null
                                                                                ? d.getBarrio().getNombreBarrio()
                                                                                : ""))
                                                .toList());
                clienteMap.put(
                                "correos",
                                cliente.getCorreos()
                                                .stream()
                                                .map(c -> Map.of(
                                                                "correo",
                                                                c.getCorreoElectronico(),

                                                                "tipo",
                                                                c.getTipoCorreo() != null
                                                                                ? c.getTipoCorreo().getNombreTipo()
                                                                                : ""))
                                                .toList());
                clienteMap.put(
                                "telefonos",
                                cliente.getTelefonos()
                                                .stream()
                                                .map(t -> Map.of(
                                                                "numero",
                                                                t.getNumero(),

                                                                "complemento",
                                                                t.getComplemento() != null
                                                                                ? t.getComplemento()
                                                                                : "",

                                                                "tipo",
                                                                t.getTipoTelefono() != null
                                                                                ? t.getTipoTelefono().getNombreTipo()
                                                                                : ""))
                                                .toList());
                List<VentaRegistro> ventas = ventasPorCliente.getOrDefault(cliente.getId(), List.of());
                clienteMap.put(
                                "cantidadVentas",
                                ventas.size());

                clienteMap.put(
                                "totalComprado",
                                ventas.stream()
                                                .map(VentaRegistro::getTotalVenta)
                                                .reduce(BigDecimal.ZERO, BigDecimal::add));
                clienteMap.put(
                                "ventas",
                                ventas.stream()
                                                .map(this::mapVentaCliente)
                                                .toList());
                return clienteMap;
        }

        private Map<String, Object> mapVentaCliente(VentaRegistro venta) {

                Map<String, Object> ventaMap = new LinkedHashMap<>();

                ventaMap.put("idVenta", venta.getId());

                ventaMap.put("fechaVenta", venta.getFechaVenta());

                ventaMap.put("totalVenta", venta.getTotalVenta());

                if (venta.getUsuario() != null) {

                        ventaMap.put(
                                        "usuario",
                                        Map.of(
                                                        "idUsuario",
                                                        venta.getUsuario().getId(),

                                                        "nombre",
                                                        venta.getUsuario().getNombre()));
                }

                ventaMap.put(
                                "cantidadProductos",
                                venta.getDetalles() != null
                                                ? venta.getDetalles().stream()
                                                                .mapToInt(DetalleVenta::getCantidad)
                                                                .sum()
                                                : 0);

                ventaMap.put(
                                "detalles",
                                venta.getDetalles() != null
                                                ? venta.getDetalles()
                                                                .stream()
                                                                .map(this::mapDetalleVentaCliente)
                                                                .toList()
                                                : List.of());

                return ventaMap;
        }

        private Map<String, Object> mapDetalleVentaCliente(
                        DetalleVenta detalle) {

                Map<String, Object> detalleMap = new LinkedHashMap<>();

                detalleMap.put(
                                "idDetalle",
                                detalle.getId());

                detalleMap.put(
                                "cantidad",
                                detalle.getCantidad());

                detalleMap.put(
                                "precioUnitario",
                                detalle.getPrecioUnitario());

                BigDecimal subtotal = detalle.getPrecioUnitario()
                                .multiply(BigDecimal.valueOf(detalle.getCantidad()));

                detalleMap.put(
                                "subtotalCalculado",
                                subtotal);

                Producto producto = detalle.getProducto();

                if (producto != null) {

                        Map<String, Object> productoMap = new LinkedHashMap<>();

                        productoMap.put(
                                        "idProducto",
                                        producto.getId());

                        productoMap.put(
                                        "nombreProducto",
                                        producto.getNombreProducto());

                        productoMap.put(
                                        "stockActual",
                                        producto.getStock());

                        productoMap.put(
                                        "stockMinimo",
                                        producto.getStockMinimo());

                        productoMap.put(
                                        "stockMaximo",
                                        producto.getStockMaximo());

                        productoMap.put(
                                        "lote",
                                        producto.getLote());

                        productoMap.put(
                                        "fechaExpiracion",
                                        producto.getFechaExpiracion());

                        productoMap.put(
                                        "fechaIngreso",
                                        producto.getFechaIngreso());

                        productoMap.put(
                                        "fechaModificacion",
                                        producto.getFechaModificacion());

                        if (producto.getCategoria() != null) {

                                productoMap.put(
                                                "categoria",
                                                producto.getCategoria().getNombre());
                        }

                        detalleMap.put(
                                        "producto",
                                        productoMap);
                }

                return detalleMap;
        }

        // ===============================================================================================

        // REPORTE PARA PROVEEDORES
        // ===============================================================================================

        private Map<String, Object> generarReporteProveedores() {

                List<Proveedor> proveedores = proveedorDAO.findAll();

                List<PedidoCompra> todosLosPedidos = pedidoCompraDAO.findAll();

                Map<Long, List<PedidoCompra>> pedidosPorProveedor = todosLosPedidos.stream()
                                .filter(p -> p.getProveedor() != null)
                                .collect(Collectors.groupingBy(
                                                p -> p.getProveedor().getId()));

                Map<String, Object> contenido = new LinkedHashMap<>();

                contenido.put(
                                "totalProveedores",
                                proveedores.size());

                contenido.put(
                                "proveedoresConPedidos",
                                proveedores.stream()
                                                .filter(p -> pedidosPorProveedor.containsKey(
                                                                p.getId()))
                                                .count());

                contenido.put(
                                "proveedoresSinPedidos",
                                proveedores.stream()
                                                .filter(p -> !pedidosPorProveedor.containsKey(
                                                                p.getId()))
                                                .count());

                contenido.put(
                                "totalPedidos",
                                todosLosPedidos.size());

                contenido.put(
                                "valorTotalCompras",
                                todosLosPedidos.stream()
                                                .map(PedidoCompra::getTotalPedido)
                                                .reduce(
                                                                BigDecimal.ZERO,
                                                                BigDecimal::add));

                contenido.put(
                                "proveedores",
                                proveedores.stream()
                                                .map(proveedor -> mapProveedorCompleto(
                                                                proveedor,
                                                                pedidosPorProveedor))
                                                .toList());

                return contenido;
        }

        private Map<String, Object> mapProveedorCompleto(
                        Proveedor proveedor,
                        Map<Long, List<PedidoCompra>> pedidosPorProveedor) {

                Map<String, Object> proveedorMap = new LinkedHashMap<>();

                proveedorMap.put(
                                "idProveedor",
                                proveedor.getId());

                proveedorMap.put(
                                "nombre",
                                proveedor.getNombreProv());

                proveedorMap.put(
                                "nit",
                                proveedor.getNit());

                proveedorMap.put(
                                "direcciones",
                                proveedor.getDirecciones() != null
                                                ? proveedor.getDirecciones()
                                                                .stream()
                                                                .map(this::mapDireccionProveedor)
                                                                .toList()
                                                : List.of());

                proveedorMap.put(
                                "correos",
                                proveedor.getCorreos() != null
                                                ? proveedor.getCorreos()
                                                                .stream()
                                                                .map(this::mapCorreoProveedor)
                                                                .toList()
                                                : List.of());

                proveedorMap.put(
                                "telefonos",
                                proveedor.getTelefonos() != null
                                                ? proveedor.getTelefonos()
                                                                .stream()
                                                                .map(this::mapTelefonoProveedor)
                                                                .toList()
                                                : List.of());

                List<PedidoCompra> pedidos = pedidosPorProveedor.getOrDefault(
                                proveedor.getId(),
                                List.of());

                proveedorMap.put(
                                "cantidadPedidos",
                                pedidos.size());

                proveedorMap.put(
                                "valorTotalPedidos",
                                pedidos.stream()
                                                .map(PedidoCompra::getTotalPedido)
                                                .reduce(
                                                                BigDecimal.ZERO,
                                                                BigDecimal::add));

                proveedorMap.put(
                                "pedidos",
                                pedidos.stream()
                                                .map(this::mapPedidoProveedor)
                                                .toList());

                return proveedorMap;
        }

        private Map<String, Object> mapDireccionProveedor(
                        Direccion direccion) {

                Map<String, Object> direccionMap = new LinkedHashMap<>();

                direccionMap.put(
                                "direccion",
                                direccion.getDireccion());

                direccionMap.put(
                                "complemento",
                                direccion.getComplemento());

                direccionMap.put(
                                "tipo",
                                direccion.getTipoDireccion() != null
                                                ? direccion.getTipoDireccion()
                                                                .getNombreTipo()
                                                : null);

                direccionMap.put(
                                "barrio",
                                direccion.getBarrio() != null
                                                ? direccion.getBarrio()
                                                                .getNombreBarrio()
                                                : null);

                return direccionMap;
        }

        private Map<String, Object> mapCorreoProveedor(
                        Correo correo) {

                Map<String, Object> correoMap = new LinkedHashMap<>();

                correoMap.put(
                                "correo",
                                correo.getCorreoElectronico());

                correoMap.put(
                                "tipo",
                                correo.getTipoCorreo() != null
                                                ? correo.getTipoCorreo()
                                                                .getNombreTipo()
                                                : null);

                return correoMap;
        }

        private Map<String, Object> mapTelefonoProveedor(
                        Telefono telefono) {

                Map<String, Object> telefonoMap = new LinkedHashMap<>();

                telefonoMap.put(
                                "numero",
                                telefono.getNumero());

                telefonoMap.put(
                                "complemento",
                                telefono.getComplemento());

                telefonoMap.put(
                                "tipo",
                                telefono.getTipoTelefono() != null
                                                ? telefono.getTipoTelefono()
                                                                .getNombreTipo()
                                                : null);

                return telefonoMap;
        }

        private Map<String, Object> mapPedidoProveedor(
                        PedidoCompra pedido) {

                Map<String, Object> pedidoMap = new LinkedHashMap<>();

                pedidoMap.put(
                                "idPedido",
                                pedido.getId());

                pedidoMap.put(
                                "fechaPedido",
                                pedido.getFechaPedido());

                pedidoMap.put(
                                "totalPedido",
                                pedido.getTotalPedido());

                pedidoMap.put(
                                "observacion",
                                pedido.getObservacion());

                pedidoMap.put(
                                "estado",
                                pedido.getEstadoPedido() != null
                                                ? pedido.getEstadoPedido()
                                                                .getNombreEstado()
                                                : null);

                pedidoMap.put(
                                "detalles",
                                pedido.getDetallePedido() != null
                                                ? pedido.getDetallePedido()
                                                                .stream()
                                                                .map(this::mapDetallePedidoProveedor)
                                                                .toList()
                                                : List.of());

                return pedidoMap;
        }

        private Map<String, Object> mapDetallePedidoProveedor(
                        DetallePedido detalle) {

                Map<String, Object> detalleMap = new LinkedHashMap<>();

                detalleMap.put(
                                "idDetalle",
                                detalle.getId());

                detalleMap.put(
                                "cantidad",
                                detalle.getCantidad());

                detalleMap.put(
                                "precioUnitario",
                                detalle.getPrecioUnitario());

                detalleMap.put(
                                "subtotal",
                                detalle.getSubtotal());

                if (detalle.getProducto() != null) {

                        Producto producto = detalle.getProducto();

                        Map<String, Object> productoMap = new LinkedHashMap<>();

                        productoMap.put(
                                        "idProducto",
                                        producto.getId());

                        productoMap.put(
                                        "nombreProducto",
                                        producto.getNombreProducto());

                        productoMap.put(
                                        "stockActual",
                                        producto.getStock());

                        productoMap.put(
                                        "stockMinimo",
                                        producto.getStockMinimo());

                        productoMap.put(
                                        "stockMaximo",
                                        producto.getStockMaximo());

                        productoMap.put(
                                        "lote",
                                        producto.getLote());

                        productoMap.put(
                                        "fechaExpiracion",
                                        producto.getFechaExpiracion());

                        productoMap.put(
                                        "categoria",
                                        producto.getCategoria() != null
                                                        ? producto.getCategoria()
                                                                        .getNombre()
                                                        : null);

                        detalleMap.put(
                                        "producto",
                                        productoMap);
                }

                return detalleMap;
        }

        // ===============================================================================================

        // REPORTE GENERAL
        // ===============================================================================================

        private Map<String, Object> generarReporteGeneral() {

                Map<String, Object> contenido = new LinkedHashMap<>();

                /*
                 * ==========================
                 * RESUMEN GENERAL
                 * ==========================
                 */

                Map<String, Object> resumenGeneral = new LinkedHashMap<>();

                resumenGeneral.put("totalProductos", productoDAO.count());
                resumenGeneral.put("totalClientes", clienteDAO.count());
                resumenGeneral.put("totalProveedores", proveedorDAO.count());
                resumenGeneral.put("totalVentas", ventaRegistroDAO.count());
                resumenGeneral.put("totalPedidos", pedidoCompraDAO.count());
                resumenGeneral.put("totalAlertas", alertaInvDAO.count());

                contenido.put("resumenGeneral", resumenGeneral);

                /*
                 * ==========================
                 * INVENTARIO
                 * ==========================
                 */

                List<Producto> productos = productoDAO.findAll();

                Map<String, Object> inventario = new LinkedHashMap<>();

                inventario.put(
                                "productosSinStock",
                                productos.stream()
                                                .filter(p -> p.getStock() != null
                                                                && p.getStock() == 0)
                                                .count());

                inventario.put(
                                "productosStockBajo",
                                productos.stream()
                                                .filter(p -> p.getStock() != null
                                                                && p.getStockMinimo() != null
                                                                && p.getStock() <= p.getStockMinimo())
                                                .count());

                inventario.put(
                                "productosStockNormal",
                                productos.stream()
                                                .filter(p -> p.getStock() != null
                                                                && p.getStockMinimo() != null
                                                                && p.getStockMaximo() != null
                                                                && p.getStock() > p.getStockMinimo()
                                                                && p.getStock() < p.getStockMaximo())
                                                .count());

                inventario.put(
                                "productosSobreStock",
                                productos.stream()
                                                .filter(p -> p.getStock() != null
                                                                && p.getStockMaximo() != null
                                                                && p.getStock() > p.getStockMaximo())
                                                .count());

                contenido.put("inventario", inventario);

                /*
                 * ==========================
                 * VENTAS
                 * ==========================
                 */

                List<VentaRegistro> ventas = ventaRegistroDAO.findAll();

                Map<String, Object> ventasMap = new LinkedHashMap<>();

                ventasMap.put(
                                "cantidadVentas",
                                ventas.size());

                ventasMap.put(
                                "valorTotalVentas",
                                ventas.stream()
                                                .map(VentaRegistro::getTotalVenta)
                                                .filter(Objects::nonNull)
                                                .reduce(BigDecimal.ZERO, BigDecimal::add));

                ventasMap.put(
                                "clientesQueHanComprado",
                                ventas.stream()
                                                .filter(v -> v.getCliente() != null)
                                                .map(v -> v.getCliente().getId())
                                                .distinct()
                                                .count());

                contenido.put("ventas", ventasMap);

                /*
                 * ==========================
                 * COMPRAS / PEDIDOS
                 * ==========================
                 */

                List<PedidoCompra> pedidos = pedidoCompraDAO.findAll();

                Map<String, Object> comprasMap = new LinkedHashMap<>();

                comprasMap.put(
                                "cantidadPedidos",
                                pedidos.size());

                comprasMap.put(
                                "valorTotalPedidos",
                                pedidos.stream()
                                                .map(PedidoCompra::getTotalPedido)
                                                .filter(Objects::nonNull)
                                                .reduce(BigDecimal.ZERO, BigDecimal::add));

                comprasMap.put(
                                "proveedoresConPedidos",
                                pedidos.stream()
                                                .filter(p -> p.getProveedor() != null)
                                                .map(p -> p.getProveedor().getId())
                                                .distinct()
                                                .count());

                contenido.put("compras", comprasMap);

                /*
                 * ==========================
                 * CLIENTES
                 * ==========================
                 */

                Map<String, Object> clientesMap = new LinkedHashMap<>();

                clientesMap.put(
                                "clientesRegistrados",
                                clienteDAO.count());

                clientesMap.put(
                                "clientesConCompras",
                                ventas.stream()
                                                .filter(v -> v.getCliente() != null)
                                                .map(v -> v.getCliente().getId())
                                                .distinct()
                                                .count());

                clientesMap.put(
                                "clientesSinCompras",
                                clienteDAO.count()
                                                - ventas.stream()
                                                                .filter(v -> v.getCliente() != null)
                                                                .map(v -> v.getCliente().getId())
                                                                .distinct()
                                                                .count());

                contenido.put("clientes", clientesMap);

                /*
                 * ==========================
                 * PROVEEDORES
                 * ==========================
                 */

                Map<String, Object> proveedoresMap = new LinkedHashMap<>();

                proveedoresMap.put(
                                "proveedoresRegistrados",
                                proveedorDAO.count());

                proveedoresMap.put(
                                "proveedoresConPedidos",
                                pedidos.stream()
                                                .filter(p -> p.getProveedor() != null)
                                                .map(p -> p.getProveedor().getId())
                                                .distinct()
                                                .count());

                proveedoresMap.put(
                                "proveedoresSinPedidos",
                                proveedorDAO.count()
                                                - pedidos.stream()
                                                                .filter(p -> p.getProveedor() != null)
                                                                .map(p -> p.getProveedor().getId())
                                                                .distinct()
                                                                .count());

                contenido.put("proveedores", proveedoresMap);

                /*
                 * ==========================
                 * ALERTAS
                 * ==========================
                 */

                Map<String, Object> alertasMap = new LinkedHashMap<>();

                alertasMap.put(
                                "totalAlertas",
                                alertaInvDAO.count());

                contenido.put("alertas", alertasMap);

                /*
                 * ==========================
                 * VENCIMIENTOS
                 * ==========================
                 */

                contenido.put(
                                "vencimientos",
                                generarReporteVencimientos());

                return contenido;
        }

        @Override
        @Transactional(readOnly = true)
        public ReporteInvResponseDTO obtenerPorId(Long id) {
                if (id == null)
                        throw new IllegalArgumentException("El ID no puede ser nulo");
                return reporteInvDAO.findById(id)
                                .map(reporteInvMapper::toResponseDTO)
                                .orElseThrow(() -> new RuntimeException("Reporte con ID " + id + " no encontrado"));
        }

        @Override
        @Transactional(readOnly = true)
        public List<ReporteInvResponseDTO> listar() {
                return reporteInvDAO.findAll().stream()
                                .map(reporteInvMapper::toResponseDTO)
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public List<ReporteInvResponseDTO> listarPorUsuario(Long idUsuario) {
                if (idUsuario == null)
                        throw new IllegalArgumentException("El ID no puede ser nulo");
                return reporteInvDAO.findByUsuarioId(idUsuario).stream()
                                .map(reporteInvMapper::toResponseDTO)
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public List<ReporteInvResponseDTO> listarPorTipo(String tipoReporte) {
                if (tipoReporte == null)
                        throw new IllegalArgumentException("El tipo no puede ser nulo");
                return reporteInvDAO.findByTipoReporte(tipoReporte).stream()
                                .map(reporteInvMapper::toResponseDTO)
                                .toList();
        }

}
