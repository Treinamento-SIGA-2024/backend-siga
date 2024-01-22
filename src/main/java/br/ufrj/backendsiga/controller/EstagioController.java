package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.entity.Estagio;
import br.ufrj.backendsiga.service.EstagioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@CrossOrigin
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
