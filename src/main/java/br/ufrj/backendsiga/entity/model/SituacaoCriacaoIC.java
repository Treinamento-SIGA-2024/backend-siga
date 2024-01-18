package br.ufrj.backendsiga.entity.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity(name = "situacao_criacao_ic")
public class SituacaoCriacaoIC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 20)
    private String codigo;

    private String descricao;

    @OneToMany(mappedBy = "situacaoCriacao")
    private Set<IniciacaoCientifica> iniciacoesCientificas;


}



