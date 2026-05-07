package pio.daw.cruddemo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pio.daw.cruddemo.models.Persona;
import pio.daw.cruddemo.repositories.PersonaRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController
@RequestMapping("/persona")
public class PersonaController {

    @Autowired
    private PersonaRepository repo;

    @GetMapping("/id/{id}")
    public Persona obtenerPersonaPorID(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @GetMapping("/name/{name}")
    public List<Persona> obtenerPersonas(@PathVariable String name) {
        return repo.getByName(name);
    }

    @PostMapping("")
    public Persona añadirPersona(@RequestBody Persona entity) {
        repo.save(entity);
        return entity;
    }
    
    
    
    
}
