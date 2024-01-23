package br.ufrj.backendsiga.model.DTO;

import lombok.Data;

@Data
public class EstagioCreateDTO {
    private String cargo;
    private String empresa;
    private Double remuneracao;
    private Integer cargaHorariaSemanal;
    private Integer quantidadeVagas;
    private String modalidade;
    private String descricao;
}
