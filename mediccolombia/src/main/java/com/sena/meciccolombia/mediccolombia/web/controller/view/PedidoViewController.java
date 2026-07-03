package com.sena.meciccolombia.mediccolombia.web.controller.view;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sena.meciccolombia.mediccolombia.dao.EstadoPedidoDAO;
import com.sena.meciccolombia.mediccolombia.security.MyUserDetails;
import com.sena.meciccolombia.mediccolombia.service.PedidoCompraService;
import com.sena.meciccolombia.mediccolombia.service.ProveedorService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.CambiarEstadoPedidoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DetallePedidoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.PedidoCompraRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.PedidoCompraResponseDTO;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;

@Controller
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoViewController {

    private final PedidoCompraService pedidoCompraService;
    private final ProveedorService proveedorService;
    private final EstadoPedidoDAO estadoPedidoDAO;

    // ─────────────────────────────────────────────
    // GET /pedidos → lista de pedidos
    // ─────────────────────────────────────────────
    @GetMapping
    public String listarPedidos(Model model, Authentication auth) {
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        List<PedidoCompraResponseDTO> pedidos = pedidoCompraService.listar();
        model.addAttribute("pedidos", pedidos);

        long enProceso = pedidos.stream()
                .filter(p -> List.of("Borrador", "Pendiente de aprobación",
                        "Enviado/Solicitado", "En tránsito", "Recibido Parcial")
                        .contains(p.getEstadoPedido()))
                .count();
        long completados = pedidos.stream()
                .filter(p -> "Completado/Recibido".equals(p.getEstadoPedido()))
                .count();
        long cancelados = pedidos.stream()
                .filter(p -> List.of("Cancelado", "Rechazado", "Devuelto")
                        .contains(p.getEstadoPedido()))
                .count();

        model.addAttribute("totalPedidos", pedidos.size());
        model.addAttribute("pedidosEnProceso", enProceso);
        model.addAttribute("pedidosCompletados", completados);
        model.addAttribute("pedidosCancelados", cancelados);

        // Todos los estados para el filtro del select
        model.addAttribute("estados", estadoPedidoDAO.findAll());
        model.addAttribute("esAdmin", "ADMIN".equals(user.getRol()));
        model.addAttribute("vistaActiva", "pedidos-lista");
        return "pedidos/lista-pedidos";
    }

    // ─────────────────────────────────────────────
    // GET /pedidos/nuevo → formulario de nuevo pedido
    // ─────────────────────────────────────────────
    @GetMapping("/nuevo")
    public String nuevoPedido(Model model) {
        model.addAttribute("proveedores", proveedorService.listar());
        model.addAttribute("vistaActiva", "pedidos-nuevo");
        return "pedidos/nuevo-pedido";
    }

    // ─────────────────────────────────────────────
    // POST /pedidos/nuevo → crear pedido
    // ─────────────────────────────────────────────
    @PostMapping("/nuevo")
    public String crearPedido(
            @RequestParam Long idProveedor,
            @RequestParam(required = false) String observacion,
            @RequestParam("idProducto") List<Long> idProductos,
            @RequestParam("cantidad") List<Integer> cantidades,
            @RequestParam("precioUnitario") List<BigDecimal> precios,
            RedirectAttributes redirectAttributes) {

        if (idProductos == null || idProductos.isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    "El pedido debe tener al menos un producto.");
            return "redirect:/pedidos/nuevo";
        }

        // Construir lista de detalles combinando los arrays paralelos
        List<DetallePedidoRequestDTO> detalles = new ArrayList<>();
        for (int i = 0; i < idProductos.size(); i++) {
            detalles.add(DetallePedidoRequestDTO.builder()
                    .idProducto(idProductos.get(i))
                    .cantidad(cantidades.get(i))
                    .precioUnitario(precios.get(i))
                    .build());
        }

        PedidoCompraRequestDTO dto = PedidoCompraRequestDTO.builder()
                .idProveedor(idProveedor)
                .observacion(observacion != null && !observacion.isBlank()
                        ? observacion
                        : null)
                .detalles(detalles)
                .build();

        try {
            PedidoCompraResponseDTO creado = pedidoCompraService.crearPedido(dto);
            redirectAttributes.addFlashAttribute("mensaje",
                    "Pedido #" + creado.getId() + " creado en estado Borrador.");
            return "redirect:/pedidos/" + creado.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al crear el pedido: " + e.getMessage());
            return "redirect:/pedidos/nuevo";
        }
    }

    // ─────────────────────────────────────────────
    // GET /pedidos/{id} → detalle del pedido
    // ─────────────────────────────────────────────
    @GetMapping("/{id}")
    public String detallePedido(@PathVariable Long id, Model model, Authentication auth) {
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        PedidoCompraResponseDTO pedido = pedidoCompraService.obtenerPorId(id);
        model.addAttribute("pedido", pedido);
        // Todos los estados para el select de cambio de estado
        model.addAttribute("estados", estadoPedidoDAO.findAll());
        model.addAttribute("esAdmin", "ADMIN".equals(user.getRol()));
        model.addAttribute("vistaActiva", "pedidos-lista");
        return "pedidos/detalle-pedido";
    }

    // ─────────────────────────────────────────────
    // POST /pedidos/{id}/estado → cambiar estado
    // ─────────────────────────────────────────────
    @PostMapping("/{id}/estado")
    public String cambiarEstado(
            @PathVariable Long id,
            @RequestParam Long idEstadoPedido,
            @RequestParam(required = false) String observacion,
            RedirectAttributes redirectAttributes) {

        try {
            CambiarEstadoPedidoRequestDTO dto = CambiarEstadoPedidoRequestDTO.builder()
                    .idEstadoPedido(idEstadoPedido)
                    .observacion(observacion)
                    .build();

            PedidoCompraResponseDTO actualizado = pedidoCompraService.cambiarEstado(id, dto);
            redirectAttributes.addFlashAttribute("mensaje",
                    "Estado actualizado a: " + actualizado.getEstadoPedido());
        } catch (IllegalStateException e) {
            // Errores de flujo — ruptura de secuencia de estados
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al cambiar el estado: " + e.getMessage());
        }

        return "redirect:/pedidos/" + id;
    }
}