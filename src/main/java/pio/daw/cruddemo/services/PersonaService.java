package pio.daw.cruddemo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pio.daw.cruddemo.models.Persona;
import pio.daw.cruddemo.repositories.PersonaRepository;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository repo;

    public List<Persona> getPersonaPorNombre(String nombre) {
        if(nombre == null) nombre = "";
        return repo.findByNameStartingWith(nombre);
    }

    public List<Persona> getPersonasEnClase(String clase) {
        return repo.findByClassroom(clase);
    }

    public Persona crearPersonaSiNoExiste(Persona p) {
        return repo.findFirstByName(p.getName())
                .orElse(repo.save(p));
    }

    public Optional<Long> borrarPersona(String nombre) {
        return repo.findFirstByName(nombre)
                .map(p -> {
                    Long id = p.getId();
                    repo.delete(p);
                    return id;
                });
    }

    public Optional<Long> borrarPersona(Long id) {
        return repo.findById(id)
                .map(p -> {
                    repo.delete(p);
                    return id;
                });
    }

    public Optional<Persona> cambiarRol(Long id, String rol) {
        return repo.findById(id)
                .map(p -> {
                    p.setRol(rol);
                    return repo.save(p);
                });
    }

    public Optional<Persona> cambiarClase(Long id, String clase) {
        return repo.findById(id)
                .map(p -> {
                    p.setClassroom(clase);
                    ;
                    return repo.save(p);
                });
    }

}
