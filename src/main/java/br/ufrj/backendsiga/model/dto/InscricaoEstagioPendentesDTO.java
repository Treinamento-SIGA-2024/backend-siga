package br.ufrj.backendsiga.model.dto;

import br.ufrj.backendsiga.model.entity.Estagio;
import br.ufrj.backendsiga.model.entity.SituacaoInscricao;
import lombok.Data;

@Data
public class InscricaoEstagioPendentesDTO {
    private Integer id;
    private EstagioCreateDTO estagio;
    private SituacaoInscricaoDTO situacaoInscricao;
    private UsuarioDTO aluno;
}
