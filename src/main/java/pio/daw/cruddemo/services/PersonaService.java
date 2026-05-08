package pio.daw.cruddemo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pio.daw.cruddemo.models.Persona;
import pio.daw.cruddemo.repositories.PersonaRepository;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository repo;

    public List<Persona> getPersonaPorNombre(String nombre){
        List<Persona> result = new ArrayList<>();
        System.out.println("NOMBRE: " + nombre);        

        if(nombre == null || nombre.isEmpty()){
            for(Persona p :repo.findAll()){
                result.add(p);
            }
        }
        else{
            if(nombre.startsWith("\"")) nombre = nombre.substring(1,nombre.length()-1);
            result = repo.findByName(nombre);
        }
        return result;
    }

    public List<Persona> getPersonasEnClase(String clase){
        return repo.findByClassroomOrderByBirthDate(clase);
    }

    public Persona crearPersonaSiNoExiste(Persona p){
        Persona result = repo.findFirstByName(p.getName()).orElse(null);
        if (result == null){
            result = repo.save(p);
        }
        return result;
    }

    public void borrarPersona(String nombre){
        
    }
    
    public void borrarPersona(Long id){
        
    }

    public Persona cambiarRol(String nombre, String rol){
        return null;

    }

    public Persona cambiarClase(String nombre, String clase){
        return null;

    }

    
}
