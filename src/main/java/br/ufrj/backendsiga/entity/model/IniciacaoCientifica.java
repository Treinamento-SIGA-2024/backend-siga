package br.ufrj.backendsiga.entity.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity(name = "iniciacao_cientifica")
public class IniciacaoCientifica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(length = 500)
    private String descricao;

    private Double remuneracao;

    @Column(name = "carga_horaria_semanal")
    private Integer cargaHorariaSemanal;

    @ManyToOne
    @JoinColumn(name = "coordenador_avaliador_id")
    private Usuario coordenador;

    @OneToMany(mappedBy = "iniciacaoCientifica")
    private Set<InscricaoIC> inscricoes;

    @ManyToMany
    @JoinTable(
            name = "r_ic_topico",
            joinColumns = @JoinColumn(name = "ic_id"),
            inverseJoinColumns = @JoinColumn(name = "topico_id")
    )
    private Set<Topico> topicos;

    @ManyToOne
    @JoinColumn(name = "situacao_criacao_id", nullable = false)
    private SituacaoCriacaoIC situacaoCriacao;

}