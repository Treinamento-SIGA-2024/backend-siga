package br.ufrj.backendsiga.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "iniciacao_cientifica")
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

    @OneToMany(mappedBy = "iniciacaoCientifica")
    @JsonManagedReference
    private List<InscricaoIC> inscricoes;

    @ManyToMany(mappedBy = "iniciacoesCientificas")
    @JsonIgnoreProperties("iniciacoesCientificas")
    private List<Topico> topicos;

    @ManyToMany
    @JoinTable(
            name = "r_professor_ic",
            joinColumns = @JoinColumn(name = "ic_id"),
            inverseJoinColumns = @JoinColumn(name = "professor_id")
    )
    @JsonIgnoreProperties("iniciacoesCientificas")
    private List<Usuario> professores;

    @ManyToOne
    @JoinColumn(name = "situacao_criacao_id", nullable = false)
    @JsonBackReference
    private SituacaoCriacaoIC situacaoCriacao;

    @ManyToOne
    @JoinColumn(name = "coordenador_avaliador_id")
    @JsonBackReference
    private Usuario coordenadorAvaliador;
}