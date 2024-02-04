package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.dto.InscricaoEstagioPendentesDTO;
import br.ufrj.backendsiga.model.dto.FormularioEstagioBodyDTO;
import br.ufrj.backendsiga.model.dto.getEstagioDTO;
import br.ufrj.backendsiga.model.entity.InscricaoEstagio;
import br.ufrj.backendsiga.model.mapping.InscricaoEstagioMapper;
import br.ufrj.backendsiga.service.InscricaoEstagioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/inscricoes")
public class InscricaoEstagioController {
    private final InscricaoEstagioService inscricaoEstagioService;

    public InscricaoEstagioController(InscricaoEstagioService inscricaoEstagioService) {
        this.inscricaoEstagioService = inscricaoEstagioService;
    }

    @GetMapping(path = "/estagio")
    public List<InscricaoEstagioPendentesDTO> listPendentes() {
        return inscricaoEstagioService.listPendentes();
    }
    @GetMapping("/estagio/aluno/{aluno_id}")
    public List<getEstagioDTO> listAllByAluno(@PathVariable Integer aluno_id) {
        List<InscricaoEstagio> listaPedidos = inscricaoEstagioService.findEstagioByAluno(aluno_id);
        return listaPedidos.stream().map(InscricaoEstagioMapper.INSTANCE::toEstagioDTO).toList();
    }
    @GetMapping(path = "/estagio/{id}")
    public InscricaoEstagioPendentesDTO findById(@PathVariable Integer id) {
        InscricaoEstagio inscricao = inscricaoEstagioService.findById(id);
        return InscricaoEstagioMapper.INSTANCE.toPendentesDTO(inscricao);
    }
    @PutMapping(path = "/estagio/aprovar/{id}")
    public InscricaoEstagio approvePedido(@PathVariable Integer id) {
        return inscricaoEstagioService.approvePedido(id);
    }
    @PutMapping(path = "/estagio/rejeitar/{id}")
    public InscricaoEstagio rejectPedido(@PathVariable Integer id) {
        return inscricaoEstagioService.rejectPedido(id);
    }

    @PostMapping(path = "/aluno/formulario")
    public void postFomularioAluno(@RequestBody FormularioEstagioBodyDTO body){
        inscricaoEstagioService.gerarPedido(body.getMatricula(), body.getEstagioId());
    }

    @PutMapping("/estagio/cancelar/{inscricaoId}")
    public String cancelarInscricaoEstagio(@PathVariable Integer inscricaoId) {

        return inscricaoEstagioService.cancelarInscricaoEstagio(inscricaoId);
    }
}
