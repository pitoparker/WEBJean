package pio.daw.cruddemo.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pio.daw.cruddemo.models.Persona;
import pio.daw.cruddemo.models.Rol;
import pio.daw.cruddemo.services.PersonaService;

@Controller
@RequestMapping("/web")
public class PersonaWebController {

    @Autowired
    private PersonaService service;

    @GetMapping
    public String lista(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String rol,
            @RequestParam(required = false) String classroom,
            Model model) {
        String filtroName = (name != null && name.isBlank()) ? null : name;
        String filtroRol = (rol != null && rol.isBlank()) ? null : rol;
        String filtroClassroom = (classroom != null && classroom.isBlank()) ? null : classroom;

        List<Persona> personas = service.buscarPersonas(filtroName, filtroRol, filtroClassroom);
        model.addAttribute("personas", personas);
        model.addAttribute("roles", Rol.values());
        model.addAttribute("name", name);
        model.addAttribute("rol", rol);
        model.addAttribute("classroom", classroom);
        return "personas/lista";
    }

    @GetMapping("/nueva")
    public String nuevaForm(Model model) {
        model.addAttribute("roles", Rol.values());
        return "personas/nueva";
    }

    @PostMapping("/nueva")
    public String crearPersona(
            @RequestParam String name,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @RequestParam String rol,
            @RequestParam String classroom) {
        Persona p = new Persona();
        p.setName(name);
        p.setBirthDate(birthDate);
        p.setRol(Rol.valueOf(rol));
        p.setClassroom(classroom);
        service.crearPersonaSiNoExiste(p);
        return "redirect:/web";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        service.buscarPorId(id).ifPresent(p -> model.addAttribute("persona", p));
        return "personas/editar";
    }

    @PostMapping("/editar/{id}")
    public String actualizarClase(
            @PathVariable Long id,
            @RequestParam String classroom) {
        service.cambiarClase(id, classroom);
        return "redirect:/web";
    }

    @PostMapping("/borrar/{id}")
    public String borrarPorId(@PathVariable Long id) {
        service.borrarPersona(id);
        return "redirect:/web";
    }

    @GetMapping("/fechas")
    public String fechasForm(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
            Model model) {
        if (inicio != null && fin != null) {
            model.addAttribute("personas", service.buscarEntreFechas(inicio, fin));
            model.addAttribute("inicio", inicio);
            model.addAttribute("fin", fin);
        }
        return "personas/fechas";
    }

    @PostMapping("/borrar-grupo")
    public String borrarGrupo(
            @RequestParam String rol,
            @RequestParam String classroom) {
        service.borrarPorRolYClase(rol, classroom);
        return "redirect:/web";
    }
}
