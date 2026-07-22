package com.sena.meciccolombia.mediccolombia.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.meciccolombia.mediccolombia.component.ProyeccionesMapper;
import com.sena.meciccolombia.mediccolombia.dao.DetalleProveedorProductoDAO;
import com.sena.meciccolombia.mediccolombia.dao.DetalleVentaDAO;
import com.sena.meciccolombia.mediccolombia.dao.DetallePedidoDAO;
import com.sena.meciccolombia.mediccolombia.dao.MetodoProyeccionDAO;
import com.sena.meciccolombia.mediccolombia.dao.MovimientoProdDAO;
import com.sena.meciccolombia.mediccolombia.dao.PedidoCompraDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProyeccionesDAO;
import com.sena.meciccolombia.mediccolombia.dao.TipoProyeccionDAO;
import com.sena.meciccolombia.mediccolombia.dao.VentaRegistroDAO;
import com.sena.meciccolombia.mediccolombia.domain.DetalleVenta;
import com.sena.meciccolombia.mediccolombia.domain.DetallePedido;
import com.sena.meciccolombia.mediccolombia.domain.MetodoProyeccion;
import com.sena.meciccolombia.mediccolombia.domain.MovimientoProd;
import com.sena.meciccolombia.mediccolombia.domain.Producto;
import com.sena.meciccolombia.mediccolombia.domain.Proyecciones;
import com.sena.meciccolombia.mediccolombia.domain.TipoProyeccion;
import com.sena.meciccolombia.mediccolombia.domain.VentaRegistro;
import com.sena.meciccolombia.mediccolombia.service.ConfiguracionSistemaService;
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

        private final ConfiguracionSistemaService configuracionService;

        private final TipoProyeccionDAO tipoProyeccionDAO;
        private final MetodoProyeccionDAO metodoProyeccionDAO;
        private final MovimientoProdDAO movimientoProdDAO;
        private final DetallePedidoDAO detallePedidoDAO;

        private static final Long METODO_SUMA = 2L;
        private static final Long METODO_CONTEO = 3L;

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

        // Consultas en tiempo real

        @Override
        @Transactional(readOnly = true)
        public List<ProyeccionesResponseDTO> consultarProveedoresMasFiables() {
                return pedidoCompraDAO.findByEstadoPedidoId(6L).stream()
                                .collect(Collectors.groupingBy(p -> p.getProveedor(), Collectors.counting()))
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
                return detalleProveedorProductoDAO.findAll().stream()
                                .map(detalle -> ProyeccionesResponseDTO.builder()
                                                .referenciaTipo("PRECIO_MERCADO")
                                                .resultadoProyeccion("Precio: $" + detalle.getPrecioUnitario())
                                                .nombreProducto(detalle.getProducto().getNombreProducto())
                                                .nombreCategoria(detalle.getProveedor().getNombreProv())
                                                .build())
                                .toList();
        }

        // GENERADAS MANUALMENTE O CON EL SHCEDULERS
        @Override
        @Transactional
        public void generarManual(Long idTipoProyeccion, LocalDateTime inicio, LocalDateTime fin) {
                if (idTipoProyeccion == null || inicio == null || fin == null)
                        throw new IllegalArgumentException("Tipo, fecha inicio y fecha fin son obligatorios");
                if (fin.isBefore(inicio))
                        throw new IllegalArgumentException("La fecha fin no puede ser anterior a la fecha inicio");
                if (idTipoProyeccion < 1 || idTipoProyeccion > 4)
                        throw new IllegalArgumentException("Tipo de proyeccion no permitido.");

                TipoProyeccion tipo = tipoProyeccionDAO.findById(idTipoProyeccion)
                                .orElseThrow(() -> new RuntimeException("Tipo de proyección no encontrado"));

                switch (idTipoProyeccion.intValue()) {
                        case 1 -> {
                                MetodoProyeccion metodo = metodoProyeccionDAO.findById(METODO_SUMA).orElseThrow();
                                generarMasVendidos(tipo, metodo, inicio, fin);
                        }
                        case 2 -> {
                                MetodoProyeccion metodo = metodoProyeccionDAO.findById(METODO_SUMA).orElseThrow();
                                generarMenosVendidos(tipo, metodo, inicio, fin);
                        }
                        case 3 -> {
                                MetodoProyeccion metodo = metodoProyeccionDAO.findById(METODO_CONTEO).orElseThrow();
                                generarRetirosVencimiento(tipo, metodo, inicio, fin);
                        }
                        case 4 -> {
                                MetodoProyeccion metodo = metodoProyeccionDAO.findById(METODO_SUMA).orElseThrow();
                                generarVentasCategoria(tipo, metodo, inicio, fin);
                        }
                }
        }

        private void generarMasVendidos(TipoProyeccion tipo, MetodoProyeccion metodo,
                        LocalDateTime inicio, LocalDateTime fin) {

                Map<Producto, Integer> ventasPorProducto = detalleVentaDAO.findAll().stream()
                                .filter(d -> d.getVenta().getFechaVenta().isAfter(inicio)
                                                && d.getVenta().getFechaVenta().isBefore(fin))
                                .collect(Collectors.groupingBy(
                                                DetalleVenta::getProducto,
                                                Collectors.summingInt(DetalleVenta::getCantidad)));

                ventasPorProducto.entrySet().stream()
                                .sorted(Map.Entry.<Producto, Integer>comparingByValue(Comparator.reverseOrder()))
                                .limit(5)
                                .forEach(entry -> {
                                        Producto producto = entry.getKey();
                                        int totalVendido = entry.getValue();
                                        int totalPedidoHistorico = detallePedidoDAO.findAll().stream()
                                                        .filter(dp -> dp.getProducto().getId().equals(producto.getId()))
                                                        .mapToInt(DetallePedido::getCantidad)
                                                        .sum();
                                        int porcentaje = leerConfigInt("porcentaje_estimacion_pedidos", 20);
                                        int pedidosEstimados = (int) (totalPedidoHistorico
                                                        * (1.0 + porcentaje / 100.0));

                                        proyeccionesDAO.save(Proyecciones.builder()
                                                        .tipoProyeccion(tipo)
                                                        .metodoProyeccion(metodo)
                                                        .producto(producto)
                                                        .resultadoProyeccion(totalVendido
                                                                        + " unidades vendidas en el período")
                                                        .referenciaTipo("PRODUCTO_MAS_VENDIDO")
                                                        .pedidosEstimados(pedidosEstimados)
                                                        .fechaGeneracion(LocalDateTime.now())
                                                        .fechaInicio(inicio)
                                                        .fechaFin(fin)
                                                        .unidadMedida("unidades")
                                                        .build());
                                });
        }

        private void generarMenosVendidos(TipoProyeccion tipo, MetodoProyeccion metodo,
                        LocalDateTime inicio, LocalDateTime fin) {

                detalleVentaDAO.findAll().stream()
                                .filter(d -> d.getVenta().getFechaVenta().isAfter(inicio)
                                                && d.getVenta().getFechaVenta().isBefore(fin))
                                .collect(Collectors.groupingBy(
                                                DetalleVenta::getProducto,
                                                Collectors.summingInt(DetalleVenta::getCantidad)))
                                .entrySet().stream()
                                .sorted(Map.Entry.comparingByValue())
                                .limit(5)
                                .forEach(entry -> proyeccionesDAO.save(Proyecciones.builder()
                                                .tipoProyeccion(tipo)
                                                .metodoProyeccion(metodo)
                                                .producto(entry.getKey())
                                                .resultadoProyeccion(
                                                                entry.getValue() + " unidades vendidas en el período")
                                                .referenciaTipo("PRODUCTO_MENOS_VENDIDO")
                                                .pedidosEstimados(0)
                                                .fechaGeneracion(LocalDateTime.now())
                                                .fechaInicio(inicio)
                                                .fechaFin(fin)
                                                .unidadMedida("unidades")
                                                .build()));
        }

        private void generarRetirosVencimiento(TipoProyeccion tipo, MetodoProyeccion metodo,
                        LocalDateTime inicio, LocalDateTime fin) {

                movimientoProdDAO.findByTipoMovimientoId(7L).stream()
                                .filter(m -> m.getFechaMovimiento().isAfter(inicio)
                                                && m.getFechaMovimiento().isBefore(fin))
                                .collect(Collectors.groupingBy(
                                                MovimientoProd::getProducto,
                                                Collectors.summingInt(MovimientoProd::getCantidad)))
                                .forEach((producto, totalRetirado) -> proyeccionesDAO.save(Proyecciones.builder()
                                                .tipoProyeccion(tipo)
                                                .metodoProyeccion(metodo)
                                                .producto(producto)
                                                .resultadoProyeccion(
                                                                totalRetirado + " unidades retiradas por vencimiento")
                                                .referenciaTipo("RETIRO_VENCIMIENTO")
                                                .pedidosEstimados(0)
                                                .fechaGeneracion(LocalDateTime.now())
                                                .fechaInicio(inicio)
                                                .fechaFin(fin)
                                                .unidadMedida("unidades")
                                                .build()));
        }

        private void generarVentasCategoria(TipoProyeccion tipo, MetodoProyeccion metodo,
                        LocalDateTime inicio, LocalDateTime fin) {

                detalleVentaDAO.findAll().stream()
                                .filter(d -> d.getVenta().getFechaVenta().isAfter(inicio)
                                                && d.getVenta().getFechaVenta().isBefore(fin))
                                .collect(Collectors.groupingBy(
                                                d -> d.getProducto().getCategoria(),
                                                Collectors.summingInt(DetalleVenta::getCantidad)))
                                .forEach((categoria, totalVendido) -> proyeccionesDAO.save(Proyecciones.builder()
                                                .tipoProyeccion(tipo)
                                                .metodoProyeccion(metodo)
                                                .categoria(categoria)
                                                .resultadoProyeccion(
                                                                totalVendido + " unidades vendidas en la categoría")
                                                .referenciaTipo("VENTA_CATEGORIA")
                                                .pedidosEstimados(0)
                                                .fechaGeneracion(LocalDateTime.now())
                                                .fechaInicio(inicio)
                                                .fechaFin(fin)
                                                .unidadMedida("unidades")
                                                .build()));
        }

        private int leerConfigInt(String clave, int fallback) {
                try {
                        String valor = configuracionService.obtenerValor(clave);
                        return (valor != null && !valor.isBlank()) ? Integer.parseInt(valor.trim()) : fallback;
                } catch (NumberFormatException e) {
                        return fallback;
                }
        }
}