package br.ufrj.backendsiga.entity.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "Usuario_Cargo",
            joinColumns = @JoinColumn(name = "Cargo_Id"),
            inverseJoinColumns = @JoinColumn(name = "Usuario_Id"))
    private Set<Usuario> usuarios;
}
