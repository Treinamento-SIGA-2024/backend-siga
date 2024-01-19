package br.ufrj.backendsiga.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "situacao_inscricao")
public class SituacaoInscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 20)
    private String codigo;

    private String descricao;

    @OneToMany(mappedBy = "situacaoInscricao")
    @JsonManagedReference
    private List<InscricaoIC> inscricoesIC;

    @OneToMany(mappedBy = "situacaoInscricao")
    @JsonManagedReference
    private List<InscricaoEstagio> inscricoesEstagio;
}
