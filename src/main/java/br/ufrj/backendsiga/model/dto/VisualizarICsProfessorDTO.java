package br.ufrj.backendsiga.model.dto;
import java.util.Set;

import lombok.Data;
@Data
public class VisualizarICsProfessorDTO {
    private Integer id;
    private String nome;
    private Set<InscricaoICPendentesDTO> inscricoes;
}
