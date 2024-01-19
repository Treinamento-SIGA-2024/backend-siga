package br.ufrj.backendsiga.entity.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class InscricaoICDTO {

    private String nome;
    private String matricula;
    //private String nomeIc;
    private String descricao;

    public InscricaoICDTO(String nome, String matricula, String descricao) {
        this.nome = nome;
        this.matricula = matricula;
        //this.nomeIc = nomeIc;
        this.descricao = descricao;
    }

}