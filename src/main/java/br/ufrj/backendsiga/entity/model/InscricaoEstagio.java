package br.ufrj.backendsiga.entity.model;

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
    private Estagio estagio;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Usuario aluno;

    @ManyToOne
    @JoinColumn(name = "coordenador_avaliador_id")
    private Usuario coordenadorAvaliador;

    @ManyToOne
    @JoinColumn(name = "situacao_id", nullable = false)
    private SituacaoInscricao situacaoInscricao;
}
