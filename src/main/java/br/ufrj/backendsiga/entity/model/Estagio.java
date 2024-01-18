package br.ufrj.backendsiga.entity.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Estagio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String cargo;

    @Column(length = 500)
    private String descricao;

    @Column(name = "Quantidade_Vagas", nullable = false)
    private Integer quantidadeVagas;

    @Column(name = "Carga_Horaria_Semanal")
    private Integer cargaHorariaSemanal;

    private Double remuneracao;

    @Column(length = 20)
    private String modalidade;

    @Column(nullable = false, length = 50)
    private String empresa;

    @OneToMany(mappedBy = "estagio")
    private Set<InscricaoEstagio> inscricoesEstagio;

}

