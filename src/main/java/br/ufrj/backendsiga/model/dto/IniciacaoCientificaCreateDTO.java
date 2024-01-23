package br.ufrj.backendsiga.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class IniciacaoCientificaCreateDTO {
    private String nome;
    private String descricao;
    private Double remuneracao;
    private Integer cargaHorariaSemanal;
    private Set<TopicoDTO> topicos;
    private Set<UsuarioDTO> professores;
}
