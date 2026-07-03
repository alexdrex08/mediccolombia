package com.sena.meciccolombia.mediccolombia.web.controller.view;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.meciccolombia.mediccolombia.service.AlertaInvService;
import com.sena.meciccolombia.mediccolombia.service.MovimientoProdService;
import com.sena.meciccolombia.mediccolombia.service.ProductoService;
import com.sena.meciccolombia.mediccolombia.service.ProveedorService;
import com.sena.meciccolombia.mediccolombia.web.dto.response.MovimientoProdResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardViewController {

    private static final int LIMITE_ULTIMOS_MOVIMIENTOS = 5;

    private final ProductoService productoService;
    private final ProveedorService proveedorService;
    private final MovimientoProdService movimientoProdService;
    private final AlertaInvService alertaInvService;

    @GetMapping
    public String dashboard(Model modelo) {

        int totalProductos = productoService.listarProductos().size();
        int productosStockBajo = alertaInvService.listarPorTipo("STOCK_BAJO").size();
        int productosVencidos = alertaInvService.listarPorTipo("PRODUCTO_VENCIDO").size();
        int totalProveedores = proveedorService.listar().size();

        modelo.addAttribute("vistaActiva", "dashboard");
        modelo.addAttribute("fechaActualizacion", LocalDateTime.now());
        modelo.addAttribute("totalProductos", totalProductos);
        modelo.addAttribute("productosStockBajo", productosStockBajo);
        modelo.addAttribute("productosVencidos", productosVencidos);
        modelo.addAttribute("totalProveedores", totalProveedores);
        modelo.addAttribute("ultimosMovimientos", obtenerUltimosMovimientos());

        if (productosVencidos > 0) {
            modelo.addAttribute("recomendacion",
                    "Hay " + productosVencidos + " producto(s) vencido(s) pendientes de retiro. "
                            + "Revisa el módulo de Alertas antes de continuar.");
            modelo.addAttribute("recomendacionEstilo", "danger");
        } else if (productosStockBajo > 0) {
            modelo.addAttribute("recomendacion",
                    "Hay " + productosStockBajo + " producto(s) con stock bajo. "
                            + "Se sugiere generar un pedido de compra pronto.");
            modelo.addAttribute("recomendacionEstilo", "warning");
        } else {
            modelo.addAttribute("recomendacion",
                    "El inventario se encuentra estable: no hay productos vencidos ni con stock crítico por el momento.");
            modelo.addAttribute("recomendacionEstilo", "success");
        }

        return "dashboard/dashboard";
    }

    private List<MovimientoDashboardItem> obtenerUltimosMovimientos() {

        Stream<MovimientoDashboardItem> entradas = movimientoProdService.listarPorSigno(1).stream()
                .map(m -> new MovimientoDashboardItem(m, true));

        Stream<MovimientoDashboardItem> salidas = movimientoProdService.listarPorSigno(-1).stream()
                .map(m -> new MovimientoDashboardItem(m, false));

        return Stream.concat(entradas, salidas)
                .sorted(Comparator.comparing(
                        (MovimientoDashboardItem item) -> item.getMovimiento().getFechaMovimiento())
                        .reversed())
                .limit(LIMITE_ULTIMOS_MOVIMIENTOS)
                .toList();
    }

    @Getter
    @AllArgsConstructor
    public static class MovimientoDashboardItem {
        private final MovimientoProdResponseDTO movimiento;
        private final boolean entrada;
    }
}