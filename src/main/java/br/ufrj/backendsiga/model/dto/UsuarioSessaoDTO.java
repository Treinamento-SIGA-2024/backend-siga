package br.ufrj.backendsiga.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class UsuarioSessaoDTO {
    private Integer id;
    private String matricula;
    private String email;
    private String nome;
    private List<CargoDTO> cargos;
}
