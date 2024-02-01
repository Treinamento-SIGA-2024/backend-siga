package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.entity.SituacaoInscricao;
import br.ufrj.backendsiga.repository.SituacaoInscricaoRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/situacao_inscricao")
public class SituacaoInscricaoController {
    private SituacaoInscricaoRepository situacaoInscricaoRepository;

    public SituacaoInscricaoController (SituacaoInscricaoRepository situacaoInscricaoRepository) {
        this.situacaoInscricaoRepository = situacaoInscricaoRepository;
    }
    @GetMapping()
    public List<SituacaoInscricao> getAllSituacaoInscricao () {
        return this.situacaoInscricaoRepository.findAll();
    }
}
