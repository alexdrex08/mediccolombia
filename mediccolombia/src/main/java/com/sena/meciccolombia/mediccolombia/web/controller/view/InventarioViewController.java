package com.sena.meciccolombia.mediccolombia.web.controller.view;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sena.meciccolombia.mediccolombia.security.MyUserDetails;
import com.sena.meciccolombia.mediccolombia.service.AlertaInvService;
import com.sena.meciccolombia.mediccolombia.service.CategoriaService;
import com.sena.meciccolombia.mediccolombia.service.MovimientoProdService;
import com.sena.meciccolombia.mediccolombia.service.ProductoService;
import com.sena.meciccolombia.mediccolombia.service.ReporteInvService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.ProductoCreateRequestDto;
import com.sena.meciccolombia.mediccolombia.web.dto.request.ProductoUpdateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.AlertaInvResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProductoDetalleDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProductoHistorialDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProductoResumenDTO;

import com.sena.meciccolombia.mediccolombia.dao.DetalleVentaDAO;
import com.sena.meciccolombia.mediccolombia.dao.DetallePedidoDAO;
import com.sena.meciccolombia.mediccolombia.domain.DetalleVenta;
import com.sena.meciccolombia.mediccolombia.domain.DetallePedido;

@Controller
@RequestMapping("/productos")
public class InventarioViewController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private AlertaInvService alertaInvService;

    @Autowired
    private ReporteInvService reporteInvService;

    @Autowired
    private MovimientoProdService movimientoProdService;

    @Autowired
    private DetalleVentaDAO detalleVentaDAO;

    @Autowired
    private DetallePedidoDAO detallePedidoDAO;

    // ─────────────────────────────────────────────────────────────
    // LISTADO DE PRODUCTOS
    // ─────────────────────────────────────────────────────────────

    @GetMapping
    public String verProductos(Model modelo, Authentication auth) {

        List<ProductoResumenDTO> productos = productoService.listarProductos();

        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        ProductoResumenDTO productoMenorStock = productos.stream()
                .min(Comparator.comparing(ProductoResumenDTO::getStock))
                .orElse(null);

        modelo.addAttribute("vistaActiva", "inventario-lista");
        modelo.addAttribute("fechaActualizacion", LocalDateTime.now());
        modelo.addAttribute("productos", productos);
        modelo.addAttribute("categorias", categoriaService.listarCategorias());
        modelo.addAttribute("totalAlertas", alertaInvService.listarIsResueltaFalse().size());
        modelo.addAttribute("alertasCriticas", alertaInvService.listarPorTipoYEstado("STOCK_BAJO", false).size());
        modelo.addAttribute("proximosAVencer", productoService.productosProximosAVencer(7).size());
        modelo.addAttribute("totalReportes", reporteInvService.listar().size());
        modelo.addAttribute("totalEntradas", movimientoProdService.listarPorSigno(1).size());
        modelo.addAttribute("totalSalidas", movimientoProdService.listarPorSigno(-1).size());
        modelo.addAttribute("productoMenorStock", productoMenorStock);
        modelo.addAttribute("esAdmin", "ADMIN".equals(user.getRol()));

        return "inventario/productos";
    }

    // ─────────────────────────────────────────────────────────────
    // DETALLE / EDITAR
    // ─────────────────────────────────────────────────────────────

    // ─── detalleProducto — reemplaza el método completo ─────────────────
    @GetMapping("/ver/{idProducto}")
    public String detalleProducto(@PathVariable Long idProducto, Model modelo, Authentication auth) {

        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        ProductoDetalleDTO producto = productoService.obtenerPorId(idProducto);
        ProductoHistorialDTO historial = productoService.productoHistorial(idProducto);
        List<AlertaInvResponseDTO> alertas = alertaInvService.listarPorProducto(idProducto);

        // Ventas y pedidos donde participó este producto
        List<DetalleVenta> ventasDelProducto = detalleVentaDAO.findByProductoId(idProducto);
        List<DetallePedido> pedidosDelProducto = detallePedidoDAO.findByProductoId(idProducto);

        // IDs de movimientos de entrada para pintar badges
        Set<Long> idsEntrada = movimientoProdService.listarPorSigno(1).stream()
                .map(m -> m.getId())
                .collect(Collectors.toSet());

        long totalEntradas = historial.getMovimientos().stream()
                .filter(m -> idsEntrada.contains(m.getId())).count();
        long totalSalidas = historial.getMovimientos().size() - totalEntradas;

        modelo.addAttribute("vistaActiva", "inventario-lista");
        modelo.addAttribute("producto", producto);
        modelo.addAttribute("movimientos", historial.getMovimientos());
        modelo.addAttribute("alertas", alertas);
        modelo.addAttribute("ventas", ventasDelProducto);
        modelo.addAttribute("pedidos", pedidosDelProducto);
        modelo.addAttribute("idsEntrada", idsEntrada);
        modelo.addAttribute("totalMovimientos", historial.getMovimientos().size());
        modelo.addAttribute("totalEntradas", totalEntradas);
        modelo.addAttribute("totalSalidas", totalSalidas);
        modelo.addAttribute("totalAlertas", alertas.size());
        modelo.addAttribute("totalVentas", ventasDelProducto.size());
        modelo.addAttribute("totalPedidos", pedidosDelProducto.size());
        modelo.addAttribute("esAdmin", "ADMIN".equals(user.getRol()));

        return "inventario/producto-detalle";
    }

    // ─── editarProducto GET  ─────────────
    @GetMapping("/editar/{idProducto}")
    public String editarProducto(@PathVariable Long idProducto, Model modelo, Authentication auth) {
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (!"ADMIN".equals(user.getRol())) {
            return "redirect:/productos/ver/" + idProducto;
        }
        modelo.addAttribute("producto", productoService.obtenerPorId(idProducto));
        modelo.addAttribute("categorias", categoriaService.listarCategorias());
        modelo.addAttribute("vistaActiva", "inventario-lista");
        return "inventario/producto-editar";
    }

    @PostMapping("/editar/{idProducto}")
    public String guardarEdicion(
            @PathVariable Long idProducto,
            @ModelAttribute ProductoUpdateRequestDTO dto,
            Authentication auth,
            RedirectAttributes redirectAttributes) {

        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (!"ADMIN".equals(user.getRol())) {
            redirectAttributes.addFlashAttribute("error", "No tienes permisos para editar productos.");
            return "redirect:/productos";
        }

        if (dto.getStockMinimo() >= dto.getStockMaximo()) {
            redirectAttributes.addFlashAttribute("error",
                    "El stock mínimo debe ser menor que el stock máximo.");
            return "redirect:/productos/editar/" + idProducto;
        }
        if (dto.getFechaExpiracion() == null || dto.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("error",
                    "La fecha de expiración debe ser una fecha futura.");
            return "redirect:/productos/editar/" + idProducto;
        }

        try {
            productoService.actualizarProducto(idProducto, dto);
            redirectAttributes.addFlashAttribute("mensaje",
                    "Producto actualizado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al actualizar: " + e.getMessage());
            return "redirect:/productos/editar/" + idProducto;
        }

        return "redirect:/productos/ver/" + idProducto;
    }

    // ─────────────────────────────────────────────────────────────
    // ELIMINAR
    // ─────────────────────────────────────────────────────────────

    @PostMapping("/eliminar/{idProducto}")
    public String eliminarProducto(@PathVariable Long idProducto,
            RedirectAttributes redirectAttributes) {
        try {
            productoService.eliminarProducto(idProducto);
            redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "No se pudo eliminar el producto: " + e.getMessage());
        }
        return "redirect:/productos";
    }

    // ─────────────────────────────────────────────────────────────
    // ALERTAS
    // ─────────────────────────────────────────────────────────────

    @GetMapping("/alertas")
    public String verAlertas(Model modelo, Authentication auth) {

        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        List<AlertaInvResponseDTO> alertasStockBajo = alertaInvService.listarPorTipoYEstado("STOCK_BAJO", false);
        List<AlertaInvResponseDTO> alertasProximasVencer = alertaInvService.listarPorTipoYEstado("PROXIMO_A_VENCER", false);
        List<AlertaInvResponseDTO> alertasVencidas = alertaInvService.listarPorTipoYEstado("PRODUCTO_VENCIDO", false);

        modelo.addAttribute("alertasStockBajo", alertasStockBajo);
        modelo.addAttribute("alertasProximasVencer", alertasProximasVencer);
        modelo.addAttribute("alertasVencidas", alertasVencidas);
        modelo.addAttribute("totalStockBajo", alertasStockBajo.size());
        modelo.addAttribute("totalProximasVencer", alertasProximasVencer.size());
        modelo.addAttribute("totalVencidas", alertasVencidas.size());
        modelo.addAttribute("totalAlertas",
                alertasStockBajo.size() + alertasProximasVencer.size() + alertasVencidas.size());
        modelo.addAttribute("esAdmin", "ADMIN".equals(user.getRol()));
        modelo.addAttribute("vistaActiva", "alertas");

        List<AlertaInvResponseDTO> alertasResueltas = alertaInvService.listarIsResueltaTrue();
        modelo.addAttribute("alertasResueltas",alertasResueltas);

        return "inventario/alertas";
    }

    @PostMapping("/alertas/{id}/eliminar")
    public String eliminarAlerta(@PathVariable Long id,
            Authentication auth,
            RedirectAttributes redirectAttributes) {

        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (!"ADMIN".equals(user.getRol())) {
            redirectAttributes.addFlashAttribute("error", "No tienes permisos para eliminar alertas.");
            return "redirect:/productos/alertas"; 
        }
        try {
            alertaInvService.resolverAlerta(id);
            redirectAttributes.addFlashAttribute("mensaje", "Alerta resuelta correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al eliminar la alerta: " + e.getMessage());
        }
        return "redirect:/productos/alertas";
    }

    // ─────────────────────────────────────────────────────────────
    // CREAR PRODUCTO
    // ─────────────────────────────────────────────────────────────

    @GetMapping("/nuevo")
    public String nuevoProducto(Model modelo, Authentication auth) {

        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        modelo.addAttribute("categorias", categoriaService.listarCategorias());
        modelo.addAttribute("nombreUsuario", user.getNombre());
        modelo.addAttribute("vistaActiva", "inventario-lista");

        return "inventario/crear-producto";
    }

    @PostMapping("/nuevo")
    public String crearProducto(
            @ModelAttribute ProductoCreateRequestDto dto,
            Authentication auth,
            RedirectAttributes redirectAttributes) {

        // Esto trae el usuario autenticado en el DTO
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        dto.setIdUsuario(user.getId());

        // Validaciones de negocio
        if (dto.getStock() < 0) {
            redirectAttributes.addFlashAttribute("error", "El stock no puede ser negativo.");
            return "redirect:/productos/nuevo";
        }
        if (dto.getStockMinimo() >= dto.getStockMaximo()) {
            redirectAttributes.addFlashAttribute("error",
                    "El stock mínimo debe ser menor que el stock máximo.");
            return "redirect:/productos/nuevo";
        }
        if (dto.getFechaExpiracion() == null || dto.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("error",
                    "La fecha de expiración debe ser una fecha futura.");
            return "redirect:/productos/nuevo";
        }

        try {
            productoService.crearProducto(dto);
            redirectAttributes.addFlashAttribute("mensaje",
                    "Producto \"" + dto.getNombreProducto() + "\" creado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al crear el producto: " + e.getMessage());
            return "redirect:/productos/nuevo";
        }

        return "redirect:/productos";
    }
}