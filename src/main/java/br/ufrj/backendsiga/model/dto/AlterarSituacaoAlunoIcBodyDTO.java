package br.ufrj.backendsiga.model.dto;

import lombok.Data;

@Data
public class AlterarSituacaoAlunoIcBodyDTO {
    private Integer icId;
    private String matricula;
    private String codigo;
}
