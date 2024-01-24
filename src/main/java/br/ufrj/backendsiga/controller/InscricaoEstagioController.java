package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.dto.InscricaoEstagioPendentesDTO;
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
    public List<InscricaoEstagioPendentesDTO> listPendentes() {
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
}
