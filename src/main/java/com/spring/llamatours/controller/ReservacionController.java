package com.spring.llamatours.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import com.spring.llamatours.DTOs.DestinoDTO;
import com.spring.llamatours.DTOs.ReservacionDTO;
import com.spring.llamatours.DTOs.UsuarioDTO;
import com.spring.llamatours.services.DestinoService;
import com.spring.llamatours.services.ReservacionService;
import com.spring.llamatours.services.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/reservaciones")
@RequiredArgsConstructor
public class ReservacionController {
    private final ReservacionService reservacionService;
    private final UsuarioService usuarioService;
    private final DestinoService destinoService;

    @GetMapping("/lista")
    public String listarReservaciones(Model model) {
        List<ReservacionDTO> reservaciones = reservacionService.findAllReservaciones();
        List<UsuarioDTO> usuarios = usuarioService.findAllUsuarios();
        model.addAttribute("reservaciones", reservaciones);
        model.addAttribute("usuarios", usuarios);
        return "reservaciones/lista";
    }

    @GetMapping("/editar/{id}")
    public String mostraFormularioEdicion(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<ReservacionDTO> resOptional = reservacionService.findReservacionById(id);
        if (resOptional.isPresent()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean esAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

            model.addAttribute("reservacion", resOptional.get());
            model.addAttribute("usuarios", usuarioService.findAllUsuarios());
            model.addAttribute("destinos", destinoService.findAllDestinos());
            model.addAttribute("esAdmin", esAdmin);

            if (!esAdmin) {
                String correo = auth.getName();
                usuarioService.findUsuarioByCorreo(correo)
                        .ifPresent(usuario -> model.addAttribute("usuarioAutenticado", usuario));
            }

            return "reservaciones/formulario";
        } else {
            redirectAttributes.addFlashAttribute("error", "Reservacion no encontrada: " + id);
            return "redirect:/reservaciones/lista";
        }
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        ReservacionDTO reservacion = new ReservacionDTO();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean esAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if (!esAdmin) {
            String correo = auth.getName(); // El username en Spring Security suele ser el correo
            Optional<UsuarioDTO> userOpt = usuarioService.findUsuarioByCorreo(correo);
            if (userOpt.isPresent()) {
                reservacion.setUsuarioId(userOpt.get().getId());
                model.addAttribute("usuarioAutenticado", userOpt.get());
            } else {
                model.addAttribute("error", "No se pudo identificar al usuario.");
                return "redirect:/";
            }
        }

        model.addAttribute("reservacion", reservacion);

        if (esAdmin) {
            model.addAttribute("usuarios", usuarioService.findAllUsuarios());
        }

        model.addAttribute("destinos", destinoService.findAllDestinos());
        model.addAttribute("esAdmin", esAdmin);

        return "reservaciones/formulario";
    }

    @PostMapping("/guardar")
    public String guardarReservacion(@Valid @ModelAttribute("reservacion") ReservacionDTO reservacionDTO,
                                    BindingResult bioBindingResult,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        if (bioBindingResult.hasErrors()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean esAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

            model.addAttribute("reservacion", reservacionDTO);
            model.addAttribute("destinos", destinoService.findAllDestinos());
            model.addAttribute("esAdmin", esAdmin);

            if (esAdmin) {
                model.addAttribute("usuarios", usuarioService.findAllUsuarios());
            } else {
                String correo = auth.getName();
                Optional<UsuarioDTO> userOpt = usuarioService.findUsuarioByCorreo(correo);
                userOpt.ifPresent(user -> model.addAttribute("usuarioAutenticado", user));
            }

            return "reservaciones/formulario";
        }

        try {
            if (reservacionDTO.getId() == null) {
                reservacionService.saveReservacion(reservacionDTO);
                redirectAttributes.addFlashAttribute("mensaje", "Reservación creada correctamente");
            } else {
                reservacionService.updateReservacion(reservacionDTO.getId(), reservacionDTO);
                redirectAttributes.addFlashAttribute("mensaje", "Reservación actualizada correctamente");
            }

            // 🔐 Verifica el rol del usuario autenticado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return "redirect:/reservaciones/lista";
            } else {
                return "redirect:/perfil"; // 👈 Cambio clave aquí
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
            if (reservacionDTO.getId() == null || reservacionDTO.getDestinoId() == null) {
                return "redirect:/reservaciones/registro";
            } else {
                return "redirect:/reservaciones/editar/" + reservacionDTO.getId();
            }
        }
    }

    @PostMapping("/eliminar")
    public String elminarReservacion(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            reservacionService.deleteReservacionById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Reservacion eliminada con exito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar");
        }
        return "redirect:/reservaciones/lista";
    }

    @GetMapping("/listaPorUsuario")
    public String listaReservacionesPorUsuario(@RequestParam("usuarioId") String id, Model model,
            RedirectAttributes redirectAttributes) {
        try {
            Long idd = Long.parseLong(id);
            Optional<UsuarioDTO> us = usuarioService.findUsuarioById(idd);
            if (us.isPresent()) {
                List<ReservacionDTO> reservacionDTOs = reservacionService.findReservacionByUsuarioId(idd);
                List<UsuarioDTO> usuarios = usuarioService.findAllUsuarios();
                model.addAttribute("reservaciones", reservacionDTOs);
                model.addAttribute("usuarios", usuarios);
                model.addAttribute("userSeleccionado", idd);
                return "reservaciones/lista";
            } else {
                redirectAttributes.addFlashAttribute("error", "Usuario no existe");
                return "redirect:/reservaciones/lista";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al hacer busquedas por usuario: " + e.getMessage());
            return "redirect:/reservaciones/lista";
        }
    }
}
