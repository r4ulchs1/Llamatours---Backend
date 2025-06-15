package com.spring.llamatours.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.llamatours.DTOs.UsuarioDTO;
import com.spring.llamatours.services.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping("/lista")
    public String listarUsuarios(Model model) {
        List<UsuarioDTO> usuarioDTO= usuarioService.findAllUsuarios();
        model.addAttribute("usuarios", usuarioDTO);
        return "usuarios/lista";
    }
    
    @GetMapping("/editar/{id}")
    public String mostraFormularioEdicion(@PathVariable Long id, Model model) {
        Optional<UsuarioDTO> usuarOptional= usuarioService.findUsuarioById(id);
        if (usuarOptional.isPresent()) {
            model.addAttribute("usuario", usuarOptional.get());
            return "usuarios/formulario";
        }else{
            return "redirect:/usuarios/lista";
        }
    }
    
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new UsuarioDTO());
        return "usuarios/formulario";
    }



    @PostMapping("/guardar") //guardar/actualizar
    public String guardarUsuario(@ModelAttribute UsuarioDTO usuarioDTO, RedirectAttributes redirectAttributes) {
        try {
            if (usuarioDTO.getId()==null) {
                usuarioService.saveUsuario(usuarioDTO);
                redirectAttributes.addFlashAttribute("mensaje", "Registrado Correctamente");
            }else{
                usuarioService.updateUsuario(usuarioDTO.getId(), usuarioDTO);
                redirectAttributes.addFlashAttribute("mensaje", "Actualizado Correctamente");
            }
            return "redirect:/usuarios/lista";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar usuario" +e.getMessage());
            if (usuarioDTO.getId()==null) {
                return "redirect:/usuarios/registro";
            }else{
                return "redirect:/usuarios/editar/"+usuarioDTO.getId();
            }
        }
    }
    

    @PostMapping("/eliminar")
    public String eliminarUsuario(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            boolean eliminado=usuarioService.deleteUsuarioById(id);
            if (eliminado) {
                redirectAttributes.addFlashAttribute("mensaje","Usuario eliminado correctamente");
            }else{
                redirectAttributes.addFlashAttribute("error","Usuario no encontrado");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error","Error al eliminar usuario" +e.getMessage());
        }
        return "redirect:/usuarios/lista";
    }
    
}
