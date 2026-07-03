package com.sena.meciccolombia.mediccolombia.web.controller.view;

import java.time.LocalDateTime;
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

import com.sena.meciccolombia.mediccolombia.dao.TipoEstadoDAO;
import com.sena.meciccolombia.mediccolombia.security.MyUserDetails;
import com.sena.meciccolombia.mediccolombia.service.EstadoUsuarioService;
import com.sena.meciccolombia.mediccolombia.service.IVentaRegistroService;
import com.sena.meciccolombia.mediccolombia.service.UsuarioService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.EstadoUsuarioRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.UsuarioCambiarContrasenaDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.MovimientoProdResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioDetalleMovimientosResponseDto;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.VentaRegistroResponseDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/perfil")
@RequiredArgsConstructor
public class PerfilViewController {

        private final UsuarioService usuarioService;
        private final IVentaRegistroService ventaRegistroService;
        private final EstadoUsuarioService estadoUsuarioService;
        private final TipoEstadoDAO tipoEstadoDAO;

        // ─────────────────────────────────────────────
        // GET /perfil
        // ─────────────────────────────────────────────
        @GetMapping
        public String verPerfil(Model model, Authentication auth) {
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                Long idUsuario = user.getId();
                String rol = user.getRol();

                // Datos del usuario autenticado
                UsuarioDetalleResponseDTO usuario = usuarioService.obtenerDetalle(idUsuario);
                model.addAttribute("usuario", usuario);

                // Movimientos y estadísticas
                UsuarioDetalleMovimientosResponseDto detalleMovimientos = usuarioService
                                .obtenerDetalleMovimiento(idUsuario);
                List<MovimientoProdResponseDTO> movimientos = detalleMovimientos.getMovimientos();

                long totalEntradas = movimientos.stream()
                                .filter(m -> {
                                        String tipo = m.getTipoMovimiento() != null
                                                        ? m.getTipoMovimiento().toLowerCase()
                                                        : "";
                                        return tipo.contains("compra")
                                                        || (tipo.contains("devoluci") && tipo.contains("cliente"))
                                                        || tipo.contains("traslado recib")
                                                        || (tipo.contains("nivelaci") && tipo.contains("+"));
                                })
                                .count();

                model.addAttribute("totalMovimientos", movimientos.size());
                model.addAttribute("totalEntradas", totalEntradas);
                model.addAttribute("totalSalidas", movimientos.size() - totalEntradas);

                List<VentaRegistroResponseDTO> ventas = ventaRegistroService.listarVentasPorUsuario(idUsuario);
                model.addAttribute("totalVentas", ventas.size());

                List<MovimientoProdResponseDTO> actividadReciente = movimientos.stream()
                                .sorted((a, b) -> {
                                        if (a.getFechaMovimiento() == null)
                                                return 1;
                                        if (b.getFechaMovimiento() == null)
                                                return -1;
                                        return b.getFechaMovimiento().compareTo(a.getFechaMovimiento());
                                })
                                .limit(5)
                                .toList();
                model.addAttribute("actividadReciente", actividadReciente);

                // ── Sección exclusiva ADMIN ──
                boolean esAdmin = "ADMIN".equals(rol);
                model.addAttribute("esAdmin", esAdmin);

                if (esAdmin) {
                        // Todos los usuarios para la tabla de gestión
                        List<UsuarioResponseDTO> todosUsuarios = usuarioService.listar();
                        model.addAttribute("todosUsuarios", todosUsuarios);

                        // Tipos de estado para el select del modal
                        model.addAttribute("tiposEstado", tipoEstadoDAO.findAll());
                }

                model.addAttribute("vistaActiva", "perfil");
                return "perfil/perfil";
        }

        // ─────────────────────────────────────────────
        // GET /perfil/cambiar-contrasena
        // ─────────────────────────────────────────────
        @GetMapping("/cambiar-contrasena")
        public String verCambiarContrasena(Model model) {
                model.addAttribute("vistaActiva", "perfil");
                return "perfil/cambiar-contrasena";
        }

        // ─────────────────────────────────────────────
        // POST /perfil/cambiar-contrasena
        // ─────────────────────────────────────────────
        @PostMapping("/cambiar-contrasena")
        public String procesarCambioContrasena(
                        Authentication auth,
                        @RequestParam String contrasenaActual,
                        @RequestParam String nuevaContrasena,
                        RedirectAttributes redirectAttributes) {

                MyUserDetails user = (MyUserDetails) auth.getPrincipal();

                try {
                        UsuarioCambiarContrasenaDTO dto = UsuarioCambiarContrasenaDTO.builder()
                                        .contrasenaActual(contrasenaActual)
                                        .nuevaContrasena(nuevaContrasena)
                                        .build();
                        usuarioService.cambiarContrasena(user.getId(), dto);
                        redirectAttributes.addFlashAttribute("mensaje",
                                        "Contraseña actualizada exitosamente.");
                } catch (Exception e) {
                        redirectAttributes.addFlashAttribute("error",
                                        "La contraseña actual no es correcta.");
                }
                return "redirect:/perfil/cambiar-contrasena";
        }

        // ─────────────────────────────────────────────
        // POST /perfil/usuarios/{id}/estado
        // ─────────────────────────────────────────────
        @PostMapping("/usuarios/{id}/estado")
        public String cambiarEstadoUsuario(
                        @PathVariable Long id,
                        @RequestParam Long idTipoEstado,
                        @RequestParam String fechaInicio,
                        @RequestParam(required = false) String fechaFin,
                        @RequestParam(required = false) String observacion,
                        Authentication auth,
                        RedirectAttributes redirectAttributes) {

                                //verificar datos
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                if (!"ADMIN".equals(user.getRol())) {
                        redirectAttributes.addFlashAttribute("error",
                                        "No tienes permisos para realizar esta acción.");
                        return "redirect:/perfil";
                }

                try {
                        EstadoUsuarioRequestDTO dto = EstadoUsuarioRequestDTO.builder()
                                        .idUsuario(id)
                                        .idTipoEstado(idTipoEstado)
                                        .fechaInicio(LocalDateTime.parse(fechaInicio))
                                        .fechaFin(fechaFin != null && !fechaFin.isBlank()
                                                        ? LocalDateTime.parse(fechaFin)
                                                        : null)
                                        .observacion(observacion != null && !observacion.isBlank()
                                                        ? observacion
                                                        : null)
                                        .build();

                        estadoUsuarioService.crear(dto);

                        redirectAttributes.addFlashAttribute("mensaje",
                                        "Estado del usuario actualizado correctamente.");
                } catch (Exception e) {
                        redirectAttributes.addFlashAttribute("error",
                                        "Error al actualizar el estado: " + e.getMessage());
                }

                return "redirect:/perfil";
        }
}