package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.dto.EstagioCreateDTO;
import br.ufrj.backendsiga.model.entity.Estagio;
import br.ufrj.backendsiga.service.EstagioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/estagio")
public class EstagioController {
    private final EstagioService estagioService;

    @GetMapping()
    public List<Estagio> listAll(){
        return estagioService.listAll();
    }

    @GetMapping("/id/{id}")
    public Estagio getEstagio (@PathVariable Integer id) {
        return estagioService.getEstagioById(id);
    }

    @PostMapping()
    public Estagio createEstagio(@RequestBody EstagioCreateDTO novoEstagio) {
        return estagioService.createEstagio(novoEstagio);
    }
}
