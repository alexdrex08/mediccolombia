package com.sena.meciccolombia.mediccolombia.web.controller.view;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sena.meciccolombia.mediccolombia.dao.BarrioDireccionDAO;
import com.sena.meciccolombia.mediccolombia.dao.TipoCorreoDAO;
import com.sena.meciccolombia.mediccolombia.dao.TipoDireccionDAO;
import com.sena.meciccolombia.mediccolombia.dao.TipoTelefonoDAO;
import com.sena.meciccolombia.mediccolombia.service.ClienteService;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ClienteDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ClienteResponseDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteViewController {

    private final ClienteService clienteService;
    
    private final TipoCorreoDAO tipoCorreoDAO;
    private final TipoTelefonoDAO tipoTelefonoDAO;
    private final TipoDireccionDAO tipoDireccionDAO;
    private final BarrioDireccionDAO barrioDireccionDAO;

    // ─────────────────────────────────────────────
    // GET /clientes
    // ─────────────────────────────────────────────
    @GetMapping
    public String listarClientes(
            @RequestParam(required = false) String busqueda,
            Model modelo) {

        List<ClienteResponseDTO> clientes;

        if (busqueda != null && !busqueda.isBlank()) {
            try {
                ClienteResponseDTO encontrado = clienteService.buscarPorIdentificacion(busqueda.trim());
                clientes = List.of(encontrado);
                modelo.addAttribute("busquedaActiva", true);
                modelo.addAttribute("busqueda", busqueda);
                modelo.addAttribute("sinResultados", false);
            } catch (Exception e) {
                clientes = List.of();
                modelo.addAttribute("busquedaActiva", true);
                modelo.addAttribute("busqueda", busqueda);
                modelo.addAttribute("sinResultados", true);
            }
        } else {
            clientes = clienteService.listar();
            modelo.addAttribute("busquedaActiva", false);
            modelo.addAttribute("sinResultados", false);
        }

        modelo.addAttribute("clientes", clientes);
        modelo.addAttribute("totalClientes", clienteService.listar().size());
        modelo.addAttribute("fechaActualizacion", LocalDateTime.now());
        modelo.addAttribute("vistaActiva", "clientes-lista");
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