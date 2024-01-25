package br.ufrj.backendsiga.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "cargo")
public class Cargo {
    public final static String ALUNO = "Aluno";
    public final static String PROFESSOR = "Professor";
    public final static String COORDENADOR = "Coordenador";
    public final static String SECRETARIO = "Secretario";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", unique = true, nullable = false, length = 40)
    private String nome;

    
    @ManyToMany
    @JsonIgnoreProperties("cargos")
    @JoinTable(
            name = "r_usuario_cargo",
            joinColumns = @JoinColumn(name = "cargo_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuarios;
}
