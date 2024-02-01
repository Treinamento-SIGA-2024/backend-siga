package br.ufrj.backendsiga.model.dto;

import java.util.Set;

import lombok.Data;

@Data
public class IniciacaoCientificaProfessorAtivaDTO {
  private Integer id;
  private String nome;
  private String descricao;
  private Set<InscricaoICDTO> inscricoes;
  private Set<UsuarioDTO> professores;
}
