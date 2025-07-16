package com.spring.llamatours.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.llamatours.DTOs.PagoDTO;
import com.spring.llamatours.DTOs.ReservacionDTO;
import com.spring.llamatours.DTOs.UsuarioDTO;
import com.spring.llamatours.mapper.PagoMapper;
import com.spring.llamatours.model.Pago;
import com.spring.llamatours.repos.PagoRepo;
import com.spring.llamatours.services.PagoService;
import com.spring.llamatours.services.ReservacionService;
import com.spring.llamatours.services.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;






@Controller
@RequestMapping("/pagos")
@RequiredArgsConstructor
public class PagoController {
    private final PagoService pagoService;
    private final PagoRepo pagoRepo;
    private final PagoMapper pagoMapper;
    private final UsuarioService usuarioService;
    private final ReservacionService reservacionService;

    @GetMapping("/lista")
    public String listarPagos(Model model) {
        List<PagoDTO> pagoDTOs=pagoService.findAllPagos();
        List<UsuarioDTO> usuarios= usuarioService.findAllUsuarios();
        List<ReservacionDTO> reservaciones= reservacionService.findAllReservaciones();
        model.addAttribute("pagos", pagoDTOs);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("reservaciones", reservaciones);
        return "pagos/lista";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<PagoDTO> pagoOptional=pagoService.findPagoById(id);
        if (pagoOptional.isPresent()) {
            model.addAttribute("pago", pagoOptional.get());
            model.addAttribute("usuarios", usuarioService.findAllUsuarios());
            model.addAttribute("reservaciones", reservacionService.findAllReservaciones());
        return "pagos/formulario";
        }else{
            redirectAttributes.addFlashAttribute("error","Pago no encontrado");
            return "redirect:/pagos/lista";
        }
    }
    
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(@RequestParam(required = false) Long reservacionId, Model model) {
        PagoDTO pagoDTO = new PagoDTO();

        if (reservacionId != null) {
            Pago pago = pagoRepo.findByReservacionId(reservacionId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
            pagoDTO = pagoMapper.toDTO(pago);
        }

        model.addAttribute("pago", pagoDTO);
        model.addAttribute("reservaciones", reservacionService.findAllReservaciones());

        return "pagos/formulario";
    }
    
    @PostMapping("/guardar")
    public String postMethodName(@Valid @ModelAttribute("pago") PagoDTO pagoDTO,BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pago", pagoDTO);
            model.addAttribute("reservaciones", reservacionService.findAllReservaciones());
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>"+bindingResult);
            return "pagos/formulario";
        }
        try {
            if (pagoDTO.getId()==null) {
                pagoService.savePago(pagoDTO);
                redirectAttributes.addFlashAttribute("mensaje","Pago Exitoso");
            }else{
                pagoService.updatePago(pagoDTO.getId(), pagoDTO);
                redirectAttributes.addFlashAttribute("mensaje","Pago actualizado correctamente");
            }
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error","Error al guardar el pago: "+e.getMessage());
            if (pagoDTO.getId()==null) {
                return "redirect:/pagos/registro";
            }else{
                return "redirect:/pagos/editar/"+pagoDTO.getId();
            }
        }
    }

    @PostMapping("/cancelar")
    public String cancelarPago(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            pagoService.cancelarPagoById(id);
            redirectAttributes.addFlashAttribute("mensaje","Pago cancelado y reservacion actualizada");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error","Error al cancelar el pago: "+e.getMessage());
        }
        return "redirect:/pagos/lista";
    }
    
    @GetMapping("/listarPorReservacion")
    public String listarPagosPorReservacion(@RequestParam("reservacionId") String id,RedirectAttributes redirectAttributes, Model model) {
        try {
            Long resId=Long.parseLong(id);
            Optional<PagoDTO> pagOptional=pagoService.findPagoByReservacionId(resId);
            List<PagoDTO> pagosEncontrados= new ArrayList<>();
            if (pagOptional.isPresent()) {
                pagosEncontrados.add(pagOptional.get());
            }else{
                redirectAttributes.addFlashAttribute("error","Pago no existe");
                return "redirect:/pagos/lista";
            }
            List<UsuarioDTO> usuarios= usuarioService.findAllUsuarios();
            List<ReservacionDTO> reservaciones= reservacionService.findAllReservaciones();
            model.addAttribute("pagos", pagosEncontrados);
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("reservaciones", reservaciones);
            model.addAttribute("reservaSeleccionado", resId);
            return "pagos/lista";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error","Error al hacer busqueda por reservacion: "+ e.getMessage());
            return "redirect:/pagos/lista";
        }
    }
    
    @GetMapping("/listarPorUsuario")
    public String listarPagosPorUsuaraio(@RequestParam("usuarioId") String id,RedirectAttributes redirectAttributes, Model model) {
        try {
            Long userId=Long.parseLong(id);
            List<ReservacionDTO> reservacionesUsuario= reservacionService.findReservacionByUsuarioId(userId);
            List<PagoDTO> pagosEncontrados= new ArrayList<>();
            for(ReservacionDTO res: reservacionesUsuario){
                Optional<PagoDTO> pagoOpt= pagoService.findPagoByReservacionId(res.getId());
                pagoOpt.ifPresent(pagosEncontrados::add);
            }
            List<UsuarioDTO> usuarios= usuarioService.findAllUsuarios();
            List<ReservacionDTO> reservaciones= reservacionService.findAllReservaciones();
            model.addAttribute("pagos", pagosEncontrados);
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("reservaciones", reservaciones);
            model.addAttribute("userSeleccionado", userId);
            return "pagos/lista";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error","Error al hacer busqueda por usuario: "+ e.getMessage());
            return "redirect:/pagos/lista";
        }
    }

    @GetMapping("/calcularMonto")
    @ResponseBody
    public String calcularMontoPorReservacion(@RequestParam Long reservacionId) {
        try {
            Optional<PagoDTO> pagoOpt = pagoService.findPagoByReservacionId(reservacionId);
            if (pagoOpt.isPresent()) {
                return pagoOpt.get().getMonto().toString();
            } else {
                ReservacionDTO reservacion = reservacionService.findReservacionById(reservacionId)
                    .orElseThrow(() -> new RuntimeException("Reservación no encontrada"));
                BigDecimal monto = BigDecimal.valueOf(reservacion.getCantidadPersonas())
                        .multiply(reservacion.getDestino().getPrecio());
                return monto.toString();
            }
        } catch (Exception e) {
            return "0";
        }
    }
}
