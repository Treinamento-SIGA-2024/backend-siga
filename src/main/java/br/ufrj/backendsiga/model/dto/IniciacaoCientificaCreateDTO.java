package br.ufrj.backendsiga.model.dto;

import java.util.List;

public class IniciacaoCientificaCreateDTO {
    private String nome;
    private String descricao;
    private Double remuneracao;
    private Integer cargaHorariaSemanal;
    private List<TopicoDTO> topicos;
}
