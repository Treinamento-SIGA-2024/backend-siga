package br.ufrj.backendsiga.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "inscricao_estagio")
public class InscricaoEstagio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "estagio_id", nullable = false)
    @JsonBackReference
    private Estagio estagio;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    @JsonBackReference
    private Usuario aluno;

    @ManyToOne
    @JoinColumn(name = "coordenador_avaliador_id")
    @JsonBackReference
    private Usuario coordenadorAvaliador;

    @ManyToOne
    @JoinColumn(name = "situacao_id", nullable = false)
    @JsonBackReference
    private SituacaoInscricao situacaoInscricao;
}
