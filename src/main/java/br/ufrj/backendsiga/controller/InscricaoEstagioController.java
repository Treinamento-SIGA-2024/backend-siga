package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.entity.model.InscricaoEstagio;
import br.ufrj.backendsiga.service.InscricaoEstagioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoEstagioController {
    private final InscricaoEstagioService inscricaoEstagioService;

    public InscricaoEstagioController(InscricaoEstagioService inscricaoEstagioService) {
        this.inscricaoEstagioService = inscricaoEstagioService;
    }

    @GetMapping(path = "/estagio")
    public List<InscricaoEstagio> listPendentes() {
        return inscricaoEstagioService.listPendentes();
    }
}
