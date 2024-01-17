package br.ufrj.backendsiga.entity.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Estagio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String Cargo;
    private String Descricao;
    private int Quantidade_Vagas;
    private int Carga_Horaria_Semanal;
    private Double Remuneracao;
    private String Modalidade;
    private String Empresa;
}

