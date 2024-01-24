package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.entity.InscricaoIC;
import br.ufrj.backendsiga.repository.UsuarioRepository;
import br.ufrj.backendsiga.service.InscricaoIcService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin // novo na branch
@RestController
@RequestMapping("/inscricoes")
public class InscricaoIcController {
    private final InscricaoIcService inscricaoICService;

    public InscricaoIcController(InscricaoIcService inscricaoICService, UsuarioRepository usuarioRepository) {
        this.inscricaoICService = inscricaoICService;
    }


    @PostMapping("/IC/{ic_id}/aluno/{aluno_id}")
    public InscricaoIC createInscricaoIC(@PathVariable Integer ic_id,
                                         @PathVariable Integer aluno_id) {


        return inscricaoICService.criarInscricaoIC(ic_id, aluno_id);
    }


    @GetMapping("/IC/aluno/{aluno_id}")
    public List<InscricaoIC> verInscricoesIC(@PathVariable Integer aluno_id){
        return inscricaoICService.verInscricoesIC(aluno_id);
    }
}
