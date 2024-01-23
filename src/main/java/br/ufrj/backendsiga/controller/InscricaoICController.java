package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.dto.InscricaoICDTO;
import br.ufrj.backendsiga.model.dto.InscricaoICPendentesDTO;
import br.ufrj.backendsiga.model.entity.InscricaoIC;
import br.ufrj.backendsiga.model.mapping.InscricaoICMapper;
import br.ufrj.backendsiga.service.InscricaoICService;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/inscricao")
@CrossOrigin
public class InscricaoICController {

    private final InscricaoICService inscricaoICService;
    public InscricaoICController(InscricaoICService inscricaoICService) {
        this.inscricaoICService = inscricaoICService;
    }

    //Vamos pegar as solicitações de ICs desse path
    // Queremos uma lista de solicitações de alunos com 3 campos:
    // 1) Titulo da IC (nome na tabela iniciacao cientifica) // Pegamos isso na IC
    // 2) Nome e matricula do aluno // Pegamos no usuario aluno
    //Como determinamos se o usuario é aluno?
    //Resposta: Pelo cargo =>
    //Temos que pegar o cargo do usuario
    //Como determinamos a situação do pedido aluno da ic?
    //Pegamos da tablea situação inscricao o campo

    @GetMapping("/ic/{matricula}/{icId}")
    public List<InscricaoICPendentesDTO> listaICsPendentes(@PathVariable String matricula, @PathVariable Integer icId ){
        return inscricaoICService.findInscricoesICProfessor(matricula, icId);
    }

    @PutMapping("/ic/{matricula}/{inscricaoId}")
    public InscricaoIC alterarSituacaoInscricaoAluno(@PathVariable Integer inscricaoId, @PathVariable String matricula){
        return inscricaoICService.alterarInscricaoAluno(inscricaoId, matricula);
    }
    
}
