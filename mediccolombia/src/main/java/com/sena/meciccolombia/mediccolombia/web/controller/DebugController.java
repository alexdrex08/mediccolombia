package com.sena.meciccolombia.mediccolombia.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sena.meciccolombia.mediccolombia.scheduler.AlertaInvScheduler;
import com.sena.meciccolombia.mediccolombia.scheduler.VencimientoScheduler;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/debug")
public class DebugController {

    private final AlertaInvScheduler scheduler;
    private final VencimientoScheduler vencimientos;

    @GetMapping("/generar-alertas")
    public String ejecutar(RedirectAttributes redirectAttributes) {

        try {
            scheduler.generarAlertasAutomaticas();
            vencimientos.retirarProductosVencidos();
            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    "La verificación manual de alertas se ejecutó correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "No fue posible ejecutar la verificación: " + e.getMessage());
        }
        return "redirect:/productos/alertas";
    }
}