package pio.daw.cruddemo.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@Table(name = "restaurantes")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String ciudad;

    // Tipo de cocina: italiano, español, japonés...
    private String tipo;

    // 0 = sin estrella Michelin, 1-3 = estrellas Michelin
    private Integer estrellas;

    // @ToString.Exclude evita bucle infinito al imprimir (Restaurante → Plato → Restaurante...)
    @ToString.Exclude
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plato> platos = new ArrayList<>();
}