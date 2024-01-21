package br.ufrj.backendsiga.model.dto;
import lombok.Data;

import java.util.List;

@Data
public class IniciacaoCientificaNestedDTO {
    private String id;
    private String nome;
    private String descricao;
    private Double remuneracao;
    private Integer cargaHorariaSemanal;
    private List<InscricaoICDTO> inscricoes;
    private List<TopicoDTO> topicos;
    private List<UsuarioDTO> professores;
    private SituacaoCriacaoICDTO situacaoCriacao;
    private UsuarioDTO coordenadorAvaliador;
}
