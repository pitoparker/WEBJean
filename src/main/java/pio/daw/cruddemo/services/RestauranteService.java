package pio.daw.cruddemo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pio.daw.cruddemo.models.Plato;
import pio.daw.cruddemo.models.Restaurante;
import pio.daw.cruddemo.repositories.PlatoRepository;
import pio.daw.cruddemo.repositories.RestauranteRepository;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepo;

    @Autowired
    private PlatoRepository platoRepo;

    // ── RESTAURANTE ──────────────────────────────────────────────────────

    public List<Restaurante> findAll() {
        return restauranteRepo.findAll();
    }

    public List<Restaurante> findByTipo(String tipo) {
        return restauranteRepo.findByTipoContainingIgnoreCase(tipo);
    }

    public Optional<Restaurante> findById(Long id) {
        return restauranteRepo.findById(id);
    }

    public Restaurante save(Restaurante r) {
        return restauranteRepo.save(r);
    }

    public void deleteById(Long id) {
        restauranteRepo.deleteById(id);
    }

    // Operación 4: restaurantes con algún plato por debajo de un precio
    public List<Restaurante> findConPlatosBaratos(Double precio) {
        return restauranteRepo.findRestaurantesConPlatosMenorQue(precio);
    }

    // ── PLATO ────────────────────────────────────────────────────────────

    public List<Plato> findAllPlatos() {
        return platoRepo.findAll();
    }

    public Optional<Plato> findPlatoById(Long id) {
        return platoRepo.findById(id);
    }

    public Plato savePlato(Plato p) {
        return platoRepo.save(p);
    }

    public void deletePlatoById(Long id) {
        platoRepo.deleteById(id);
    }

    public List<Plato> findPlatosByRestaurante(Restaurante r) {
        return platoRepo.findByRestaurante(r);
    }

    // Operación 1: platos por categoría
    public List<Plato> findPlatosByCategoria(Restaurante r, String categoria) {
        return platoRepo.findByRestauranteAndCategoria(r, categoria);
    }

    // Operación 2a: vegetarianos
    public List<Plato> findVegetarianos() {
        return platoRepo.findByVegetariano(true);
    }

    // Operación 2b: rango de precio
    public List<Plato> findByRangoPrecio(Double min, Double max) {
        return platoRepo.findByPrecioBetween(min, max);
    }

    // Operación 3: precio medio del menú
    public Double calcularPrecioMedio(Restaurante r) {
        Double avg = platoRepo.calcularPrecioMedio(r);
        return avg != null ? avg : 0.0;
    }
}
