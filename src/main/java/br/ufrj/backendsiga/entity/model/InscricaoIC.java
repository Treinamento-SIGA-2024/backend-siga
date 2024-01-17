package br.ufrj.backendsiga.entity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class InscricaoIC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String situacao;
    private int alunoId;
    private int icId;
    private int professorId;
}
