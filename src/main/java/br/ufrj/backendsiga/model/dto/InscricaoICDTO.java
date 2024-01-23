package br.ufrj.backendsiga.model.dto;

import lombok.Data;

@Data
public class InscricaoICDTO {
    private Integer id;
    private UsuarioDTO aluno;
    private UsuarioDTO professorAvaliador;
    private SituacaoInscricaoDTO situacaoInscricao;
}
