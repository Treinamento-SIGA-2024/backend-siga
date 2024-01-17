package br.ufrj.backendsiga.entity.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String email;
    private String senha;

    @ManyToMany
    @JoinTable(
            name = "Usuario_Cargo",
            joinColumns = @JoinColumn(name = "Usuario_Id"),
            inverseJoinColumns = @JoinColumn(name = "Cargo_Id"))
    private Set<Cargo> cargos;
}
