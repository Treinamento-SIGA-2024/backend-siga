package br.ufrj.backendsiga.model.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Integer id;
    private String matricula;
    private String email;
    private String nome;
}
