package pio.daw.cruddemo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pio.daw.cruddemo.models.Persona;
import pio.daw.cruddemo.services.PersonaService;

import java.util.List;

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

        return service.getPersonaPorNombre(name);
    }

    @PostMapping("")
    public Persona añadirPersona(@RequestBody Persona entity) {
        return service.crearPersonaSiNoExiste(entity);
    }

    @DeleteMapping("/id/{id}")
    public Persona borrarPorId(@PathVariable String id) {
        return null;
    }

}
