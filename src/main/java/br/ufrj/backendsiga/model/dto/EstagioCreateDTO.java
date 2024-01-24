package br.ufrj.backendsiga.model.dto;

import lombok.Data;

@Data
public class EstagioCreateDTO {
    private Integer id;
    private String cargo;
    private String empresa;
    private Double remuneracao;
    private Integer cargaHorariaSemanal;
    private Integer quantidadeVagas;
    private String modalidade;
    private String descricao;
}
