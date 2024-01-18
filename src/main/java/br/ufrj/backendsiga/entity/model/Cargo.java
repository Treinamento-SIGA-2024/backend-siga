package br.ufrj.backendsiga.entity.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity(name = "cargo")
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 40)
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "r_usuario_cargo",
            joinColumns = @JoinColumn(name = "cargo_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> usuarios;
}
