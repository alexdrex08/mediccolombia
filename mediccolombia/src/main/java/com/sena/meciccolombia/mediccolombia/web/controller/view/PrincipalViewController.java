package com.sena.meciccolombia.mediccolombia.web.controller.view;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sena.meciccolombia.mediccolombia.security.MyUserDetails;
import com.sena.meciccolombia.mediccolombia.service.AlertaInvService;
import com.sena.meciccolombia.mediccolombia.service.ProductoService;
import com.sena.meciccolombia.mediccolombia.service.ProveedorService;
import com.sena.meciccolombia.mediccolombia.service.ReporteInvService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PrincipalViewController {

    private final ProductoService productoService;
    private final ProveedorService proveedorService;
    private final AlertaInvService alertaInvService;
    private final ReporteInvService reporteInvService;

    @GetMapping("/principal")
    public String principal(Model modelo, Authentication auth) {
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        modelo.addAttribute("vistaActiva", "principal");
        modelo.addAttribute("totalProductos", productoService.listarProductos().size());
        modelo.addAttribute("esAdmin", "ADMIN".equals(user.getRol()));
        modelo.addAttribute("usuario", user.getNombre());
        modelo.addAttribute("totalProveedores", proveedorService.listar().size());
        modelo.addAttribute("totalAlertas", alertaInvService.listarIsResueltaFalse().size());
        modelo.addAttribute("totalReportes", reporteInvService.listar().size());

        return "principal/principal";
    }
}