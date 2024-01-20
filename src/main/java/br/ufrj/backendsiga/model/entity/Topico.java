package br.ufrj.backendsiga.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity(name = "topico")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String nome;

    @ManyToMany(mappedBy = "topicos")
    private Set<IniciacaoCientifica> iniciacoesCientificas;
}
