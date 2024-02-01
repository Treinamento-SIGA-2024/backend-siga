package br.ufrj.backendsiga.model.dto;

import lombok.Data;

@Data
public class UsuarioLoginDTO {
    //Matrícula ou Email
    private String identificador;
    private String senha;
}
