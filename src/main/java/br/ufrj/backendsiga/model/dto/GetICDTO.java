package br.ufrj.backendsiga.model.dto;

import lombok.Data;

@Data
public class GetICDTO {
    private Integer id;
    private SituacaoInscricaoDTO situacaoInscricao;
    private IniciacaoCientificaDTO iniciacaoCientifica;
}
