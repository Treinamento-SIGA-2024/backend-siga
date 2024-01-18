package br.ufrj.backendsiga.entity.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //TIRAR DEPOIS HEIN
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

    @ManyToMany
    @JoinTable(
            name = "r_usuario_cargo",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "cargo_id")
    )
    private Set<Cargo> cargos;

    //Informações de aluno
    @OneToMany(mappedBy = "aluno")
    private Set<InscricaoIC> inscricoesIC;

    @OneToMany(mappedBy = "aluno")
    private Set<InscricaoEstagio> inscricoesEstagio;

    //Informações de coordenador
    @OneToMany(mappedBy = "coordenadorAvaliador")
    Set<IniciacaoCientifica> iniciacoesCientificasAvaliadas;

    @OneToMany(mappedBy = "coordenadorAvaliador")
    Set<InscricaoEstagio> inscricoesEstagioAvaliadas;

    //Informações de professor
    @ManyToMany(mappedBy = "professores")
    private Set<IniciacaoCientifica> iniciacoesCientificas;

    @OneToMany(mappedBy = "professor")
    private Set<InscricaoIC> inscricoesICAvaliadas;

}
