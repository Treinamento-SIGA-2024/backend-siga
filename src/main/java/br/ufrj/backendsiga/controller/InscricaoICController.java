package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.dto.AlterarSituacaoAlunoIcBodyDTO;
import br.ufrj.backendsiga.model.dto.GetICDTO;
import br.ufrj.backendsiga.model.dto.InscricaoICPendentesDTO;
import br.ufrj.backendsiga.model.dto.VisualizarICsProfessorDTO;
import br.ufrj.backendsiga.model.entity.Cargo;
import br.ufrj.backendsiga.model.entity.IniciacaoCientifica;
import br.ufrj.backendsiga.model.entity.InscricaoIC;
import br.ufrj.backendsiga.model.entity.Usuario;
import br.ufrj.backendsiga.model.mapping.IniciacaoCientificaMapper;
import br.ufrj.backendsiga.service.InscricaoICService;

import br.ufrj.backendsiga.service.SessaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/inscricoes")
public class InscricaoICController {
    private final InscricaoICService inscricaoICService;
    private final SessaoService sessaoService;

    @PostMapping("/IC/{ic_id}/aluno/{aluno_id}")
    public void createInscricaoIC(@PathVariable Integer ic_id,
                                         @PathVariable Integer aluno_id) {

        inscricaoICService.criarInscricaoIC(ic_id, aluno_id);
    }

    @GetMapping("/IC/aluno/{aluno_id}")
    public List<GetICDTO> verInscricoesIC(@PathVariable Integer aluno_id){
        return inscricaoICService.verInscricoesIC(aluno_id);
    }

    //Vamos pegar as solicitações de ICs desse path
    // Queremos uma lista de solicitações de alunos com 3 campos:
    // 1) Titulo da IC (nome na tabela iniciacao cientifica) // Pegamos isso na IC
    // 2) Nome e matricula do aluno // Pegamos no usuario aluno
    //Como determinamos se o usuario é aluno?
    //Resposta: Pelo cargo =>
    //Temos que pegar o cargo do usuario
    //Como determinamos a situação do pedido aluno da ic?
    //Pegamos da tablea situação inscricao o campo

    @GetMapping("/ic/{matricula}/{icId}")
    public List<InscricaoICPendentesDTO> listaICsPendentes(@PathVariable String matricula, @PathVariable Integer icId ){
        return inscricaoICService.findInscricoesICProfessor(matricula, icId);
    }
    @GetMapping("/ic")
    public List<VisualizarICsProfessorDTO> listaICsProfessor(@RequestHeader(HttpHeaders.AUTHORIZATION) String sessaoId){
        Usuario prof = sessaoService.validateAndAssertCargoByNome(sessaoId, Cargo.PROFESSOR);

        List<IniciacaoCientifica> iniciacaoCientificasProfessor = inscricaoICService.findAllInscricoesICProfessor(prof.getMatricula());
        return iniciacaoCientificasProfessor.stream()
                .map(IniciacaoCientificaMapper
                        .INSTANCE::toVisualizarICsProfessorDTO).toList();
    }
    //  CASO NÃO FOSSE RETORNADO UMA LISTA
    //  IniciacaoCientificaMapper.INSTANCE.toVisualizarICsProfessorDTO(iniciacaoCientificasProfessor)
    @PutMapping("/ic/{inscricaoId}")
    public void alterarSituacaoInscricaoAluno(@PathVariable Integer inscricaoId, @RequestBody AlterarSituacaoAlunoIcBodyDTO bodyAlterar){
        inscricaoICService.alterarInscricaoAluno(inscricaoId, bodyAlterar);
    }

    @PutMapping("/ic/cancelar/{inscricaoId}")
    public String cancelarInscricao(@PathVariable Integer inscricaoId){
        return inscricaoICService.cancelarInscricaoIC(inscricaoId);
    }


    @PutMapping("/ic/expulsar/id/{inscricaoId}")
    public void excluirAlunoIc(@RequestHeader(HttpHeaders.AUTHORIZATION) String sessaoId,
                                      @PathVariable Integer inscricaoId){

        Usuario prof = sessaoService.validateAndAssertCargoByNome(sessaoId, Cargo.PROFESSOR);
        inscricaoICService.excluirAluno(inscricaoId, prof.getMatricula());
    }


}
