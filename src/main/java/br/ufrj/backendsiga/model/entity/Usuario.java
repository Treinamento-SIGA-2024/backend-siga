package br.ufrj.backendsiga.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 30)
    private String matricula;

    @Column(unique = true, nullable = false, length = 40)
    private String email;

    @Column(nullable = false, length = 40)
    private String nome;

    @Column(nullable = false, length = 40)
    private String senha;

    @ManyToMany(mappedBy = "usuarios")
    @JsonIgnoreProperties("usuarios")
    private List<Cargo> cargos;

    //Informações de aluno
    @OneToMany(mappedBy = "aluno")
    @JsonManagedReference
    private List<InscricaoIC> inscricoesIC;

    @OneToMany(mappedBy = "aluno")
    @JsonManagedReference
    private List<InscricaoEstagio> inscricoesEstagio;

    //Informações de coordenador
    @OneToMany(mappedBy = "coordenadorAvaliador")
    @JsonManagedReference
    List<IniciacaoCientifica> iniciacoesCientificasAvaliadas;

    @OneToMany(mappedBy = "coordenadorAvaliador")
    @JsonManagedReference
    List<InscricaoEstagio> inscricoesEstagioAvaliadas;

    //Informações de professor
    @ManyToMany(mappedBy = "professores")
    @JsonIgnoreProperties("professores")
    private List<IniciacaoCientifica> iniciacoesCientificas;

    @OneToMany(mappedBy = "professorAvaliador")
    @JsonManagedReference
    private List<InscricaoIC> inscricoesICAvaliadas;

}
