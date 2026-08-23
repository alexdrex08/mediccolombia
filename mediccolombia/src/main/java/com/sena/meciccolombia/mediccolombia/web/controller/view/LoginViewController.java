package com.sena.meciccolombia.mediccolombia.web.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginViewController {
    @GetMapping
    public String redirectToPrincipal() {
        return "redirect:/principal";
    }
}