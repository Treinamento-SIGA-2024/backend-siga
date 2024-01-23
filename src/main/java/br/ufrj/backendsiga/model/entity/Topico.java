package br.ufrj.backendsiga.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "topico")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "r_ic_topico",
            joinColumns = @JoinColumn(name = "topico_id"),
            inverseJoinColumns = @JoinColumn(name = "ic_id")
    )
    @JsonIgnoreProperties("topicos")
    private List<IniciacaoCientifica> iniciacoesCientificas;
}
