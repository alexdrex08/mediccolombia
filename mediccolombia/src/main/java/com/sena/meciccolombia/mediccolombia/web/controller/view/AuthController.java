package com.sena.meciccolombia.mediccolombia.web.controller.view;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sena.meciccolombia.mediccolombia.dao.UsuarioDAO;
import com.sena.meciccolombia.mediccolombia.domain.Usuario;
import com.sena.meciccolombia.mediccolombia.service.UsuarioService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.UsuarioCreateRequestDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    // ─────────────────────────────────────────────────────────────────
    // TOKEN DE RECUPERACIÓN
    // ─────────────────────────────────────────────────────────────────
    private static final String TOKEN_RECUPERACION = "ASURAS-2026-RESET";
    private static final String TOKEN_CAMBIAR = "ASURAS-2026-CHANGE";

    private final UsuarioService usuarioService;
    private final UsuarioDAO usuarioDAO;
    private final PasswordEncoder passwordEncoder;

    // ─────────────────────────────────────────────────────────────────
    // GET /auth/login - formulario de login
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    // ─────────────────────────────────────────────────────────────────
    // GET /auth/register — formulario de registro
    // ─────────────────────────────────────────────────────────────────
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("usuario", new UsuarioCreateRequestDTO());
        return "auth/register";
    }

    // ─────────────────────────────────────────────────────────────────
    // POST /auth/register — crear cuenta nueva
    @PostMapping("/register")
    public String registrar(
            @ModelAttribute UsuarioCreateRequestDTO dto,
            RedirectAttributes redirectAttributes,
            Model model) {

        // Verificar correo duplicado antes de intentar crear
        if (usuarioDAO.existsByCorreo(dto.getCorreo())) {
            model.addAttribute("error", "Ya existe una cuenta con ese correo electrónico.");
            model.addAttribute("nombreAnterior", dto.getNombre());
            model.addAttribute("correoAnterior", dto.getCorreo());
            model.addAttribute("identificacionAnterior", dto.getIdentificacion());
            return "auth/register";
        }

        // Rol por defecto para registros públicos
        dto.setRol("EMPLEADO");

        // Crear el usuario (el service encripta la contraseña con BCrypt)
        usuarioService.crear(dto);

        redirectAttributes.addFlashAttribute("mensaje",
                "Cuenta creada exitosamente. Ya puedes iniciar sesión.");
        return "redirect:/auth/login";
    }

    // ─────────────────────────────────────────────────────────────────
    // GET /auth/recuperar — paso 1: formulario inicial
    // ─────────────────────────────────────────────────────────────────
    @GetMapping("/recuperar")
    public String recuperar() {
        return "auth/recuperar-contrasena";
    }

    @GetMapping("/cambiar")
    public String cambiar() {
        return "auth/cambiar-contrasena";
    }

    // ─────────────────────────────────────────────────────────────────
    // GET /api/auth/verificar-identificacion?identificacion=...
    @GetMapping("/api/verificar-token")
    @ResponseBody
    public org.springframework.http.ResponseEntity<Void> verificarToken(
            @RequestParam String identificacion,
            @RequestParam String token) {

        // Validar token primero
        if (!TOKEN_RECUPERACION.equals(token)) {
            return org.springframework.http.ResponseEntity.status(403).build();
        }

        // Validar que el documento existe
        boolean existe = usuarioDAO.findByIdentificacion(identificacion).isPresent();
        return existe
                ? org.springframework.http.ResponseEntity.ok().build()
                : org.springframework.http.ResponseEntity.notFound().build();
    }


        @GetMapping("/api/verificar-token-cambio")
    @ResponseBody
    public org.springframework.http.ResponseEntity<Void> verificarTokenCambio(
            @RequestParam String identificacion,
            @RequestParam String token) {

        // Validar token primero
        if (!TOKEN_CAMBIAR.equals(token)) {
            return org.springframework.http.ResponseEntity.status(403).build();
        }

        // Validar que el documento existe
        boolean existe = usuarioDAO.findByIdentificacion(identificacion).isPresent();
        return existe
                ? org.springframework.http.ResponseEntity.ok().build()
                : org.springframework.http.ResponseEntity.notFound().build();
    }

    // ─────────────────────────────────────────────────────────────────
    // POST /auth/cambiar-contrasena-recuperacion //
    // ─────────────────────────────────────────────────────────────────
    @PostMapping("/cambiar-contrasena-recuperacion")
    public String cambiarContrasenaRecuperacion(
            @RequestParam String identificacion,
            @RequestParam String token,
            @RequestParam String nuevaContrasena,
            @RequestParam String confirmarContrasena,
            RedirectAttributes redirectAttributes) {

        // Validación 1 — token
        if (!TOKEN_RECUPERACION.equals(token)) {
            redirectAttributes.addFlashAttribute("error",
                    "Token de recuperación inválido. Contacta al administrador.");
            return "redirect:/auth/recuperar";
        }

        // Validación 2 — contraseñas coinciden
        if (!nuevaContrasena.equals(confirmarContrasena)) {
            redirectAttributes.addFlashAttribute("error",
                    "Las contraseñas no coinciden.");
            return "redirect:/auth/recuperar";
        }

        // Validación 3 — identificacion existe
        Optional<Usuario> usuarioOpt = usuarioDAO.findByIdentificacion(identificacion);
        if (usuarioOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    "No se encontró un usuario con ese número de documento.");
            return "redirect:/auth/recuperar";
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setContrasena(passwordEncoder.encode(nuevaContrasena));
        usuarioDAO.save(usuario);

        redirectAttributes.addFlashAttribute("mensaje",
                "Contraseña actualizada exitosamente. Ya puedes iniciar sesión.");
        return "redirect:/auth/login";
    }

    @PostMapping("/cambiar-contrasena")
    public String cambiarContrasena(
            @RequestParam String contrasenaActual,
            @RequestParam String nuevaContrasena,
            @RequestParam String token,
            @RequestParam String identificacion,
            @RequestParam String confirmarContrasena,
            Authentication auth,
            RedirectAttributes redirectAttributes) {
        // Validación 1 — token
        if (!TOKEN_CAMBIAR.equals(token)) {
            redirectAttributes.addFlashAttribute("error",
                    "Token de cambio de contraseña inválido. Contacta al administrador.");
            return "redirect:/auth/cambiar";
        }

        // Validación 2 — contraseñas coinciden
        if (!nuevaContrasena.equals(confirmarContrasena)) {
            redirectAttributes.addFlashAttribute("error",
                    "Las contraseñas no coinciden.");
            return "redirect:/auth/cambiar";
        }

        // Validación 3 — identificacion existe
        Optional<Usuario> usuarioOpt = usuarioDAO.findByIdentificacion(identificacion);
        if (usuarioOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    "No se encontró un usuario con ese número de documento.");
            return "redirect:/auth/cambiar";
        }

        Usuario usuario = usuarioOpt.get();

        // Validación 5 — contraseña actual coincide (usando BCrypt)
        if (!passwordEncoder.matches(contrasenaActual, usuario.getContrasena())) {
            redirectAttributes.addFlashAttribute("error",
                    "La contraseña actual es incorrecta.");
            return "redirect:/auth/cambiar";
        }

        // Validación 6 — la nueva contraseña no puede ser igual a la actual
        if (passwordEncoder.matches(nuevaContrasena, usuario.getContrasena())) {
            redirectAttributes.addFlashAttribute("error",
                    "La nueva contraseña no puede ser igual a la actual.");
            return "redirect:/auth/cambiar";
        }

        usuario.setContrasena(passwordEncoder.encode(nuevaContrasena));
        usuarioDAO.save(usuario);

        redirectAttributes.addFlashAttribute("mensaje",
                "Contraseña actualizada exitosamente. Ya puedes iniciar sesión.");

        return "redirect:/perfil";
    }
}
