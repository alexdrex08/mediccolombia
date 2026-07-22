package com.sena.meciccolombia.mediccolombia.web.controller.view;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.meciccolombia.mediccolombia.security.MyUserDetails;
import com.sena.meciccolombia.mediccolombia.service.AlertaInvService;
import com.sena.meciccolombia.mediccolombia.service.ConfiguracionSistemaService;
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

    private final ProductoService productoService;
    private final ProveedorService proveedorService;
    private final ConfiguracionSistemaService configuracionService;
    private final MovimientoProdService movimientoProdService;
    private final AlertaInvService alertaInvService;

    @GetMapping
    public String dashboard(Model modelo, Authentication auth) {
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        int totalProductos = productoService.listarProductos().size();
        int productosStockBajo = alertaInvService.listarPorTipoYEstado("STOCK_BAJO", false).size();
        int productosVencidos = alertaInvService.listarPorTipoYEstado("PRODUCTO_VENCIDO", false).size();
        int totalProveedores = proveedorService.listar().size();

        modelo.addAttribute("vistaActiva", "dashboard");
        modelo.addAttribute("esAdmin", "ADMIN".equals(user.getRol()));

        modelo.addAttribute("fechaActualizacion", LocalDateTime.now());
        modelo.addAttribute("totalProductos", totalProductos);
        modelo.addAttribute("productosStockBajo", productosStockBajo);
        modelo.addAttribute("productosVencidos", productosVencidos);
        modelo.addAttribute("totalProveedores", totalProveedores);
        int limite = leerConfigInt("filas_dashboard", 5);
        modelo.addAttribute("ultimosMovimientos", obtenerUltimosMovimientos(limite));

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

    private List<MovimientoDashboardItem> obtenerUltimosMovimientos(int limite) {

        Stream<MovimientoDashboardItem> entradas = movimientoProdService.listarPorSigno(1).stream()
                .map(m -> new MovimientoDashboardItem(m, true));

        Stream<MovimientoDashboardItem> salidas = movimientoProdService.listarPorSigno(-1).stream()
                .map(m -> new MovimientoDashboardItem(m, false));

        return Stream.concat(entradas, salidas)
                .sorted(Comparator.comparing(
                        (MovimientoDashboardItem item) -> item.getMovimiento().getFechaMovimiento())
                        .reversed())
                .limit(limite)
                .toList();
    }

    @Getter
    @AllArgsConstructor
    public static class MovimientoDashboardItem {
        private final MovimientoProdResponseDTO movimiento;
        private final boolean entrada;
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