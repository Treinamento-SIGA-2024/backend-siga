package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.entity.InscricaoIC;
import br.ufrj.backendsiga.repository.IniciacaoCientificaRepository;
import br.ufrj.backendsiga.repository.UsuarioRepository;
import br.ufrj.backendsiga.service.InscricaoICService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/inscricoes")
public class InscricaoICController {
    private final InscricaoICService inscricaoICService;
    public InscricaoICController(InscricaoICService inscricaoICService, UsuarioRepository usuarioRepository, IniciacaoCientificaRepository iniciacaoCientificaRepository, UsuarioRepository usuarioRepository1) {
        this.inscricaoICService = inscricaoICService;
    }


    @PostMapping("/IC/{ic_id}/aluno/{aluno_id}/professor/{professor_id}")
    public InscricaoIC createInscricaoIC(@PathVariable Integer ic_id,
                                         @PathVariable Integer aluno_id,
                                         @PathVariable Integer professor_id) {

         InscricaoIC inscricao = inscricaoICService.criarInscricaoIC(ic_id, aluno_id, professor_id);
        return inscricao;
    }
}
