package pio.daw.cruddemo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pio.daw.cruddemo.models.Plato;
import pio.daw.cruddemo.models.Restaurante;
import pio.daw.cruddemo.services.RestauranteService;

@RestController
@RequestMapping("/api/restaurante")
public class RestauranteApiController {

    @Autowired
    private RestauranteService service;

    // ── RESTAURANTES ─────────────────────────────────────────────────────

    /** GET /api/restaurante?tipo=italiano */
    @GetMapping("")
    public List<Restaurante> listar(@RequestParam(required = false) String tipo) {
        if (tipo != null && !tipo.isBlank())
            return service.findByTipo(tipo);
        return service.findAll();
    }

    /** GET /api/restaurante/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> obtener(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** POST /api/restaurante */
    @PostMapping("")
    public Restaurante crear(@RequestBody Restaurante r) {
        return service.save(r);
    }

    /** PUT /api/restaurante/{id} */
    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> actualizar(@PathVariable Long id,
                                                   @RequestBody Restaurante datos) {
        return service.findById(id).map(r -> {
            r.setNombre(datos.getNombre());
            r.setCiudad(datos.getCiudad());
            r.setTipo(datos.getTipo());
            r.setEstrellas(datos.getEstrellas());
            return ResponseEntity.ok(service.save(r));
        }).orElse(ResponseEntity.notFound().build());
    }

    /** DELETE /api/restaurante/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        if (service.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ── OPERACIONES ESPECÍFICAS ───────────────────────────────────────────

    /** GET /api/restaurante/{id}/platos?categoria=entrante */
    @GetMapping("/{id}/platos")
    public ResponseEntity<List<Plato>> platosPorCategoria(
            @PathVariable Long id,
            @RequestParam(required = false) String categoria) {
        return service.findById(id).map(r -> {
            List<Plato> platos = (categoria != null && !categoria.isBlank())
                    ? service.findPlatosByCategoria(r, categoria)
                    : service.findPlatosByRestaurante(r);
            return ResponseEntity.ok(platos);
        }).orElse(ResponseEntity.notFound().build());
    }

    /** GET /api/restaurante/{id}/precio-medio */
    @GetMapping("/{id}/precio-medio")
    public ResponseEntity<Double> precioMedio(@PathVariable Long id) {
        return service.findById(id)
                .map(r -> ResponseEntity.ok(service.calcularPrecioMedio(r)))
                .orElse(ResponseEntity.notFound().build());
    }

    /** GET /api/restaurante/baratos?precio=10 */
    @GetMapping("/baratos")
    public List<Restaurante> baratos(@RequestParam Double precio) {
        return service.findConPlatosBaratos(precio);
    }

    // ── PLATOS ───────────────────────────────────────────────────────────

    /**
     * GET /api/restaurante/platos
     * Filtros opcionales: ?vegetariano=true  /  ?precioMin=5&precioMax=20
     */
    @GetMapping("/platos")
    public List<Plato> listarPlatos(
            @RequestParam(required = false) Boolean vegetariano,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax) {
        if (Boolean.TRUE.equals(vegetariano))   return service.findVegetarianos();
        if (precioMin != null && precioMax != null) return service.findByRangoPrecio(precioMin, precioMax);
        return service.findAllPlatos();
    }

    /** GET /api/restaurante/platos/{id} */
    @GetMapping("/platos/{id}")
    public ResponseEntity<Plato> obtenerPlato(@PathVariable Long id) {
        return service.findPlatoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/restaurante/platos
     * Body: { "nombre":"...", "precio":12.5, "categoria":"principal",
     *         "vegetariano":false, "restaurante":{"id":1} }
     */
    @PostMapping("/platos")
    public ResponseEntity<Plato> crearPlato(@RequestBody Plato plato) {
        if (plato.getRestaurante() == null || plato.getRestaurante().getId() == null)
            return ResponseEntity.badRequest().build();
        Optional<Restaurante> r = service.findById(plato.getRestaurante().getId());
        if (r.isEmpty()) return ResponseEntity.notFound().build();
        plato.setRestaurante(r.get());
        return ResponseEntity.ok(service.savePlato(plato));
    }

    /** PUT /api/restaurante/platos/{id} */
    @PutMapping("/platos/{id}")
    public ResponseEntity<Plato> actualizarPlato(@PathVariable Long id,
                                                  @RequestBody Plato datos) {
        return service.findPlatoById(id).map(p -> {
            p.setNombre(datos.getNombre());
            p.setDescripcion(datos.getDescripcion());
            p.setPrecio(datos.getPrecio());
            p.setCategoria(datos.getCategoria());
            p.setVegetariano(datos.getVegetariano());
            return ResponseEntity.ok(service.savePlato(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    /** DELETE /api/restaurante/platos/{id} */
    @DeleteMapping("/platos/{id}")
    public ResponseEntity<Void> borrarPlato(@PathVariable Long id) {
        if (service.findPlatoById(id).isEmpty()) return ResponseEntity.notFound().build();
        service.deletePlatoById(id);
        return ResponseEntity.noContent().build();
    }
}