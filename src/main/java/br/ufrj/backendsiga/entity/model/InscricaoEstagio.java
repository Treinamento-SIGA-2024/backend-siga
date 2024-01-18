package br.ufrj.backendsiga.entity.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "Inscricao_Estagio")
public class InscricaoEstagio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Estagio_Id", nullable = false)
    private Estagio estagio;

    @ManyToOne
    @JoinColumn(name = "Aluno_Id", nullable = false)
    private Usuario aluno;

    @ManyToOne
    @JoinColumn(name = "Coordenador_Id")
    private Usuario coordenador;

    @ManyToOne
    @JoinColumn(name = "Situacao_Inscricao_Id", nullable = false)
    private SituacaoInscricao situacaoInscricao;
}
