package br.ufrj.backendsiga.entity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class IniciacaoCientifica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String Nome;
    private Double Remuneracao;
    private int Carga_Horaria_Semanal;
    private int Situacao_Criacao_Id;
    private int Coordenador_Id;

}