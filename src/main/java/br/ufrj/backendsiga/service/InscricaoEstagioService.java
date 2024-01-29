package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.dto.InscricaoEstagioPendentesDTO;
import br.ufrj.backendsiga.model.entity.*;
import br.ufrj.backendsiga.model.mapping.InscricaoEstagioMapper;
import br.ufrj.backendsiga.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class InscricaoEstagioService {
    private final InscricaoEstagioRepository inscricaoEstagioRepository;
    private final SituacaoInscricaoService situacaoInscricaoService;

    private final EstagioRepository estagioRepository;

    private final UsuarioRepository usuarioRepository;
    private final CargoRepository cargoRepository;

    public InscricaoEstagioService(InscricaoEstagioRepository inscricaoEstagioRepository,
                                   SituacaoInscricaoService situacaoInscricaoService,
                                   EstagioRepository estagioRepository,
                                   UsuarioRepository usuarioRepository,
                                   CargoRepository cargoRepository) {
        this.inscricaoEstagioRepository = inscricaoEstagioRepository;
        this.situacaoInscricaoService = situacaoInscricaoService;
        this.estagioRepository = estagioRepository;
        this.usuarioRepository = usuarioRepository;
        this.cargoRepository = cargoRepository;
    }

    public InscricaoEstagio findById(Integer id) {
        return inscricaoEstagioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Inscrição de estágio não encontrada"));
    }

    public List<InscricaoEstagioPendentesDTO> listPendentes() {
        SituacaoInscricao pendente = situacaoInscricaoService.findByCodigo("000");
        List<InscricaoEstagio> inscricoesPendentes = inscricaoEstagioRepository.findAllBySituacaoInscricao(pendente);
        return inscricoesPendentes.stream().map(InscricaoEstagioMapper.INSTANCE::toPendentesDTO).toList();
    }

    public InscricaoEstagio approvePedido(Integer id) {
        InscricaoEstagio inscricaoEstagio = findById(id);
        if(inscricaoEstagio.getSituacaoInscricao().getCodigo().equals("001")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Inscrição de estágio já aprovada");
        } else if(inscricaoEstagio.getSituacaoInscricao().getCodigo().equals("002")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Inscrição de estágio já rejeitada");
        }
        SituacaoInscricao aprovado = situacaoInscricaoService.findByCodigo("001");
        inscricaoEstagio.setSituacaoInscricao(aprovado);
        return inscricaoEstagioRepository.save(inscricaoEstagio);
    }
    public InscricaoEstagio rejectPedido(Integer id) {
        InscricaoEstagio inscricaoEstagio = findById(id);
        if(inscricaoEstagio.getSituacaoInscricao().getCodigo().equals("001")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Inscrição de estágio já aprovada");
        } else if(inscricaoEstagio.getSituacaoInscricao().getCodigo().equals("002")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Inscrição de estágio já rejeitada");
        }
        SituacaoInscricao rejeitado = situacaoInscricaoService.findByCodigo("002");
        inscricaoEstagio.setSituacaoInscricao(rejeitado);
        return inscricaoEstagioRepository.save(inscricaoEstagio);
    }

    public InscricaoEstagio gerarPedido(String matricula, Integer estagioID){
        Estagio estagio =  estagioRepository.findById(estagioID).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estágio não encontrado"));

        Usuario aluno = usuarioRepository.findByMatricula(matricula).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        Cargo cargoAluno = cargoRepository.findCargoByNome("Aluno").orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cargo aluno não encotrado"));

        if(!aluno.getCargos().contains(cargoAluno)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario não permitido");
        }
        SituacaoInscricao pendente = situacaoInscricaoService.findByCodigo("000");
        InscricaoEstagio novoPedidoDeEstagio = new InscricaoEstagio();
        novoPedidoDeEstagio.setEstagio(estagio);
        novoPedidoDeEstagio.setSituacaoInscricao(pendente);
        novoPedidoDeEstagio.setAluno(aluno);
        return inscricaoEstagioRepository.save(novoPedidoDeEstagio);
    }

    public List<InscricaoEstagio> findEstagioByAluno(Integer aluno_id) {
        final String CARGO_ALUNO = "Aluno";

        Cargo cargoAluno = cargoRepository.findCargoByNome(CARGO_ALUNO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cargo não encontrado."));

        Usuario aluno = usuarioRepository.findById(aluno_id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado."));

        if(!aluno.getCargos().contains(cargoAluno)) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não é um aluno.");
        }

        List<InscricaoEstagio> inscricoes = aluno.getInscricoesEstagio();

        if(inscricoes.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O aluno não possui estágio.");
        }

        return inscricoes;
    }
}
