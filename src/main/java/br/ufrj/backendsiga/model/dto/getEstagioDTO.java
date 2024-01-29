package br.ufrj.backendsiga.model.dto;

import lombok.Data;

@Data
public class getEstagioDTO {

    private Integer id;
    private EstagioCreateDTO estagio;
    private SituacaoInscricaoDTO situacaoInscricao;
}
