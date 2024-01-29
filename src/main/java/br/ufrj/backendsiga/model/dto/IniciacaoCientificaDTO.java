package br.ufrj.backendsiga.model.dto;

import java.util.Set;

import lombok.Data;

@Data
public class IniciacaoCientificaDTO {
    private Integer id;
    private String nome;
    private Set<UsuarioDTO> professores;
}
