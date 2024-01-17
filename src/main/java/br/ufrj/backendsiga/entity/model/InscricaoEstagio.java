package br.ufrj.backendsiga.entity.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "Inscricao_Estagio")
public class InscricaoEstagio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private int Situacao_Inscricao_Id;
    private int Coordenador_Id;
    private int Estagio_Id;
    private int Aluno_Id;
}
