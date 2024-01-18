package br.ufrj.backendsiga.entity.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity(name = "Iniciacao_Cientifica")
public class IniciacaoCientifica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(length = 500)
    private String descricao;

    private Double remuneracao;

    @Column(name = "Carga_Horaria_Semanal")
    private Integer cargaHorariaSemanal;

    @ManyToOne
    @JoinColumn(name = "Coordenador_Id")
    private Usuario coordenador;

    @OneToMany(mappedBy = "iniciacaoCientifica")
    private Set<InscricaoIC> inscricoes;

    @ManyToMany
    @JoinTable(
            name = "IC_Topico",
            joinColumns = @JoinColumn(name = "Iniciacao_Cientifica_Id"),
            inverseJoinColumns = @JoinColumn(name = "Topico_Id")
    )
    private Set<Topico> topicos;

    @ManyToOne
    @JoinColumn(name = "Situacao_Criacao_Id", nullable = false)
    private SituacaoCriacaoIC situacaoCriacao;

}