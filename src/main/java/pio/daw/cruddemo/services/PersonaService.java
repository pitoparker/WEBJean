package pio.daw.cruddemo.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pio.daw.cruddemo.models.Persona;
import pio.daw.cruddemo.models.Rol;
import pio.daw.cruddemo.repositories.PersonaRepository;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository repo;

    public List<Persona> buscarPersonas(String name, String rol, String classroom) {
        boolean hasName = name != null;
        boolean hasRol = rol != null;
        boolean hasClassroom = classroom != null;

        if (hasName && hasRol && hasClassroom)
            return repo.findByNameStartingWithAndRolAndClassroom(name, rol, classroom);
        if (hasName && hasRol)
            return repo.findByNameStartingWithAndRol(name, rol);
        if (hasName && hasClassroom)
            return repo.findByNameStartingWithAndClassroom(name, classroom);
        if (hasRol && hasClassroom)
            return repo.findByRolAndClassroom(rol, classroom);
        if (hasName)
            return repo.findByNameStartingWith(name);
        if (hasRol)
            return repo.findByRol(rol);
        if (hasClassroom)
            return repo.findByClassroom(classroom);

        List<Persona> todas = new ArrayList<>();
        repo.findAll().forEach(todas::add);
        return todas;
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

    public Optional<Persona> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Optional<Persona> cambiarRol(Long id, String rol) {
        return repo.findById(id)
                .map(p -> {
                    p.setRol(Rol.valueOf(rol));
                    return repo.save(p);
                });
    }

    public List<Persona> buscarEntreFechas(LocalDate inicio, LocalDate fin) {
        return repo.findByBirthDateBetween(inicio, fin);
    }

    @Transactional
    public void borrarPorRolYClase(String rol, String classroom) {
        repo.deleteByRolAndClassroom(rol, classroom);
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
