package com.sena.meciccolombia.mediccolombia.web.controller;

import com.sena.meciccolombia.mediccolombia.service.SuscriptorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ContactoController {

    private final SuscriptorService suscriptorService;

    @PostMapping("/contacto")
    public String contacto(
            @RequestParam(required = false) String correo,
            RedirectAttributes redirectAttributes) {

        if (correo == null || correo.isBlank()) {
            redirectAttributes.addFlashAttribute("error", "El correo es obligatorio.");
            return "redirect:/";
        }

        if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            redirectAttributes.addFlashAttribute("error", "Ingresa un correo electrónico válido.");
            return "redirect:/";
        }

        try {
            suscriptorService.guardar(correo);
            redirectAttributes.addFlashAttribute("mensaje", "¡Gracias por suscribirte! Recibirás nuestras novedades.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al suscribir: " + e.getMessage());
        }

        return "redirect:/";
    }
}