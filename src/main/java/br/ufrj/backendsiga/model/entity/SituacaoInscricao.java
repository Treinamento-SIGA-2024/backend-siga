package br.ufrj.backendsiga.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "situacao_inscricao")
public class SituacaoInscricao {
    public final static String PENDENTE = "000";
    public final static String ATIVO = "001";
    public final static String RECUSADO = "002";
    public final static String EXPULSO = "003";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 20)
    private String codigo;

    private String descricao;

    @OneToMany(mappedBy = "situacaoInscricao")
    @JsonManagedReference
    private List<InscricaoIC> inscricoesIC;

    @OneToMany(mappedBy = "situacaoInscricao")
    @JsonManagedReference
    private List<InscricaoEstagio> inscricoesEstagio;
}
