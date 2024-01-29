package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.dto.IniciacaoCientificaCreateDTO;
import br.ufrj.backendsiga.model.dto.IniciacaoCientificaNestedDTO;
import br.ufrj.backendsiga.model.dto.IniciacaoCientificaProfessorAtivaDTO;
import br.ufrj.backendsiga.model.dto.TopicoDTO;
import br.ufrj.backendsiga.model.entity.Cargo;
import br.ufrj.backendsiga.model.entity.IniciacaoCientifica;
import br.ufrj.backendsiga.model.entity.InscricaoEstagio;
import br.ufrj.backendsiga.model.entity.Topico;
import br.ufrj.backendsiga.model.entity.Usuario;
import br.ufrj.backendsiga.model.mapping.IniciacaoCientificaMapper;
import br.ufrj.backendsiga.repository.IniciacaoCientificaRepository;
import br.ufrj.backendsiga.service.IniciacaoCientificaService;
import br.ufrj.backendsiga.service.UsuarioService;
import lombok.RequiredArgsConstructor;

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
    private final UsuarioService usuarioService;

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

    @PostMapping("/{matriculaProfessorCriador}")
    public IniciacaoCientificaNestedDTO createIniciacaoCientifica(@PathVariable String matriculaProfessorCriador, @RequestBody IniciacaoCientificaCreateDTO iniciacaoCientificaCreateDTO) {
        IniciacaoCientifica iniciacaoCientifica = iniciacaoCientificaService.createIniciacaoCientificaAndAddProfessorByMatricula(
                IniciacaoCientificaMapper.INSTANCE.toEntity(iniciacaoCientificaCreateDTO),
                matriculaProfessorCriador
        );
        return IniciacaoCientificaMapper.INSTANCE.toNestedDTO(iniciacaoCientifica);
    }

    @PutMapping("/{matriculaCoordenador}/aprovar/{icId}")
    public IniciacaoCientifica approvePedido(@PathVariable Integer icId, @PathVariable String matriculaCoordenador) {
        return iniciacaoCientificaService.approvePedido(icId, matriculaCoordenador);
    }
    @PutMapping("/{matriculaCoordenador}/rejeitar/{icId}")
    public IniciacaoCientifica rejectPedido(@PathVariable Integer icId, @PathVariable String matriculaCoordenador) {
        return iniciacaoCientificaService.rejectPedido(icId, matriculaCoordenador);
    }
}
