package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.dto.*;
import br.ufrj.backendsiga.model.entity.Cargo;
import br.ufrj.backendsiga.model.entity.IniciacaoCientifica;
import br.ufrj.backendsiga.model.entity.InscricaoEstagio;
import br.ufrj.backendsiga.model.entity.Topico;
import br.ufrj.backendsiga.model.entity.Usuario;
import br.ufrj.backendsiga.model.mapping.IniciacaoCientificaMapper;
import br.ufrj.backendsiga.model.mapping.UsuarioMapper;
import br.ufrj.backendsiga.repository.IniciacaoCientificaRepository;
import br.ufrj.backendsiga.service.CargoService;
import br.ufrj.backendsiga.service.IniciacaoCientificaService;
import br.ufrj.backendsiga.service.SessaoService;
import br.ufrj.backendsiga.service.UsuarioService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/iniciacao_cientifica")
public class IniciacaoCientificaController {

    private final IniciacaoCientificaRepository iniciacaoCientificaRepository;
    private final IniciacaoCientificaService iniciacaoCientificaService;
    private final SessaoService sessaoService;
    private final CargoService cargoService;
    private final UsuarioService usuarioService;


    private final IniciacaoCientificaMapper iniciacaoCientificaMapper = IniciacaoCientificaMapper.INSTANCE;
    @GetMapping()
    public List<IniciacaoCientifica> findAllIniciacaoCientifica() {
        return iniciacaoCientificaRepository.findAll();
    }

    @GetMapping("/{icId}")
    public IniciacaoCientificaNestedDTO getIniciacaoCientificaById(@PathVariable String icId) {
        return IniciacaoCientificaMapper.INSTANCE.toNestedDTO(iniciacaoCientificaService.getIniciacaoCientificaById(Integer.parseInt(icId)));
    }

    @GetMapping("/{icId}/ativos")
    public IniciacaoCientificaNestedDTO getIniciacaoCientificaAtivosById(@PathVariable String icId) {
        IniciacaoCientifica icInscricoesAtivas = iniciacaoCientificaService.getIniciacaoCientificaAtivosById(Integer.parseInt(icId));
        return IniciacaoCientificaMapper.INSTANCE.toNestedDTO(icInscricoesAtivas);
    }

    @GetMapping("/ativas/professor")
    public List<IniciacaoCientificaProfessorAtivaDTO> getIniciacaoCientificaAtivasByProfessor(@RequestHeader(HttpHeaders.AUTHORIZATION) String sessaoId){
        Usuario prof = sessaoService.validateAndAssertCargoByNome(sessaoId, Cargo.PROFESSOR);
        List<IniciacaoCientifica> icsAtivas = iniciacaoCientificaService.findAllIniciacaoCientificaAceitasByProfessor(prof.getMatricula());
        return icsAtivas.stream().map(ic->IniciacaoCientificaMapper.INSTANCE.toAtivaDTO(ic)).toList();
    }

    @GetMapping("/pendentes")
    public List<IniciacaoCientificaNestedDTO> findAllIniciacaoCientificaPendente() {
        List<IniciacaoCientifica> lista = iniciacaoCientificaService.findAllBySituacaoCriacaoPendente();
        List<IniciacaoCientificaNestedDTO> retorno = lista.stream().
                map(IniciacaoCientificaMapper.INSTANCE::toNestedDTO).toList();

        return retorno;
    }

    @GetMapping("/aceitas")
    public List<IniciacaoCientificaNestedDTO> findAllIniciacaoCientificaAceitasAndAlunoNãoInscrito(
        @RequestHeader(HttpHeaders.AUTHORIZATION) String sessaoId
    ) {
        //Listar ics em que o aluno não esta inscrito ou com situação expulsa ou cancelado
        Usuario aluno = sessaoService.validateAndAssertCargoByNome(sessaoId, Cargo.ALUNO);
        List<IniciacaoCientifica> lista = iniciacaoCientificaService.findAllBySituacaoCriacaoAceitaAndAlunoNaoInscrito(aluno);
        List<IniciacaoCientificaNestedDTO> retorno = lista.stream().map((ic) ->
            IniciacaoCientificaMapper.INSTANCE.toNestedDTO(ic)
        ).toList();

        return retorno;
    }

    @GetMapping("/aluno")
    public List<IniciacaoCientificaNestedDTO> findAllIniciacaoCientificaAlunoInscrito(
        @RequestHeader(HttpHeaders.AUTHORIZATION) String sessaoId
    ){
        Usuario aluno = sessaoService.validateAndAssertCargoByNome(sessaoId, Cargo.ALUNO);
        List<IniciacaoCientifica> icsDoAluno = iniciacaoCientificaService.findAllIniciacoesCientificasAlunoInscrito(aluno);
        return icsDoAluno.stream().map(ic->iniciacaoCientificaMapper.toNestedDTO(ic)).toList();
    }

    //Precisará ser mudado no front-end, antigamente era um post com query parameter da matrícula do professor criador.
    //Agora recebe-se um Authorization Header com o ID da sessão do professor criador.estagio
    @PostMapping()
    public IniciacaoCientificaNestedDTO createIniciacaoCientifica(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String sessaoId,
            @RequestBody IniciacaoCientificaCreateDTO iniciacaoCientificaCreateDTO
    ) {
        System.out.println(sessaoId);
        Usuario professorCriador = sessaoService.validateAndAssertCargoByNome(sessaoId, Cargo.PROFESSOR);

        return iniciacaoCientificaMapper.toNestedDTO(
                iniciacaoCientificaService.createIniciacaoCientificaAndAddProfessorCriador(
                    iniciacaoCientificaMapper.toEntity(iniciacaoCientificaCreateDTO),
                    professorCriador
            )
        );
    }
    @PutMapping("/{matriculaCoordenador}/aprovar/{icId}")
    public void approvePedido(@PathVariable Integer icId, @PathVariable String matriculaCoordenador) {
        iniciacaoCientificaService.approvePedido(icId, matriculaCoordenador);
    }

    @PutMapping("/{matriculaCoordenador}/rejeitar/{icId}")
    public void rejectPedido(@PathVariable Integer icId, @PathVariable String matriculaCoordenador) {
         iniciacaoCientificaService.rejectPedido(icId, matriculaCoordenador);
    }

    @PutMapping("/{icId}/vincular")
    public IniciacaoCientificaNestedDTO vinculateProfessorIC(@PathVariable Integer icId, @RequestBody UsuarioDTO professor) {
        Cargo cargo = cargoService.getCargoByNome(Cargo.PROFESSOR);
        Usuario user = usuarioService.getUsuarioByMatriculaAndAssertCargo(professor.getMatricula(), cargo);
        IniciacaoCientifica ic = iniciacaoCientificaService.addProfessorToIc(icId, user);
        return IniciacaoCientificaMapper.INSTANCE.toNestedDTO(ic);
    }
}
