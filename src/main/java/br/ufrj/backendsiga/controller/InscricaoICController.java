package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.entity.InscricaoIC;
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
    @GetMapping("/ic/{matricula}/{icId}")
    public List<InscricaoIC> listaICsPendentes(@PathVariable String matricula, @PathVariable Integer icId ){
        return inscricaoICService.findInscricoesICProfessor(matricula, icId);
    }

    @PutMapping("/ic/{matricula}/{inscricaoId}")
    public InscricaoIC alterarSituacaoInscricaoAluno(@PathVariable Integer inscricaoId, @PathVariable String matricula){
        return inscricaoICService.alterarInscricaoAluno(inscricaoId, matricula);
    }
    
}
