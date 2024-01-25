package br.ufrj.backendsiga.service;
import br.ufrj.backendsiga.model.entity.*;
import br.ufrj.backendsiga.repository.IniciacaoCientificaRepository;
import br.ufrj.backendsiga.repository.SituacaoCriacaoICRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IniciacaoCientificaService {
    private final IniciacaoCientificaRepository iniciacaoCientificaRepository;
    private final UsuarioService usuarioService;
    private final CargoService cargoService;
    private final SituacaoCriacaoICService situacaoCriacaoService;
    private final TopicoService topicoService;

    public IniciacaoCientifica getIniciacaoCientificaById(Integer icId) {
        Optional<IniciacaoCientifica> optIc = iniciacaoCientificaRepository.findById(icId);

        if (optIc.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Iniciação científica inexistente."
            );
        }

        return optIc.get();
    }

    public List<IniciacaoCientifica> findAllBySituacaoCriacaoPendente(){
        SituacaoCriacaoIC situacaoPendente = situacaoCriacaoService.getSituacaoCriacaoICByCodigo(SituacaoCriacaoIC.PENDENTE);
        return iniciacaoCientificaRepository.findAllBySituacaoCriacao(situacaoPendente);
    }

    public List<IniciacaoCientifica> findAllBySituacaoCriacaoAceita(){
        SituacaoCriacaoIC situacaoPendente = situacaoCriacaoService.getSituacaoCriacaoICByCodigo(SituacaoCriacaoIC.ACEITA);
        return iniciacaoCientificaRepository.findAllBySituacaoCriacao(situacaoPendente);
    }

    @Transactional
    public List<IniciacaoCientifica> listIniciacaoCientificaByProfessorMatricula(String matricula) {
        Usuario professor = usuarioService.getUsuarioByMatriculaAndAssertCargoByNome(matricula, Cargo.PROFESSOR);

        return iniciacaoCientificaRepository.findAllByProfessoresIsContaining(professor);
    }

    @Transactional
    public IniciacaoCientifica createIniciacaoCientificaAndAddProfessorByMatricula(IniciacaoCientifica iniciacaoCientifica, String matriculaProfessorCriador) {
        Usuario professorCriador = usuarioService.getUsuarioByMatriculaAndAssertCargoByNome(matriculaProfessorCriador, Cargo.PROFESSOR);

        if (iniciacaoCientifica.getNome() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A iniciação científica precisa conter um nome."
            );
        }
        if (iniciacaoCientifica.getRemuneracao() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A iniciação científica precisa conter a remuneração (utilize 0 para não-remunerada)"
            );
        }

        //Por ser uma relação ManyToMany, precisamos de cautela, para evitar adulteração pela parte do front:
        //1. Pega os professores do RequestBody e os procura por id;
        //2. Coloca os dados atualizados na entidade
        //3. Atualiza a relação de cada professor para com essa ic
        List<Usuario> professores = usuarioService.getListUsuarioByIdAndAssertCargoByNome(
                iniciacaoCientifica.getProfessores().stream().map(Usuario::getId).toList(),
                Cargo.PROFESSOR
        );
        iniciacaoCientifica.setProfessores(new ArrayList<Usuario>(professores));
        professores.forEach(professor -> professor.getIniciacoesCientificas().add(iniciacaoCientifica));

        //Mesma lógica aqui.
        List<Topico> topicos = topicoService.getListTopicoById(
                iniciacaoCientifica.getTopicos().stream().map(Topico::getId).toList()
        );
        iniciacaoCientifica.setTopicos(topicos);
        topicos.forEach(topico -> topico.getIniciacoesCientificas().add(iniciacaoCientifica));

        iniciacaoCientifica.getProfessores().add(professorCriador);

        SituacaoCriacaoIC situacaoPendente = situacaoCriacaoService.getSituacaoCriacaoICByCodigo(SituacaoCriacaoIC.PENDENTE);
        iniciacaoCientifica.setSituacaoCriacao(situacaoPendente);

        if (iniciacaoCientifica.getTopicos().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A iniciação científica precisa conter pelo menos um tópico"
            );
        }

        return iniciacaoCientificaRepository.saveAndFlush(iniciacaoCientifica);
    }

    public IniciacaoCientifica approvePedido(Integer icId, String matriculaCoordenador) {
        IniciacaoCientifica ic = getIniciacaoCientificaById(icId);
        Usuario coordenador = usuarioService.getUsuarioByMatriculaAndAssertCargoByNome(matriculaCoordenador, Cargo.COORDENADOR);

        if(ic.getSituacaoCriacao().getCodigo().equals("001")) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Inscrição de estágio já aprovada"
            );
        } else if(ic.getSituacaoCriacao().getCodigo().equals("002")) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Inscrição de estágio já rejeitada"
            );
        }

        SituacaoCriacaoIC situacaoAprovado = situacaoCriacaoService.getSituacaoCriacaoICByCodigo(SituacaoCriacaoIC.ACEITA);
        ic.setSituacaoCriacao(situacaoAprovado);
        ic.setCoordenadorAvaliador(coordenador);

        return iniciacaoCientificaRepository.save(ic);
    }
    
    public IniciacaoCientifica rejectPedido(Integer icId, String matriculaCoordenador) {
        IniciacaoCientifica ic = getIniciacaoCientificaById(icId);
        Usuario coordenador = usuarioService.getUsuarioByMatriculaAndAssertCargoByNome(matriculaCoordenador, Cargo.COORDENADOR);

        if(ic.getSituacaoCriacao().getCodigo().equals("001")) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Inscrição de estágio já aprovada"
            );
        } else if(ic.getSituacaoCriacao().getCodigo().equals("002")) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Inscrição de estágio já rejeitada"
            );
        }

        SituacaoCriacaoIC situacaoRecusada = situacaoCriacaoService.getSituacaoCriacaoICByCodigo(SituacaoCriacaoIC.RECUSADA);
        ic.setSituacaoCriacao(situacaoRecusada);
        ic.setCoordenadorAvaliador(coordenador);

        return iniciacaoCientificaRepository.save(ic);
    }
}
