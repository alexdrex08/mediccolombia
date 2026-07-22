package com.sena.meciccolombia.mediccolombia.web.controller.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.meciccolombia.mediccolombia.security.MyUserDetails;
import com.sena.meciccolombia.mediccolombia.service.IVentaRegistroService;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProductoMasVendidoResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TotalVendidosDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.VentaRegistroResponseDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaViewController {

    private final IVentaRegistroService ventaRegistroService;

    // ─────────────────────────────────────────────
    // GET /ventas/nueva  →  vista POS
    // ─────────────────────────────────────────────
    @GetMapping("/nueva-venta")
    public String nuevaVenta(Model model, Authentication auth) {

        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        model.addAttribute("vistaActiva", "ventas-nueva");
        model.addAttribute("idUsuarioActual", user.getId());
        return "ventas/nueva-venta";
    }

    // ─────────────────────────────────────────────
    // GET /ventas  →  historial de ventas
    // ─────────────────────────────────────────────
    @GetMapping
    public String historialVentas(Model model, Authentication auth) {
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        // Lista completa de ventas
        List<VentaRegistroResponseDTO> ventas = ventaRegistroService.listarVentas();
        model.addAttribute("ventas", ventas);

        // Total vendido hoy (inicio del día hasta ahora)
        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        LocalDateTime finDia = LocalDate.now().atTime(LocalTime.MAX);
        TotalVendidosDTO totales = ventaRegistroService.obtenerTotalVendidoEnElDia(inicioDia, finDia);
        model.addAttribute("totalHoy", totales.getTotalVenta());
        model.addAttribute("cantidadVentasHoy", totales.getProductos().size());

        // Producto más vendido (primero de la lista ordenada desc)
        List<ProductoMasVendidoResponseDTO> masVendidos = ventaRegistroService.obtenerProductosMasVendidos();
        String nombreMasVendido = masVendidos.isEmpty()
                ? "Sin datos aún"
                : masVendidos.get(0).getNombreProducto()
                        + " (" + masVendidos.get(0).getTotalUnidadesVendidas() + " ud)";
        model.addAttribute("masVendido", nombreMasVendido);

        model.addAttribute("esAdmin", "ADMIN".equals(user.getRol()));
        model.addAttribute("vistaActiva", "ventas-lista");
        return "ventas/historial-ventas";
    }

    // ─────────────────────────────────────────────
    // GET /ventas/{id}  →  detalle de una venta
    // ─────────────────────────────────────────────
    @GetMapping("/{id}")
    public String detalleVenta(@PathVariable Long id, Model model) {
        VentaRegistroResponseDTO venta = ventaRegistroService.obtenerVentaPorId(id);
        model.addAttribute("venta", venta);
        model.addAttribute("vistaActiva", "ventas-lista");
        return "ventas/detalle-venta";
    }
}