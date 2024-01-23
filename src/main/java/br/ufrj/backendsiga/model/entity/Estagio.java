package br.ufrj.backendsiga.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "estagio")
public class Estagio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String cargo;

    @Column(length = 500)
    private String descricao;

    @Column(name = "quantidade_vagas", nullable = false)
    private Integer quantidadeVagas;

    @Column(name = "carga_horaria_semanal")
    private Integer cargaHorariaSemanal;

    private Double remuneracao;

    @Column(length = 20)
    private String modalidade;

    @Column(nullable = false, length = 50)
    private String empresa;

    @OneToMany(mappedBy = "estagio")
    private List<InscricaoEstagio> inscricoesEstagio;

}

