package com.sena.meciccolombia.mediccolombia.web.controller.view;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sena.meciccolombia.mediccolombia.dao.BarrioDireccionDAO;
import com.sena.meciccolombia.mediccolombia.dao.DetalleVentaDAO;
import com.sena.meciccolombia.mediccolombia.dao.TipoCorreoDAO;
import com.sena.meciccolombia.mediccolombia.dao.TipoDireccionDAO;
import com.sena.meciccolombia.mediccolombia.dao.TipoTelefonoDAO;
import com.sena.meciccolombia.mediccolombia.dao.VentaRegistroDAO;
import com.sena.meciccolombia.mediccolombia.domain.DetalleVenta;
import com.sena.meciccolombia.mediccolombia.domain.VentaRegistro;
import com.sena.meciccolombia.mediccolombia.security.MyUserDetails;
import com.sena.meciccolombia.mediccolombia.service.ClienteService;
import com.sena.meciccolombia.mediccolombia.service.IVentaRegistroService;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ClienteDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ClienteResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DetalleVentaResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.VentaRegistroResponseDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteViewController {

    private final ClienteService clienteService;
    private final IVentaRegistroService ventaRegitroService;

    private final TipoCorreoDAO tipoCorreoDAO;
    private final DetalleVentaDAO detalleVentaDAO;
    private final VentaRegistroDAO ventaRegistroDAO;
    private final TipoTelefonoDAO tipoTelefonoDAO;
    private final TipoDireccionDAO tipoDireccionDAO;
    private final BarrioDireccionDAO barrioDireccionDAO;

    // ─────────────────────────────────────────────
    // GET /clientes
    // ─────────────────────────────────────────────
    @GetMapping
    public String listarClientes(
            Model modelo,
            Authentication auth) {

        List<ClienteResponseDTO> clientes = clienteService.listar();
        List<VentaRegistroResponseDTO> detalles = ventaRegitroService.listarVentas();

        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        List<Map<String, Object>> clientesEnriquecidos = clientes.stream()
                .map(cliente -> {
                    Map<String, Object> datos = new LinkedHashMap<>();
                    datos.put("id", cliente.getId());
                    datos.put("nombreCliente", cliente.getNombreCliente());
                    datos.put("identificacion", cliente.getIdentificacion());

                    // Producto mas comprado

                    List<VentaRegistro> ventCliente = ventaRegistroDAO
                            .findByClienteId(cliente.getId());

                    String productoMasComprado = ventCliente.stream()
                            .flatMap(v -> v.getDetalles().stream())
                            .map(d -> d.getProducto().getNombreProducto())
                            .collect(Collectors.groupingBy(p -> p, Collectors.counting()))
                            .entrySet().stream()
                            .max(Map.Entry.comparingByValue())
                            .map(Map.Entry::getKey)
                            .orElse("Sin producto favorito");

                    datos.put("productoMasComprado", productoMasComprado);
                    datos.put("totalCompras", ventCliente.size());

                    // Ultima compra
                    VentaRegistro ultimaCompra = ventCliente.stream()
                            .filter(p -> p.getFechaVenta() != null)
                            .max(Comparator.comparing(VentaRegistro::getFechaVenta))
                            .orElse(null);

                    if (ultimaCompra != null) {
                        datos.put("ultimaCompraId", ultimaCompra.getId());
                        datos.put("ultimaCompraFecha", ultimaCompra.getFechaVenta());
                        datos.put("ultimoMedioPago", ultimaCompra.getMedioPago());
                        datos.put("ultimaCompraTotal", ultimaCompra.getTotalVenta());
                    } else {
                        datos.put("ultimaCompraId", null);
                        datos.put("ultimaCompraFecha", null);
                        datos.put("ultimoMedioPago", null);
                        datos.put("ultimaCompraTotal", null);
                    }

                    return datos;
                })
                .toList();
        modelo.addAttribute("clientes", clientesEnriquecidos);
        modelo.addAttribute("totalClientes", clienteService.listar().size());
        modelo.addAttribute("fechaActualizacion", LocalDateTime.now());
        modelo.addAttribute("vistaActiva", "clientes-lista");
        modelo.addAttribute("esAdmin", "ADMIN".equals(user.getRol()));
        return "clientes/lista-clientes";
    }

    // ─────────────────────────────────────────────
    // GET /clientes/nuevo
    // ─────────────────────────────────────────────
    @GetMapping("/nuevo")
    public String nuevoCliente(Model modelo) {
        modelo.addAttribute("modoEdicion", false);
        modelo.addAttribute("vistaActiva", "clientes-nuevo");
        return "clientes/form-cliente";
    }

    // ─────────────────────────────────────────────
    // GET /clientes/{id}/editar
    // ─────────────────────────────────────────────
    @GetMapping("/{id}/editar")
    public String editarCliente(@PathVariable Long id, Model modelo) {
        ClienteResponseDTO cliente = clienteService.obtenerPorId(id);
        modelo.addAttribute("cliente", cliente);
        modelo.addAttribute("modoEdicion", true);
        modelo.addAttribute("vistaActiva", "clientes-lista");
        return "clientes/form-cliente";
    }

    // ─────────────────────────────────────────────
    // GET /clientes/{id} → detalle del cliente
    // ─────────────────────────────────────────────
    @GetMapping("/{id}")
    public String verCliente(@PathVariable Long id, Model modelo) {
        ClienteDetalleResponseDTO cliente = clienteService.obtenerDetalles(id);
        modelo.addAttribute("cliente", cliente);

        // Catálogos para los selects de los modales
        modelo.addAttribute("tiposCorreo", tipoCorreoDAO.findAll());
        modelo.addAttribute("tiposTelefono", tipoTelefonoDAO.findAll());
        modelo.addAttribute("tiposDireccion", tipoDireccionDAO.findAll());
        modelo.addAttribute("barrios", barrioDireccionDAO.findAll());

        modelo.addAttribute("vistaActiva", "clientes-lista");
        return "clientes/detalle-cliente";
    }

    // ─────────────────────────────────────────────
    // POST /clientes/{id}/eliminar
    // ─────────────────────────────────────────────
    @PostMapping("/{id}/eliminar")
    public String eliminarCliente(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        clienteService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje",
                "Cliente eliminado correctamente.");
        return "redirect:/clientes";
    }
}