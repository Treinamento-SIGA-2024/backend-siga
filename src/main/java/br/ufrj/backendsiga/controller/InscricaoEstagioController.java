package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.dto.FormularioEstagioBodyDTO;
import br.ufrj.backendsiga.model.entity.InscricaoEstagio;
import br.ufrj.backendsiga.service.InscricaoEstagioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
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

    @GetMapping(path = "/estagio/{id}")
    public InscricaoEstagio findById(@PathVariable Integer id) {
        return inscricaoEstagioService.findById(id);
    }
    @PutMapping(path = "/estagio/aprovar/{id}")
    public InscricaoEstagio approvePedido(@PathVariable Integer id) {
        return inscricaoEstagioService.approvePedido(id);
    }
    @PutMapping(path = "/estagio/rejeitar/{id}")
    public InscricaoEstagio rejectPedido(@PathVariable Integer id) {
        return inscricaoEstagioService.rejectPedido(id);
    }

    @PostMapping(path = "/aluno/formulario")
    public void postFomularioAluno(@RequestBody FormularioEstagioBodyDTO body){
        inscricaoEstagioService.gerarPedido(body.getMatricula(), body.getEstagioId());

    }
}
