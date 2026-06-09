package com.sena.meciccolombia.mediccolombia.scheduler;

import com.sena.meciccolombia.mediccolombia.dao.*;
import com.sena.meciccolombia.mediccolombia.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProyeccionesScheduler {

    private final ProyeccionesDAO proyeccionesDAO;
    private final DetalleVentaDAO detalleVentaDAO;
    private final MovimientoProdDAO movimientoProdDAO;
    private final TipoProyeccionDAO tipoProyeccionDAO;
    private final MetodoProyeccionDAO metodoProyeccionDAO;
    private final DetallePedidoDAO detallePedidoDAO;
    private final CategoriaDAO categoriaDAO;

    private static final Long TIPO_MAS_VENDIDOS = 1L;
    private static final Long TIPO_MENOS_VENDIDOS = 2L;
    private static final Long TIPO_RETIROS_VENCIMIENTO = 3L;
    private static final Long TIPO_VENTAS_CATEGORIA = 4L;

    private static final Long METODO_PROMEDIO = 1L;
    private static final Long METODO_SUMA = 2L;
    private static final Long METODO_CONTEO = 3L;

    @Scheduled(cron = "0 1 0 1 * *")
    @Transactional
    public void generarProyeccionesAutomaticas() {
        log.info("Iniciando generación automática de proyecciones: {}", LocalDateTime.now());

        LocalDateTime inicioMesAnterior = LocalDateTime.now().minusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0)
                .withSecond(0);
        LocalDateTime finMesAnterior = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
                .minusSeconds(1);

        TipoProyeccion tipoMasVendidos = tipoProyeccionDAO.findById(TIPO_MAS_VENDIDOS).orElseThrow();
        TipoProyeccion tipoMenosVendidos = tipoProyeccionDAO.findById(TIPO_MENOS_VENDIDOS).orElseThrow();
        TipoProyeccion tipoRetirosVencimiento = tipoProyeccionDAO.findById(TIPO_RETIROS_VENCIMIENTO).orElseThrow();
        TipoProyeccion tipoVentasCategoria = tipoProyeccionDAO.findById(TIPO_VENTAS_CATEGORIA).orElseThrow();

        MetodoProyeccion metodaSuma = metodoProyeccionDAO.findById(METODO_SUMA).orElseThrow();
        MetodoProyeccion metodoConteo = metodoProyeccionDAO.findById(METODO_CONTEO).orElseThrow();

        generarProductosMasVendidos(tipoMasVendidos, metodaSuma, inicioMesAnterior, finMesAnterior);
        generarProductosMenosVendidos(tipoMenosVendidos, metodaSuma, inicioMesAnterior, finMesAnterior);
        generarRetirosVencimiento(tipoRetirosVencimiento, metodoConteo, inicioMesAnterior, finMesAnterior);
        generarVentasPorCategoria(tipoVentasCategoria, metodaSuma, inicioMesAnterior, finMesAnterior);

        log.info("Proyecciones automáticas generadas exitosamente");
    }

    private void generarProductosMasVendidos(TipoProyeccion tipo, MetodoProyeccion metodo,
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
                    int pedidosEstimados = (int) (totalPedidoHistorico * 1.20);

                    Proyecciones proyeccion = Proyecciones.builder()
                            .tipoProyeccion(tipo)
                            .metodoProyeccion(metodo)
                            .producto(producto)
                            .resultadoProyeccion(totalVendido + " unidades vendidas en el período")
                            .referenciaTipo("PRODUCTO_MAS_VENDIDO")
                            .pedidosEstimados(pedidosEstimados)
                            .fechaGeneracion(LocalDateTime.now())
                            .fechaInicio(inicio)
                            .fechaFin(fin)
                            .unidadMedida("unidades")
                            .build();

                    proyeccionesDAO.save(proyeccion);
                    log.info("Proyección guardada - Producto: {}, Vendido: {}", producto.getNombreProducto(),
                            totalVendido);
                });
    }

    private void generarProductosMenosVendidos(TipoProyeccion tipo, MetodoProyeccion metodo,
            LocalDateTime inicio, LocalDateTime fin) {

        Map<Producto, Integer> ventasPorProducto = detalleVentaDAO.findAll().stream()
                .filter(d -> d.getVenta().getFechaVenta().isAfter(inicio)
                        && d.getVenta().getFechaVenta().isBefore(fin))
                .collect(Collectors.groupingBy(
                        DetalleVenta::getProducto,
                        Collectors.summingInt(DetalleVenta::getCantidad)));
        ventasPorProducto.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(5)
                .forEach(entry -> {
                    Producto producto = entry.getKey();
                    int totalVendido = entry.getValue();

                    Proyecciones proyeccion = Proyecciones.builder()
                            .tipoProyeccion(tipo)
                            .metodoProyeccion(metodo)
                            .producto(producto)
                            .resultadoProyeccion(totalVendido + " unidades vendidas en el período")
                            .referenciaTipo("PRODUCTO_MENOS_VENDIDO")
                            .pedidosEstimados(0)
                            .fechaGeneracion(LocalDateTime.now())
                            .fechaInicio(inicio)
                            .fechaFin(fin)
                            .unidadMedida("unidades")
                            .build();

                    proyeccionesDAO.save(proyeccion);
                });
    }

    private void generarRetirosVencimiento(TipoProyeccion tipo, MetodoProyeccion metodo,
            LocalDateTime inicio, LocalDateTime fin) {

        movimientoProdDAO.findByTipoMovimientoId(7L).stream()
                .filter(m -> m.getFechaMovimiento().isAfter(inicio)
                        && m.getFechaMovimiento().isBefore(fin))
                .collect(Collectors.groupingBy(
                        MovimientoProd::getProducto,
                        Collectors.summingInt(MovimientoProd::getCantidad)))
                .forEach((producto, totalRetirado) -> {
                    Proyecciones proyeccion = Proyecciones.builder()
                            .tipoProyeccion(tipo)
                            .metodoProyeccion(metodo)
                            .producto(producto)
                            .resultadoProyeccion(totalRetirado + " unidades retiradas por vencimiento")
                            .referenciaTipo("RETIRO_VENCIMIENTO")
                            .pedidosEstimados(0)
                            .fechaGeneracion(LocalDateTime.now())
                            .fechaInicio(inicio)
                            .fechaFin(fin)
                            .unidadMedida("unidades")
                            .build();

                    proyeccionesDAO.save(proyeccion);
                });
    }

    private void generarVentasPorCategoria(TipoProyeccion tipo, MetodoProyeccion metodo,
            LocalDateTime inicio, LocalDateTime fin) {
        detalleVentaDAO.findAll().stream()
                .filter(d -> d.getVenta().getFechaVenta().isAfter(inicio)
                        && d.getVenta().getFechaVenta().isBefore(fin))
                .collect(Collectors.groupingBy(
                        d -> d.getProducto().getCategoria(),
                        Collectors.summingInt(DetalleVenta::getCantidad)))
                .forEach((categoria, totalVendido) -> {
                    Proyecciones proyeccion = Proyecciones.builder()
                            .tipoProyeccion(tipo)
                            .metodoProyeccion(metodo)
                            .categoria(categoria)
                            .resultadoProyeccion(totalVendido + " unidades vendidas en la categoría")
                            .referenciaTipo("VENTA_CATEGORIA")
                            .pedidosEstimados(0)
                            .fechaGeneracion(LocalDateTime.now())
                            .fechaInicio(inicio)
                            .fechaFin(fin)
                            .unidadMedida("unidades")
                            .build();

                    proyeccionesDAO.save(proyeccion);
                });
    }

}
