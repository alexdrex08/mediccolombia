package com.sena.meciccolombia.mediccolombia.web.controller.view;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sena.meciccolombia.mediccolombia.security.MyUserDetails;
import com.sena.meciccolombia.mediccolombia.service.ConfiguracionSistemaService;
import com.sena.meciccolombia.mediccolombia.service.ConfiguracionUsuarioService;
import com.sena.meciccolombia.mediccolombia.service.UsuarioService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.UsuarioCambiarContrasenaDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ConfiguracionSistemaResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioResponseDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/configuracion")
@RequiredArgsConstructor
public class ConfiguracionViewController {

    private final ConfiguracionSistemaService configuracionSistemaService;
    private final ConfiguracionUsuarioService configuracionUsuarioService;
    private final UsuarioService usuarioService;

    // ─────────────────────────────────────────────────────────────
    // GET /configuracion/sistema
    // ─────────────────────────────────────────────────────────────
    @GetMapping("/sistema")
    public String verSistema(Model model, Authentication auth) {
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        List<ConfiguracionSistemaResponseDTO> params = configuracionSistemaService.listar();

        List<String> categorias = params.stream()
                .map(ConfiguracionSistemaResponseDTO::getCategoria)
                .filter(c -> c != null && !c.isBlank())
                .distinct()
                .sorted()
                .toList();

        Map<String, Long> totalPorCategoria = params.stream()
                .collect(Collectors.groupingBy(
                        ConfiguracionSistemaResponseDTO::getCategoria,
                        Collectors.counting()));

        model.addAttribute("totalPorCategoria", totalPorCategoria);

        model.addAttribute("configuraciones", params);
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalParams", params.size());
        model.addAttribute("esAdmin", "ADMIN".equals(user.getRol()));
        model.addAttribute("vistaActiva", "configuracion");

        System.out.println(params);
        System.out.println(params.size());

        return "configuracion/configuracion-sistema";
    }

    // ─────────────────────────────────────────────────────────────
    // POST /configuracion/sistema
    // ─────────────────────────────────────────────────────────────
    @PostMapping("/sistema")
    public String guardarConfiguracionSistema(
            @RequestParam Map<String, String> params,
            Authentication auth,
            RedirectAttributes redirectAttributes) {

        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        if (!"ADMIN".equals(user.getRol())) {
            redirectAttributes.addFlashAttribute("error",
                    "No tienes permisos para modificar la configuración del sistema.");
            return "redirect:/configuracion/sistema";
        }

        int actualizadas = 0;
        int errores = 0;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey().startsWith("clave_")) {
                String clave = entry.getKey().substring(6);
                String valor = entry.getValue();

                if (valor != null && !valor.isBlank()) {
                    try {
                        configuracionSistemaService.actualizar(clave, valor);
                        actualizadas++;
                    } catch (Exception e) {
                        errores++;
                    }
                }
            }
        }

        if (errores > 0) {
            redirectAttributes.addFlashAttribute("error",
                    actualizadas + " parámetro(s) actualizados con " + errores + " error(es).");
        } else {
            redirectAttributes.addFlashAttribute("mensaje",
                    actualizadas + " parámetro(s) de configuración actualizados correctamente.");
        }

        return "redirect:/configuracion/sistema";
    }

    // ─────────────────────────────────────────────────────────────
    // GET /configuracion/perfil
    // ─────────────────────────────────────────────────────────────
    @GetMapping("/perfil")
    public String verConfiguracionPerfil(Model model, Authentication auth) {
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        UsuarioResponseDTO usuario = usuarioService.obtenerPorId(user.getId());
        model.addAttribute("nombre", usuario.getNombre());
        model.addAttribute("correo", usuario.getCorreo());
        model.addAttribute("identificacion", usuario.getIdentificacion());
        model.addAttribute("rol", usuario.getRol());

        Map<String, String> preferencias = configuracionUsuarioService.obtenerPreferenciasUsuario(user.getId());
        model.addAttribute("preferencias", preferencias);

        String nombreSistema = configuracionSistemaService.obtenerValor("nombre_sistema");
        String nombreEmpresa = configuracionSistemaService.obtenerValor("nombre_empresa");
        model.addAttribute("nombreSistema", nombreSistema != null ? nombreSistema : "MedicColombia");
        model.addAttribute("nombreEmpresa", nombreEmpresa != null ? nombreEmpresa : "Asuras Col");

        model.addAttribute("vistaActiva", "configuracion-perfil");
        return "configuracion/configuracion-perfil";
    }

    @PostMapping("/perfil")
    public String guardarConfiguracionPerfil(
            @RequestParam(required = false, defaultValue = "oscuro") String tema,
            @RequestParam(required = false, defaultValue = "es") String idioma,
            @RequestParam(required = false, defaultValue = "10") String filas_por_pagina,
            @RequestParam(required = false, defaultValue = "false") String notificaciones,
            Authentication auth,
            RedirectAttributes redirectAttributes) {

        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        try {
            Map<String, String> preferencias = Map.of(
                    "tema", tema,
                    "idioma", idioma,
                    "filas_por_pagina", filas_por_pagina,
                    "notificaciones", notificaciones);
            configuracionUsuarioService.guardarTodasLasPreferencias(user.getId(), preferencias);
            redirectAttributes.addFlashAttribute("mensaje", "Preferencias guardadas correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
        }
        return "redirect:/configuracion/perfil";
    }

    // ─────────────────────────────────────────────
    @PostMapping("/perfil/cambiar-contrasena")
    public String procesarCambioContrasena(
            Authentication auth,
            @RequestParam String contrasenaActual,
            @RequestParam String nuevaContrasena,
            @RequestParam String confirmarContrasena,
            RedirectAttributes redirectAttributes) {

        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (!nuevaContrasena.equals(confirmarContrasena)) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Las nuevas contraseñas no coinciden.");

            return "redirect:/configuracion/perfil";
        }

        try {

            UsuarioCambiarContrasenaDTO dto = UsuarioCambiarContrasenaDTO.builder()
                    .contrasenaActual(contrasenaActual)
                    .nuevaContrasena(nuevaContrasena)
                    .build();

            usuarioService.cambiarContrasena(user.getId(), dto);

            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    "Contraseña actualizada exitosamente.");

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "La contraseña actual no es correcta.");
        }

        return "redirect:/configuracion/perfil";
    }

}