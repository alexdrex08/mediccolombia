package com.sena.meciccolombia.mediccolombia.web.controller.view;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sena.meciccolombia.mediccolombia.dao.BarrioDireccionDAO;
import com.sena.meciccolombia.mediccolombia.dao.DetalleProveedorProductoDAO;
import com.sena.meciccolombia.mediccolombia.dao.PedidoCompraDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProductoDAO;
import com.sena.meciccolombia.mediccolombia.dao.TipoCorreoDAO;
import com.sena.meciccolombia.mediccolombia.dao.TipoDireccionDAO;
import com.sena.meciccolombia.mediccolombia.dao.TipoTelefonoDAO;
import com.sena.meciccolombia.mediccolombia.domain.DetalleProveedorProducto;
import com.sena.meciccolombia.mediccolombia.domain.PedidoCompra;
import com.sena.meciccolombia.mediccolombia.domain.Producto;
import com.sena.meciccolombia.mediccolombia.security.MyUserDetails;
import com.sena.meciccolombia.mediccolombia.service.IDetalleProveedorProductoService;
import com.sena.meciccolombia.mediccolombia.service.ProveedorService;
import com.sena.meciccolombia.mediccolombia.web.dto.response.PedidoCompraResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProveedorDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProveedorResponseDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/proveedores")
@RequiredArgsConstructor
public class ProveedorViewController {

    private final ProveedorService proveedorService;
    private final IDetalleProveedorProductoService detalleProveedorProductoService;
    private final PedidoCompraDAO pedidoCompraDAO;
    private final DetalleProveedorProductoDAO detalleProveedorProductoDAO;
    private final ProductoDAO productoDAO;
    private final TipoCorreoDAO tipoCorreoDAO;
    private final TipoTelefonoDAO tipoTelefonoDAO;
    private final TipoDireccionDAO tipoDireccionDAO;
    private final BarrioDireccionDAO barrioDireccionDAO;

    
    @GetMapping
    public String listarProveedores(Model model,
                                    Authentication auth
    ) {
        List<ProveedorResponseDTO> proveedores = proveedorService.listar();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        List<Map<String, Object>> proveedoresEnriquecidos = proveedores.stream()
                .map(prov -> {
                    Map<String, Object> datos = new java.util.LinkedHashMap<>();
                    datos.put("id", prov.getId());
                    datos.put("nombreProv", prov.getNombreProv());
                    datos.put("nit", prov.getNit());

                    // ── Categoría principal ──
                    List<DetalleProveedorProducto> detProds = detalleProveedorProductoDAO
                            .findByProveedorId(prov.getId());

                    String categoriaPrincipal = detProds.stream()
                            .map(d -> d.getProducto().getCategoria().getNombre())
                            .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                            .entrySet().stream()
                            .max(Map.Entry.comparingByValue())
                            .map(Map.Entry::getKey)
                            .orElse("Sin categoría");

                    datos.put("categoriaPrincipal", categoriaPrincipal);
                    datos.put("totalProductos", detProds.size());

                    // ── Último pedido ──
                    List<PedidoCompra> pedidos = pedidoCompraDAO.findByProveedorId(prov.getId());

                    PedidoCompra ultimoPedido = pedidos.stream()
                            .filter(p -> p.getFechaPedido() != null)
                            .max(Comparator.comparing(PedidoCompra::getFechaPedido))
                            .orElse(null);

                    if (ultimoPedido != null) {
                        datos.put("ultimoPedidoId", ultimoPedido.getId());
                        datos.put("ultimoPedidoFecha", ultimoPedido.getFechaPedido());
                        datos.put("ultimoPedidoEstado", ultimoPedido.getEstadoPedido().getNombreEstado());
                        datos.put("ultimoPedidoTotal", ultimoPedido.getTotalPedido());
                    } else {
                        datos.put("ultimoPedidoId", null);
                        datos.put("ultimoPedidoFecha", null);
                        datos.put("ultimoPedidoEstado", null);
                        datos.put("ultimoPedidoTotal", null);
                    }

                    return datos;
                })
                .toList();

        model.addAttribute("proveedores", proveedoresEnriquecidos);
        model.addAttribute("fechaActualizacion", LocalDateTime.now());
        model.addAttribute("totalProveedores", proveedores.size());
        model.addAttribute("vistaActiva", "proveedores");
        model.addAttribute("vistaActiva", "proveedores-lista");
        model.addAttribute("esAdmin", "ADMIN".equals(user.getRol()));

        return "proveedores/lista-proveedores";
    }

    // ─────────────────────────────────────────────
    // GET /proveedores/nuevo
    // ─────────────────────────────────────────────
    @GetMapping("/nuevo")
    public String nuevoProveedor(Model model) {
        model.addAttribute("modoEdicion", false);
        model.addAttribute("vistaActiva", "proveedores-nuevo");
        return "proveedores/form-proveedor";
    }

    // ─────────────────────────────────────────────
    // GET /proveedores/{id}/editar
    // ─────────────────────────────────────────────
    @GetMapping("/{id}/editar")
    public String editarProveedor(@PathVariable Long id, Model model) {
        ProveedorResponseDTO proveedor = proveedorService.buscarPorId(id);
        model.addAttribute("proveedor", proveedor);
        model.addAttribute("modoEdicion", true);
        model.addAttribute("vistaActiva", "proveedores-lista");
        return "proveedores/form-proveedor";
    }

    // ─────────────────────────────────────────────
    // GET /proveedores/{id} → detalle
    @GetMapping("/{id}")
    public String verProveedor(@PathVariable Long id, Model model) {
        ProveedorDetalleResponseDTO proveedor = proveedorService.obtenerDetalles(id);
        model.addAttribute("proveedor", proveedor);

        List<DetalleProveedorProducto> productosAsignados = detalleProveedorProductoDAO.findByProveedorId(id);

        model.addAttribute("productosAsignados",
                detalleProveedorProductoService.listarPorProveedor(id));

        // IDs de productos ya asignados para excluirlos
        Set<Long> idsAsignados = productosAsignados.stream()
                .map(d -> d.getProducto().getId())
                .collect(Collectors.toSet());

        // Productos disponibles = todos menos los ya asignados
        List<Producto> productosDisponibles = productoDAO.findAll().stream()
                .filter(p -> !idsAsignados.contains(p.getId()))
                .toList();

        model.addAttribute("productosDisponibles", productosDisponibles);

        model.addAttribute("tiposCorreo", tipoCorreoDAO.findAll());
        model.addAttribute("tiposTelefono", tipoTelefonoDAO.findAll());
        model.addAttribute("tiposDireccion", tipoDireccionDAO.findAll());
        model.addAttribute("barrios", barrioDireccionDAO.findAll());

        model.addAttribute("vistaActiva", "proveedores-lista");
        return "proveedores/detalle-proveedor";
    }

    // ─────────────────────────────────────────────
    // POST /proveedores/{id}/eliminar
    // ─────────────────────────────────────────────
    @PostMapping("/{id}/eliminar")
    public String eliminarProveedor(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        proveedorService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje",
                "Proveedor eliminado correctamente.");
        return "redirect:/proveedores";
    }
}