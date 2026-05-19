package pio.daw.cruddemo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pio.daw.cruddemo.models.Restaurante;


public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{
    List<Restaurante> findByTipoContainingIgnoreCase(String tipo);

    @Query("SELECT DISTINCT p.restaurante FROM Plato p WHERE p.precio < :precio")
    List<Restaurante> findRestaurantesConPlatosMenorQue(@Param("precio") Double precio);
}
