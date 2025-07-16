package com.spring.llamatours.controller;

import com.spring.llamatours.DTOs.UsuarioDTO;
import com.spring.llamatours.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "auth/login";
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new UsuarioDTO());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@Valid @ModelAttribute("usuario") UsuarioDTO usuarioDTO,
                                    BindingResult result,
                                    Model model) {
        if (result.hasErrors()) {
            return "auth/registro";
        }

        try {
            usuarioService.saveUsuario(usuarioDTO);
            return "redirect:/login?registroExitoso";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/registro";
        }
    }
}
