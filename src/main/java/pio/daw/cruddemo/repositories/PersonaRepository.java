package pio.daw.cruddemo.repositories;

import org.springframework.data.repository.CrudRepository;

import pio.daw.cruddemo.models.Persona;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;


public interface PersonaRepository extends CrudRepository<Persona,Long> {
    List<Persona> getByBirthDateOrderByRol(LocalDate birthDate, String rol);
    void deleteByRol(String rol);
    Optional<Persona> findFirstByName(String name);
    List<Persona> findByClassroomOrderByBirthDate(String classroom);
    List<Persona> findByName(String name);
    
}
