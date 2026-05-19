package pio.daw.cruddemo.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "platos")
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;
    private Double precio;

    // entrante, principal, postre, bebida
    private String categoria;

    private Boolean vegetariano;

    // Lado propietario de la relación — guarda la FK restaurante_id en la tabla platos
    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;
}