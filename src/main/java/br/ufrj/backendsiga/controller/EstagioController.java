package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.entity.Estagio;
import br.ufrj.backendsiga.service.EstagioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estagio")
public class EstagioController {
    private final EstagioService estagioService;

    public EstagioController(EstagioService estagioService) {
        this.estagioService = estagioService;
    }
    @GetMapping()
    public List<Estagio> listAll(){
        return estagioService.listAll();
    }

    @GetMapping("/id/{id}")
    public Estagio getEstagio (@PathVariable Integer id) {
        return estagioService.getEstagioById(id);
    }
}
