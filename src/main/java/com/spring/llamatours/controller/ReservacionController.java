package com.spring.llamatours.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.llamatours.DTOs.ReservacionDTO;
import com.spring.llamatours.services.ReservacionService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservacionController {
    private final ReservacionService reservacionService;

    @GetMapping("/lista")
    public String listarReservaciones(Model model) {
        List<ReservacionDTO> reservaciones= reservacionService.findAllReservaciones();
        model.addAttribute("reservaciones", reservaciones);
        return "reservaciones/lista";
    }
    
    @GetMapping("/editar/{id}")
    public String mostraFormularioEdicion(@PathVariable Long id, Model model) {
        Optional<ReservacionDTO> resOptional=reservacionService.findReservacionById(id);
        if (resOptional.isPresent()) {
        model.addAttribute("reservacion", resOptional);
            return "destinos/formulario";
        }else{
            return "redirect:/destinos/lista";
        }
    }
    
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(@RequestParam String param) {
        return new String();
    }
    
}
