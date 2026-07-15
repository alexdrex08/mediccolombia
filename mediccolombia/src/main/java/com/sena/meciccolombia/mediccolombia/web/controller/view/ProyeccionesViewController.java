package com.sena.meciccolombia.mediccolombia.web.controller.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sena.meciccolombia.mediccolombia.service.ProyeccionesService;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProyeccionesResponseDTO;

@Controller
@RequestMapping("/proyecciones")
public class ProyeccionesViewController {

    private static final DateTimeFormatter FMT_INPUT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Autowired
    private ProyeccionesService proyeccionesService;

    @GetMapping
    public String verProyecciones(Model model) {

        List<ProyeccionesResponseDTO> historial = proyeccionesService.listar();

        int totalHistorial = historial.size();
        int totalMasVendidos = proyeccionesService.listarPorTipo(1L).size();

        model.addAttribute("vistaActiva", "proyecciones");
        model.addAttribute("historial", historial);
        model.addAttribute("totalHistorial", totalHistorial);
        model.addAttribute("totalMasVendidos", totalMasVendidos);

        // Consultas en tiempo real
        model.addAttribute("proveedoresFiables", proyeccionesService.consultarProveedoresMasFiables());
        model.addAttribute("clientesFieles", proyeccionesService.consultarClientesMasFileles());
        model.addAttribute("preciosMercado", proyeccionesService.consultarPreciosMercado());

        return "proyecciones/proyecciones";
    }


    @GetMapping("/manual")
    public String verFormularioManual(Model model) {
        model.addAttribute("vistaActiva", "proyecciones-manual");
        return "proyecciones/nueva-proyeccion";
    }
    @PostMapping("/generar")
    public String generarManual(
            @RequestParam Long idTipoProyeccion,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin,
            RedirectAttributes redirectAttributes) {

        LocalDateTime inicio = LocalDateTime.parse(fechaInicio, FMT_INPUT);
        LocalDateTime fin = LocalDateTime.parse(fechaFin, FMT_INPUT);

        try {
            proyeccionesService.generarManual(idTipoProyeccion, inicio, fin);
            redirectAttributes.addFlashAttribute("mensaje",
                    "Proyección generada correctamente para el período seleccionado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al generar la proyección: " + e.getMessage());
            return "redirect:/proyecciones/manual";
        }

        return "redirect:/proyecciones";
    }
}