package pio.daw.cruddemo.models;

import java.util.List;
import pio.daw.cruddemo.models.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{
    List<Restaurante> findByTipoContainingIgnoreCase(String tipo);

    @Query("SELECT DISTINCT p.restaurante FROM Plato p WHERE p.precio < :precio")
    List<Restaurante> findRestaurantesConPlatosMenorQue(@Param("precio") Double precio);
}
