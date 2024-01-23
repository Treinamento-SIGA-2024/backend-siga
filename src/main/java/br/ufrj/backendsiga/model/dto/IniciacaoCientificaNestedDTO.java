package br.ufrj.backendsiga.model.dto;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class IniciacaoCientificaNestedDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private Double remuneracao;
    private Integer cargaHorariaSemanal;
    private Set<InscricaoICDTO> inscricoes;
    private Set<TopicoDTO> topicos;
    private Set<UsuarioDTO> professores;
    private SituacaoCriacaoICDTO situacaoCriacao;
    private UsuarioDTO coordenadorAvaliador;
}
