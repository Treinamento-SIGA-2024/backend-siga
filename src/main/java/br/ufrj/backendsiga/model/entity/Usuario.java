package br.ufrj.backendsiga.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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
    @JsonIgnoreProperties("usuarios")
    @JoinTable(
            name = "r_usuario_cargo",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "cargo_id")
    )
    private List<Cargo> cargos;

    //Informações de aluno
    @OneToMany(mappedBy = "aluno")
    private List<InscricaoIC> inscricoesIC;

    @OneToMany(mappedBy = "aluno")
    private List<InscricaoEstagio> inscricoesEstagio;

    //Informações de coordenador
    @OneToMany(mappedBy = "coordenadorAvaliador")
    List<IniciacaoCientifica> iniciacoesCientificasAvaliadas;

    @OneToMany(mappedBy = "coordenadorAvaliador")
    List<InscricaoEstagio> inscricoesEstagioAvaliadas;

    //Informações de professor
    @ManyToMany(mappedBy = "professores")
    private List<IniciacaoCientifica> iniciacoesCientificas;

    @OneToMany(mappedBy = "professor")
    private List<InscricaoIC> inscricoesICAvaliadas;

}
