package com.spring.llamatours.controller;

import com.spring.llamatours.DTOs.DestinoDTO;
import com.spring.llamatours.enums.DepartamentoNombre;
import com.spring.llamatours.model.Pago;
import com.spring.llamatours.model.Reservacion;
import com.spring.llamatours.model.Usuario;
import com.spring.llamatours.repos.PagoRepo;
import com.spring.llamatours.repos.ReservacionRepo;
import com.spring.llamatours.repos.UsuarioRepo;
import com.spring.llamatours.services.DestinoService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
// import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class PaginasController {

    private final DestinoService destinoService;
    private final UsuarioRepo usuarioRepo;
    private final PagoRepo pagoRepo;
    private final ReservacionRepo reservacionRepo;

    @GetMapping("/")
    public String mostrarIndex() {
        return "paginas/index";
    }

    @GetMapping("/viaje")
    public String mostrarViajeAgrupado(Model model) {
        // Esto YA lo tenías (no cambia)
        Map<DepartamentoNombre, List<DestinoDTO>> destinosPorDep = destinoService
                .obtenerDestinosAgrupadosPorDepartamento();
        model.addAttribute("destinosPorDepartamento", destinosPorDep);
        
        // Esto es NUEVO (se añade para el mapa)
        List<String> departamentosDisponibles = destinosPorDep.keySet().stream()
                .map(Enum::name)
                .collect(Collectors.toList());
        model.addAttribute("departamentosDisponibles", departamentosDisponibles);
        
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

    @GetMapping("/perfil")
    public String mostrarPerfil(Model model, Principal principal) {
        String email = principal.getName();
        Usuario usuario = usuarioRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Reservacion> reservaciones = reservacionRepo.findByUsuarioId(usuario.getId());

        List<Map<String, Object>> reservasConPago = new ArrayList<>();

        for (Reservacion reserva : reservaciones) {
            Map<String, Object> item = new HashMap<>();
            item.put("reserva", reserva);

            Optional<Pago> pagoOpt = pagoRepo.findByReservacionId(reserva.getId());
            pagoOpt.ifPresent(pago -> item.put("pago", pago));

            reservasConPago.add(item);
        }

        model.addAttribute("reservasConPago", reservasConPago);
        return "perfil/usuarioDetalle";
    }
    
}
