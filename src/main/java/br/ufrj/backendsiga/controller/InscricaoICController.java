package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.entity.DTO.InscricaoICDTO;
import br.ufrj.backendsiga.entity.model.IniciacaoCientifica;
import br.ufrj.backendsiga.entity.model.Usuario;
import br.ufrj.backendsiga.service.InscricaoICService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/teste")
public class InscricaoICController {

    private final InscricaoICService inscricaoICService;
    public InscricaoICController(InscricaoICService inscricaoICService) {
        this.inscricaoICService = inscricaoICService;
    }

    //Vamos pegar as solicitações de ICs desse path
    @GetMapping("/ic/{matricula}/{icId}")
    public List<InscricaoICDTO> listaICsPendentes(@PathVariable String matricula, @PathVariable Integer icId ){
        return inscricaoICService.findInscricoesICProfessor(matricula, icId);
    }
    
}
