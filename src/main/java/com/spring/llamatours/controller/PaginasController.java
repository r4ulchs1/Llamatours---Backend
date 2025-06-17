package com.spring.llamatours.controller;

import com.spring.llamatours.DTOs.DestinoDTO;
import com.spring.llamatours.enums.DepartamentoNombre;
import com.spring.llamatours.services.DestinoService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PaginasController {

    private final DestinoService destinoService;

    @GetMapping("/")
    public String mostrarIndex() {
        return "paginas/index";
    }

    @GetMapping("/viaje")
    public String mostrarViajeAgrupado(Model model) {
        Map<DepartamentoNombre, List<DestinoDTO>> destinosPorDep = destinoService
                .obtenerDestinosAgrupadosPorDepartamento();
        model.addAttribute("destinosPorDepartamento", destinosPorDep);
        return "paginas/viaje";
    }

    @GetMapping("/viajes/detalle/{id}")
    public String mostrarDetalleDestino(@PathVariable Long id, Model model) {
        Optional<DestinoDTO> destinoOpt = destinoService.findDestinoById(id);
        if (destinoOpt.isPresent()) {
            model.addAttribute("destino", destinoOpt.get());
            return "paginas/destino"; // correcto
        } else {
            return "redirect:/viajes";
        }
    }

    @GetMapping("/contacto")
    public String mostrarContacto() {
        return "paginas/contacto";
    }

    @GetMapping("/nosotros")
    public String mostrarNosotros() {
        return "paginas/nosotros";
    }

    @GetMapping("/destino")
    public String mostrarDestino() {
        return "paginas/destino";
    }

}
