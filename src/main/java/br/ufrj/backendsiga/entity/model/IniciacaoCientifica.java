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
    private int id;
    private String name;
    private String remuneracao;
    private int cargaHorariaSemanal;
    private String situacaoCriacao;
    private int coordenadorId;

}