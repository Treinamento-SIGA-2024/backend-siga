package br.ufrj.backendsiga.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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
    @JsonManagedReference
    private List<IniciacaoCientifica> iniciacoesCientificas;


}



