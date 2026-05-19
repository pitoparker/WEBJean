package pio.daw.cruddemo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pio.daw.cruddemo.models.Plato;
import pio.daw.cruddemo.models.Restaurante;
public interface PlatoRepository extends JpaRepository<Plato, Long>{
    
    List<Plato> findByRestaurante(Restaurante restaurante);
    List<Plato> findByRestauranteAndCategoria(Restaurante restaurante, String categoria);
    List<Plato> findByVegetariano(Boolean vegetariano);
    List<Plato> findByPrecioBetween(Double precioMin, Double precioMax);
    @Query("SELECT AVG(p.precio) FROM Plato p WHERE p.restaurante = :restaurante")
    Double calcularPrecioMedio(@Param("restaurante") Restaurante restaurante);
    //repositorio interfazse va implementar rar
}
