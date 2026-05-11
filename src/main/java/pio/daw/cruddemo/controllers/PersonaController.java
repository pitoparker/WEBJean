package pio.daw.cruddemo.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pio.daw.cruddemo.models.Persona;
import pio.daw.cruddemo.services.PersonaService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/persona")
public class PersonaController {

    @Autowired
    private PersonaService service;

    @GetMapping("")
    public List<Persona> obtenerPersonas(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String rol,
            @RequestParam(required = false) String classroom) {
        return service.buscarPersonas(name, rol, classroom);
    }

    @PostMapping("")
    public Persona añadirPersona(@RequestBody Persona entity) {
        return service.crearPersonaSiNoExiste(entity);
    }

    @DeleteMapping("/{id}")
    public Persona borrarPorId(@PathVariable String id) {
        return null;
    }

    @GetMapping("/fechas")
    public List<Persona> buscarEntreFechas(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate inicio,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fin) {
        return service.buscarEntreFechas(inicio, fin);
    }

    @PatchMapping("/{id}/clase")
    public Optional<Persona> cambiarClase(@PathVariable Long id, @RequestParam String clase) {
        return service.cambiarClase(id, clase);
    }

    @DeleteMapping("")
    public void borrarPorRolYClase(@RequestParam String rol, @RequestParam String classroom) {
        service.borrarPorRolYClase(rol, classroom);
    }

}
