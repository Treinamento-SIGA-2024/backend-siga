package br.ufrj.backendsiga.entity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "Inscricao_IC")
public class InscricaoIC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private int Situacao_Inscricao_Id;
    private int Aluno_Id;
    private int IC_Id;
    private int Professor_Id;
}
