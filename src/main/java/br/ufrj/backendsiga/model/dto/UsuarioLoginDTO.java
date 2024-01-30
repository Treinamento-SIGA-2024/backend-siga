package br.ufrj.backendsiga.model.dto;

import lombok.Data;

@Data
public class UsuarioLoginDTO {
    private String identificador;
    private String senha;
}
