package pio.daw.cruddemo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pio.daw.cruddemo.models.Plato;
import pio.daw.cruddemo.models.Restaurante;
import pio.daw.cruddemo.services.RestauranteService;

@Controller
@RequestMapping("/web/restaurantes")
public class RestauranteWebController {

    @Autowired
    private RestauranteService service;

    // ── RESTAURANTES ─────────────────────────────────────────────────────

    /** GET /web/restaurantes?tipo=italiano */
    @GetMapping("")
    public String lista(@RequestParam(required = false) String tipo, Model model) {
        List<Restaurante> lista = (tipo != null && !tipo.isBlank())
                ? service.findByTipo(tipo)
                : service.findAll();
        model.addAttribute("restaurantes", lista);
        model.addAttribute("tipo", tipo);
        return "restaurantes/lista";
    }

    /** GET /web/restaurantes/{id} — detalle con platos */
    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id,
                          @RequestParam(required = false) String categoria,
                          Model model) {
        Optional<Restaurante> opt = service.findById(id);
        if (opt.isEmpty()) return "redirect:/web/restaurantes";
        Restaurante r = opt.get();
        List<Plato> platos = (categoria != null && !categoria.isBlank())
                ? service.findPlatosByCategoria(r, categoria)
                : service.findPlatosByRestaurante(r);
        model.addAttribute("restaurante", r);
        model.addAttribute("platos", platos);
        model.addAttribute("precioMedio", service.calcularPrecioMedio(r));
        model.addAttribute("categoriaFiltro", categoria);
        return "restaurantes/detalle";
    }

    /** GET /web/restaurantes/nueva */
    @GetMapping("/nueva")
    public String nuevaForm(Model model) {
        model.addAttribute("restaurante", new Restaurante());
        return "restaurantes/nueva";
    }

    /** POST /web/restaurantes/nueva */
    @PostMapping("/nueva")
    public String guardar(@ModelAttribute Restaurante restaurante) {
        service.save(restaurante);
        return "redirect:/web/restaurantes";
    }

    /** GET /web/restaurantes/editar/{id} */
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Optional<Restaurante> opt = service.findById(id);
        if (opt.isEmpty()) return "redirect:/web/restaurantes";
        model.addAttribute("restaurante", opt.get());
        return "restaurantes/editar";
    }

    /** POST /web/restaurantes/editar/{id} */
    @PostMapping("/editar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Restaurante datos) {
        service.findById(id).ifPresent(r -> {
            r.setNombre(datos.getNombre());
            r.setCiudad(datos.getCiudad());
            r.setTipo(datos.getTipo());
            r.setEstrellas(datos.getEstrellas());
            service.save(r);
        });
        return "redirect:/web/restaurantes";
    }

    /** POST /web/restaurantes/borrar/{id} */
    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/web/restaurantes";
    }

    // ── PLATOS ───────────────────────────────────────────────────────────

    /** GET /web/restaurantes/{id}/platos/nuevo */
    @GetMapping("/{restauranteId}/platos/nuevo")
    public String nuevoPlatoForm(@PathVariable Long restauranteId, Model model) {
    Optional<Restaurante> opt = service.findById(restauranteId);
    if (opt.isEmpty()) return "redirect:/web/restaurantes";
    model.addAttribute("plato", new Plato());
    model.addAttribute("restaurante", opt.get());
    return "platos/nuevo";
    }

    /** POST /web/restaurantes/{id}/platos/nuevo */
    @PostMapping("/{restauranteId}/platos/nuevo")
    public String guardarPlato(@PathVariable Long restauranteId, @ModelAttribute Plato plato) {
    service.findById(restauranteId).ifPresent(r -> {
        plato.setRestaurante(r);
        service.savePlato(plato);
    });
    return "redirect:/web/restaurantes/" + restauranteId;
    }

    /** GET /web/restaurantes/platos/editar/{id} */
    @GetMapping("/platos/editar/{id}")
    public String editarPlatoForm(@PathVariable Long id, Model model) {
        Optional<Plato> opt = service.findPlatoById(id);
        if (opt.isEmpty()) return "redirect:/web/restaurantes";
        model.addAttribute("plato", opt.get());
        return "platos/editar";
    }

    /** POST /web/restaurantes/platos/editar/{id} */
    @PostMapping("/platos/editar/{id}")
    public String actualizarPlato(@PathVariable Long id, @ModelAttribute Plato datos) {
        Optional<Plato> opt = service.findPlatoById(id);
        opt.ifPresent(p -> {
            p.setNombre(datos.getNombre());
            p.setDescripcion(datos.getDescripcion());
            p.setPrecio(datos.getPrecio());
            p.setCategoria(datos.getCategoria());
            p.setVegetariano(datos.getVegetariano());
            service.savePlato(p);
        });
        Long rid = opt.map(p -> p.getRestaurante().getId()).orElse(null);
        return rid != null ? "redirect:/web/restaurantes/" + rid : "redirect:/web/restaurantes";
    }

    /** POST /web/restaurantes/platos/borrar/{id} */
    @PostMapping("/platos/borrar/{id}")
    public String borrarPlato(@PathVariable Long id) {
        Optional<Plato> opt = service.findPlatoById(id);
        Long rid = opt.map(p -> p.getRestaurante().getId()).orElse(null);
        service.deletePlatoById(id);
        return rid != null ? "redirect:/web/restaurantes/" + rid : "redirect:/web/restaurantes";
    }
}