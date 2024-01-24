package br.ufrj.backendsiga.model.dto;

import lombok.Data;

@Data
public class InscricaoICPendentesDTO {
    private Integer id;
    private UsuarioDTO aluno;
    private SituacaoInscricaoDTO situacaoInscricao;
}
