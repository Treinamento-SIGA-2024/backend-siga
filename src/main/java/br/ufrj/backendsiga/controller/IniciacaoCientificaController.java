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
    public IniciacaoCientifica getIniciacaoCientificaById(@PathVariable String icId) {
        return iniciacaoCientificaService.getIniciacaoCientificaById(Integer.parseInt(icId));
    }

    @GetMapping("/{icId}/ativos")
    public IniciacaoCientificaNestedDTO getIniciacaoCientificaAtivosById(@PathVariable String icId) {
        IniciacaoCientifica icInscricoesAtivas = iniciacaoCientificaService.getIniciacaoCientificaAtivosById(Integer.parseInt(icId));
        return IniciacaoCientificaMapper.INSTANCE.toNestedDTO(icInscricoesAtivas);
    }

    @GetMapping("/ativas/professor/{matricula}")
    public List<IniciacaoCientificaProfessorAtivaDTO> getIniciacaoCientificaAtivasByProfessor(@PathVariable String matricula){
        List<IniciacaoCientifica> icsAtivas = iniciacaoCientificaService.findAllIniciacaoCientificaAceitasByProfessor(matricula);
        return icsAtivas.stream().map(ic->IniciacaoCientificaMapper.INSTANCE.toAtivaDTO(ic)).toList();
    }

    @GetMapping("/pendentes")
    public List<IniciacaoCientifica> findAllIniciacaoCientificaPendente() {
        return iniciacaoCientificaService.findAllBySituacaoCriacaoPendente();
    }

    @GetMapping("/aceitas")
    public List<IniciacaoCientifica> findAllIniciacaoCientificaAceita() {
        return iniciacaoCientificaService.findAllBySituacaoCriacaoAceita();
    }

    //Precisará ser mudado no front-end, antigamente era um post com query parameter da matrícula do professor criador.
    //Agora recebe-se um Authorization Header com o ID da sessão do professor criador.
    @PostMapping()
    public IniciacaoCientificaNestedDTO createIniciacaoCientifica(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String sessaoId,
            @RequestBody IniciacaoCientificaCreateDTO iniciacaoCientificaCreateDTO
    ) {
        Usuario professorCriador = sessaoService.validateAndAssertCargoByNome(sessaoId, Cargo.PROFESSOR);

        return iniciacaoCientificaMapper.toNestedDTO(
                iniciacaoCientificaService.createIniciacaoCientificaAndAddProfessorCriador(
                    iniciacaoCientificaMapper.toEntity(iniciacaoCientificaCreateDTO),
                    professorCriador
            )
        );
    }

    @PutMapping("/{matriculaCoordenador}/aprovar/{icId}")
    public IniciacaoCientifica approvePedido(@PathVariable Integer icId, @PathVariable String matriculaCoordenador) {
        return iniciacaoCientificaService.approvePedido(icId, matriculaCoordenador);
    }
    @PutMapping("/{matriculaCoordenador}/rejeitar/{icId}")
    public IniciacaoCientifica rejectPedido(@PathVariable Integer icId, @PathVariable String matriculaCoordenador) {
        return iniciacaoCientificaService.rejectPedido(icId, matriculaCoordenador);
    }

    @PutMapping("/{icId}/vincular")
    public IniciacaoCientificaNestedDTO vinculateProfessorIC(@PathVariable Integer icId, @RequestBody UsuarioDTO professor) {
        Cargo cargo = cargoService.getCargoByNome(Cargo.PROFESSOR);
        Usuario user = usuarioService.getUsuarioByMatriculaAndAssertCargo(professor.getMatricula(), cargo);
        IniciacaoCientifica ic = iniciacaoCientificaService.addProfessorToIc(icId, user);
        return IniciacaoCientificaMapper.INSTANCE.toNestedDTO(ic);
    }
}
