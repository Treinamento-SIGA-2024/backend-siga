package br.ufrj.backendsiga.entity.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "Situacao_Criacao_IC")
public class SituacaoCriacaoIC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String Codigo;
    private String Descricao;


}



