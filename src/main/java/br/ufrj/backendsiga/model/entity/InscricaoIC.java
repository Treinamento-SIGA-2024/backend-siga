package br.ufrj.backendsiga.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "inscricao_ic")
public class InscricaoIC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    @JsonBackReference
    private Usuario aluno;

    @ManyToOne
    @JoinColumn(name = "ic_id", nullable = false)
    @JsonBackReference
    private IniciacaoCientifica iniciacaoCientifica;

    @ManyToOne
    @JoinColumn(name = "professor_avaliador_id")
    @JsonBackReference
    private Usuario professorAvaliador;

    @ManyToOne
    @JoinColumn(name = "situacao_id", nullable = false)
    @JsonBackReference
    private SituacaoInscricao situacaoInscricao;
}
