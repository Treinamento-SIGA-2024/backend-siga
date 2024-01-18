package br.ufrj.backendsiga.entity.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 30)
    private String matricula;

    @Column(nullable = false, length = 40)
    private String nome;

    @Column(unique = true, nullable = false, length = 40)
    private String email;

    @Column(nullable = false, length = 40)
    private String senha;

    @OneToMany(mappedBy = "coordenador")
    private Set<IniciacaoCientifica> iniciacoesCientificas;

    @OneToMany(mappedBy = "aluno")
    private Set<InscricaoIC> inscricoesIC;

    @OneToMany(mappedBy = "aluno")
    private Set<InscricaoEstagio> inscricoesEstagio;

    @ManyToMany
    @JoinTable(
            name = "Usuario_Cargo",
            joinColumns = @JoinColumn(name = "Usuario_Id"),
            inverseJoinColumns = @JoinColumn(name = "Cargo_Id")
    )
    private Set<Cargo> cargos;
}
