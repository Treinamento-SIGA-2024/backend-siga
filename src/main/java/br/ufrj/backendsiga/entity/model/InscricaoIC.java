package br.ufrj.backendsiga.entity.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "Inscricao_IC")
public class InscricaoIC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Aluno_Id", nullable = false)
    private Usuario aluno;

    @ManyToOne
    @JoinColumn(name = "IC_Id", nullable = false)
    private IniciacaoCientifica iniciacaoCientifica;

    @ManyToOne
    @JoinColumn(name = "Professor_Id")
    private Usuario professor;

    @ManyToOne
    @JoinColumn(name = "Situacao_Inscricao_Id", nullable = false)
    private SituacaoInscricao situacaoInscricao;
}
