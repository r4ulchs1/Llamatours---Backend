package com.spring.llamatours.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.llamatours.DTOs.DestinoDTO;
import com.spring.llamatours.enums.DepartamentoNombre;
import com.spring.llamatours.services.DestinoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;





@Controller
@RequestMapping("/destinos")
@RequiredArgsConstructor
public class DestinoController {
    private final DestinoService destinoService;

    @GetMapping("/lista")
    public String listarDestinos(Model model) {
        List<DestinoDTO> destinoDTOs=destinoService.findAllDestinos();
        model.addAttribute("destinos", destinoDTOs);
        return "destinos/lista";
    }
    
    @GetMapping("/editar/{id}")
    public String mostraFormularioEdicion(@PathVariable Long id, Model model) {
        Optional<DestinoDTO> desOptional=destinoService.findDestinoById(id);
        if (desOptional.isPresent()) {
        model.addAttribute("destino", desOptional.get());
        model.addAttribute("departamentos", DepartamentoNombre.values());
            return "destinos/formulario";
        }else{
            return "redirect:/destinos/lista";
        }
    }
    
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("destino", new DestinoDTO());
        model.addAttribute("departamentos", DepartamentoNombre.values());
        return "destinos/formulario";
    }
    
    @PostMapping("/guardar")
    public String guardarDestino(@Valid @ModelAttribute("destino") DestinoDTO destinoDTO, BindingResult bindingResult,Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("destino", destinoDTO);
            model.addAttribute("departamentos", DepartamentoNombre.values());
            return "destinos/formulario";
        }
        try {
            if (destinoDTO.getId()==null) {
                destinoService.saveDestino(destinoDTO);
                redirectAttributes.addFlashAttribute("mensaje","Destino creado correctamente");
            }else{
                destinoService.updateDestino(destinoDTO.getId(), destinoDTO);
                redirectAttributes.addFlashAttribute("mensaje","Destino Actualizado correctamente");
            }
            
            return "redirect:/destinos/lista";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error","Error al guardar: "+e.getMessage());
            if (destinoDTO.getId()==null) {
                return "redirect:/destinos/registro";
            }else{
                return "redirect:/destinos/editar/"+destinoDTO.getId();
            }
        }
    }
    
    @PostMapping("/eliminar")
    public String eliminarDestino(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            boolean eliminado= destinoService.deleteDestinoById(id);
            if (eliminado) {
                redirectAttributes.addFlashAttribute("mensaje","Destino eliminado correctamente");
            }else{
                redirectAttributes.addFlashAttribute("error","Destino no encontrado");
            }            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error","Error al eliminar destion: "+e.getMessage());
        }
        return "redirect:/destinos/lista";
    }
    
    @GetMapping("/listaPorDepartamento")
    public String listarDestinosPorDepartamento(@RequestParam("departamento") String departamentoNombre, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Convierte el String recibido a tu Enum DepartamentoNombre
            DepartamentoNombre depaEnum = DepartamentoNombre.valueOf(departamentoNombre.toUpperCase());
            List<DestinoDTO> destinosFiltrados = destinoService.findDestinosByDepaNombre(depaEnum);
            model.addAttribute("destinos", destinosFiltrados);
            model.addAttribute("departamentoSeleccionado", departamentoNombre); // Para mantener el filtro en la vista
            return "destinos/lista"; // Reutiliza la misma plantilla de lista
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Departamento no válido: " + departamentoNombre);
            return "redirect:/destinos/lista"; // Redirige a la lista completa si el departamento es inválido
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al buscar destinos por departamento: " + e.getMessage());
            return "redirect:/destinos/lista";
        }
    }

}
