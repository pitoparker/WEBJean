package pio.daw.cruddemo.repositories;

import org.springframework.data.repository.CrudRepository;

import pio.daw.cruddemo.models.Persona;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;



public interface PersonaRepository extends CrudRepository<Persona,Long> {
    Optional<Persona> findFirstByName(String name);
    List<Persona> findByNameStartingWith(String name);
    List<Persona> findByRol(String rol);
    List<Persona> findByClassroom(String classroom);
    List<Persona> findByRolAndClassroom(String rol, String classroom); 
    List<Persona> findByBirthDateBetween(LocalDate start, LocalDate end);

    long countByRol(String rol);
    long countByClassroom(String classroom);

    void deleteByRol(String rol);
    void deleteByClassroom(String classroom);
    void deleteByRolAndClassroom(String rol, String classroom);

}
