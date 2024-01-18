package br.ufrj.backendsiga.entity.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity(name = "situacao_inscricao")
public class SituacaoInscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 20)
    private String codigo;

    private String descricao;

    @OneToMany(mappedBy = "situacaoInscricao")
    private Set<InscricaoIC> inscricoesICs;

}
