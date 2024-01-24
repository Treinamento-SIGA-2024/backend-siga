package br.ufrj.backendsiga.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "situacao_criacao_ic")
public class SituacaoCriacaoIC {
    public final static String PENDENTE = "000";
    public final static String ACEITA = "001";
    public final static String RECUSADA = "002";

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



