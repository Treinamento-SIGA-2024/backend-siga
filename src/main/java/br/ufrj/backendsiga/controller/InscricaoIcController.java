package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.dto.GetICDTO;
import br.ufrj.backendsiga.model.entity.InscricaoIC;
import br.ufrj.backendsiga.repository.UsuarioRepository;
import br.ufrj.backendsiga.service.InscricaoICService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin // novo na branch
@RestController
@RequestMapping("/inscricoes")
public class InscricaoIniciacaoCientificaController {
    private final InscricaoIcService inscricaoICService;

    public InscricaoIniciacaoCientificaController(InscricaoIcService inscricaoICService, UsuarioRepository usuarioRepository) {
        this.inscricaoICService = inscricaoICService;
    }

    @PostMapping("/IC/{ic_id}/aluno/{aluno_id}")
    public void createInscricaoIC(@PathVariable Integer ic_id,
                                         @PathVariable Integer aluno_id) {
                                            
        inscricaoICService.criarInscricaoIC(ic_id, aluno_id);
    }

    @GetMapping("/IC/aluno/{aluno_id}")
    public List<GetICDTO> verInscricoesIC(@PathVariable Integer aluno_id){
        return inscricaoICService.verInscricoesIC(aluno_id);
    }
}
