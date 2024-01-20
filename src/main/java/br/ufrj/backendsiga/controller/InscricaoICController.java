package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.entity.InscricaoIC;
import br.ufrj.backendsiga.repository.UsuarioRepository;
import br.ufrj.backendsiga.service.InscricaoICService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/inscricoes")
public class InscricaoICController {
    private final InscricaoICService inscricaoICService;

    public InscricaoICController(InscricaoICService inscricaoICService, UsuarioRepository usuarioRepository) {
        this.inscricaoICService = inscricaoICService;
    }


    @PostMapping("/IC/{ic_id}/aluno/{aluno_id}/professor/{professor_id}/situacao/{codigo}")
    public InscricaoIC createInscricaoIC(@PathVariable Integer ic_id,
                                         @PathVariable Integer aluno_id,
                                         @PathVariable Integer professor_id,
                                         @PathVariable String codigo) {

         InscricaoIC inscricao = inscricaoICService.criarInscricaoIC(ic_id, aluno_id, professor_id, codigo);
        return inscricao;
    }


    @GetMapping("/IC/aluno/{aluno_id}")
    public List<InscricaoIC> verInscricoesIC(@PathVariable Integer aluno_id){
        List<InscricaoIC> inscricoes = inscricaoICService.verInscricoesIC(aluno_id);
        return inscricoes;
    }
}
