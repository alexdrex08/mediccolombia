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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sena.meciccolombia.mediccolombia.dao.EstadoUsuarioDAO;
import com.sena.meciccolombia.mediccolombia.dao.TipoEstadoDAO;
import com.sena.meciccolombia.mediccolombia.domain.EstadoUsuario;
import com.sena.meciccolombia.mediccolombia.security.MyUserDetails;
import com.sena.meciccolombia.mediccolombia.service.ConfiguracionSistemaService;
import com.sena.meciccolombia.mediccolombia.service.EstadoUsuarioService;
import com.sena.meciccolombia.mediccolombia.service.IVentaRegistroService;
import com.sena.meciccolombia.mediccolombia.service.UsuarioService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.EstadoUsuarioRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.UsuarioCreateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.UsuarioUpdateRequest;
import com.sena.meciccolombia.mediccolombia.web.dto.response.MovimientoProdResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioDetalleMovimientosResponseDto;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioResponseDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioViewController {

    private final UsuarioService usuarioService;
    private final IVentaRegistroService ventaRegistroService;
    private final EstadoUsuarioService estadoUsuarioService;
    private final TipoEstadoDAO tipoEstadoDAO;
    private final EstadoUsuarioDAO estadoUsuarioDAO;
    private final ConfiguracionSistemaService configuracionService;

    // ─────────────────────────────────────────────────────────────────
    // GET /usuarios → lista
    // ─────────────────────────────────────────────────────────────────
    @GetMapping
    public String listarUsuarios(Model model, Authentication auth) {

        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        boolean esAdmin = "ADMIN".equals(user.getRol());

        List<UsuarioResponseDTO> usuarios = usuarioService.listar();

        Map<Long, String> ultimoEstadoPorUsuario = estadoUsuarioDAO.findAll().stream()
                .collect(Collectors.groupingBy(
                        e -> e.getUsuario().getId(),
                        Collectors.collectingAndThen(
                                Collectors.maxBy(
                                        Comparator.comparing(
                                                EstadoUsuario::getFechaInicio)),
                                opt -> opt
                                        .map(e -> e.getTipoEstado().getNombreTipo())
                                        .orElse("Sin estado"))));

        Set<String> estadosInactivos = Set.of(
                "inactivo",
                "bloqueado",
                "ausente",
                "sin estado");

        long activos = usuarios.stream()
                .filter(u -> {

                    String estado = ultimoEstadoPorUsuario.getOrDefault(
                            u.getId(),
                            "Sin estado")
                            .trim()
                            .toLowerCase();

                    return !estadosInactivos.contains(estado);
                })
                .count();

        long inactivos = usuarios.size() - activos;

        Map<String, Long> porRol = usuarios.stream()
                .collect(Collectors.groupingBy(
                        UsuarioResponseDTO::getRol,
                        Collectors.counting()));

        model.addAttribute("vistaActiva", "usuarios-lista");
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("totalUsuarios", usuarios.size());
        model.addAttribute("usuariosActivos", activos);
        model.addAttribute("usuariosInactivos", inactivos);
        model.addAttribute("porRol", porRol);
        model.addAttribute("ultimoEstado", ultimoEstadoPorUsuario);
        model.addAttribute("esAdmin", esAdmin);

        return "usuarios/lista-usuarios";
    }

    // ─────────────────────────────────────────────────────────────────
    // GET /usuarios/{id
    // ─────────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Long id, Model model, Authentication auth) {
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        boolean esAdmin = "ADMIN".equals(user.getRol());

        UsuarioDetalleResponseDTO usuario = usuarioService.obtenerDetalle(id);

        UsuarioDetalleMovimientosResponseDto detalleMovs = usuarioService.obtenerDetalleMovimiento(id);
        List<MovimientoProdResponseDTO> movimientos = detalleMovs.getMovimientos();

        // Actividad reciente
        int limiteActividad = leerConfigInt("filas_dashboard", 5);
        List<MovimientoProdResponseDTO> actividadReciente = movimientos.stream()
                .filter(m -> m.getFechaMovimiento() != null)
                .sorted(Comparator.comparing(MovimientoProdResponseDTO::getFechaMovimiento).reversed())
                .limit(limiteActividad)
                .toList();

        long totalVentas = ventaRegistroService.listarVentasPorUsuario(id).size();

        model.addAttribute("vistaActiva", "usuarios-lista");
        model.addAttribute("usuario", usuario);
        model.addAttribute("totalMovimientos", movimientos.size());
        model.addAttribute("totalVentas", totalVentas);
        model.addAttribute("actividadReciente", actividadReciente);
        model.addAttribute("esAdmin", esAdmin);
        model.addAttribute("esPropioUsuario", user.getId().equals(id));

        if (esAdmin) {
            model.addAttribute("tiposEstado", tipoEstadoDAO.findAll());
        }

        return "usuarios/detalle-usuario";
    }

    // ─────────────────────────────────────────────────────────────────
    // GET /usuarios/nuevo
    // ─────────────────────────────────────────────────────────────────
    @GetMapping("/nuevo")
    public String nuevoUsuario(Model model, Authentication auth) {
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (!"ADMIN".equals(user.getRol()))
            return "redirect:/usuarios";

        model.addAttribute("vistaActiva", "usuarios-nuevo");
        model.addAttribute("tiposEstado", tipoEstadoDAO.findAll());
        model.addAttribute("modoEdicion", false);
        return "usuarios/nuevo-usuario";
    }

    // ─────────────────────────────────────────────────────────────────
    // GET /usuarios/{id}/editar
    // ─────────────────────────────────────────────────────────────────
    @GetMapping("/{id}/editar")
    public String editarUsuario(@PathVariable Long id, Model model, Authentication auth) {
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (!"ADMIN".equals(user.getRol()))
            return "redirect:/usuarios";

        model.addAttribute("vistaActiva", "usuarios-lista");
        model.addAttribute("usuario", usuarioService.obtenerPorId(id));
        model.addAttribute("tiposEstado", tipoEstadoDAO.findAll());
        model.addAttribute("modoEdicion", true);
        return "usuarios/nuevo-usuario";
    }

    // ─────────────────────────────────────────────────────────────────
    // POST /usuarios //
    // ─────────────────────────────────────────────────────────────────
    @PostMapping
    public String crearUsuario(
            @RequestParam String nombre,
            @RequestParam String identificacion,
            @RequestParam String correo,
            @RequestParam String rol,
            @RequestParam(required = false) String contrasena,
            @RequestParam Long idTipoEstado,
            @RequestParam(required = false) String observacion,
            Authentication auth,
            RedirectAttributes ra) {

        if (!"ADMIN".equals(((MyUserDetails) auth.getPrincipal()).getRol())) {
            ra.addFlashAttribute("error", "Sin permisos.");
            return "redirect:/usuarios";
        }

        try {
            UsuarioCreateRequestDTO dto = UsuarioCreateRequestDTO.builder()
                    .nombre(nombre)
                    .identificacion(identificacion)
                    .correo(correo)
                    .rol(rol)
                    .contrasena(contrasena != null && !contrasena.isBlank()
                            ? contrasena
                            : "12345678")
                    .build();

            UsuarioResponseDTO nuevo = usuarioService.crear(dto);

            EstadoUsuarioRequestDTO estadoDTO = EstadoUsuarioRequestDTO.builder()
                    .idUsuario(nuevo.getId())
                    .idTipoEstado(idTipoEstado)
                    .fechaInicio(LocalDateTime.now())
                    .observacion(observacion != null && !observacion.isBlank()
                            ? observacion
                            : "Estado inicial al crear el usuario")
                    .build();
            estadoUsuarioService.crear(estadoDTO);

            ra.addFlashAttribute("mensaje", "Usuario '" + nombre + "' creado correctamente.");
            return "redirect:/usuarios/" + nuevo.getId();

        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al crear: " + e.getMessage());
            return "redirect:/usuarios/nuevo";
        }
    }

    // ─────────────────────────────────────────────────────────────────
    // POST /usuarios/{id}/actualizar
    // ─────────────────────────────────────────────────────────────────
    @PostMapping("/{id}/actualizar")
    public String actualizarUsuario(
            @PathVariable Long id,
            @RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam String rol,
            Authentication auth,
            RedirectAttributes ra) {

        if (!"ADMIN".equals(((MyUserDetails) auth.getPrincipal()).getRol())) {
            ra.addFlashAttribute("error", "Sin permisos.");
            return "redirect:/usuarios";
        }

        try {
            usuarioService.actualizar(id, UsuarioUpdateRequest.builder()
                    .nombre(nombre).correo(correo).rol(rol).build());
            ra.addFlashAttribute("mensaje", "Usuario actualizado correctamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/usuarios/" + id;
    }

    // ─────────────────────────────────────────────────────────────────
    // POST /usuarios/{id}/eliminar
    // ─────────────────────────────────────────────────────────────────
    @PostMapping("/{id}/eliminar")
    public String eliminarUsuario(
            @PathVariable Long id, Authentication auth, RedirectAttributes ra) {

        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (!"ADMIN".equals(user.getRol())) {
            ra.addFlashAttribute("error", "Sin permisos.");
            return "redirect:/usuarios";
        }
        if (user.getId().equals(id)) {
            ra.addFlashAttribute("error", "No puedes eliminar tu propio usuario.");
            return "redirect:/usuarios";
        }

        try {
            usuarioService.eliminar(id);
            ra.addFlashAttribute("mensaje", "Usuario eliminado.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/usuarios";
    }

    // ─────────────────────────────────────────────────────────────────
    // POST /usuarios/{id}/estado
    // ─────────────────────────────────────────────────────────────────
    @PostMapping("/{id}/estado")
    public String cambiarEstado(
            @PathVariable Long id,
            @RequestParam Long idTipoEstado,
            @RequestParam String fechaInicio,
            @RequestParam(required = false) String fechaFin,
            @RequestParam(required = false) String observacion,
            Authentication auth,
            RedirectAttributes ra) {

        if (!"ADMIN".equals(((MyUserDetails) auth.getPrincipal()).getRol())) {
            ra.addFlashAttribute("error", "Sin permisos.");
            return "redirect:/usuarios/" + id;
        }

        try {
            estadoUsuarioService.crear(EstadoUsuarioRequestDTO.builder()
                    .idUsuario(id)
                    .idTipoEstado(idTipoEstado)
                    .fechaInicio(LocalDateTime.parse(fechaInicio))
                    .fechaFin(fechaFin != null && !fechaFin.isBlank()
                            ? LocalDateTime.parse(fechaFin)
                            : null)
                    .observacion(observacion)
                    .build());
            ra.addFlashAttribute("mensaje", "Estado actualizado.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/usuarios/" + id;
    }

    

    // ─────────────────────────────────────────────────────────────────
    // Helper
    // ─────────────────────────────────────────────────────────────────
    private int leerConfigInt(String clave, int fallback) {
        try {
            String v = configuracionService.obtenerValor(clave);
            return (v != null && !v.isBlank()) ? Integer.parseInt(v.trim()) : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }
}