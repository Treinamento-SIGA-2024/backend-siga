package br.ufrj.backendsiga.service;
import br.ufrj.backendsiga.model.dto.IniciacaoCientificaDTO;
import br.ufrj.backendsiga.model.dto.IniciacaoCientificaNestedDTO;
import br.ufrj.backendsiga.model.entity.*;
import br.ufrj.backendsiga.model.mapping.IniciacaoCientificaMapper;
import br.ufrj.backendsiga.model.mapping.InscricaoICMapper;
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
    private final InscricaoICService inscricaoICService;
    private final SituacaoInscricaoService situacaoInscricaoService;


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
    public IniciacaoCientifica createIniciacaoCientificaAndAddProfessorCriador(IniciacaoCientifica iniciacaoCientifica, Usuario professorCriador) {
        if (iniciacaoCientifica.getNome() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A iniciação científica precisa conter um nome."
            );
        }
        if (iniciacaoCientificaRepository.existsByNome(iniciacaoCientifica.getNome())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Já existe uma iniciação científica com este nome."
            );
        }
        //Por ser uma relação ManyToMany, precisamos de cautela, para evitar adulteração pela parte do front:
        //1. Pega os professores do RequestBody e os procura por id;
        //2. Coloca os dados atualizados na entidade
        //3. Atualiza a relação de cada professor para com essa ic
        if (!iniciacaoCientifica.getProfessores().isEmpty()) {
            List<Usuario> professores = usuarioService.getListUsuarioByIdAndAssertCargoByNome(
                    iniciacaoCientifica.getProfessores().stream().map(Usuario::getId).toList(),
                    Cargo.PROFESSOR
            );
            iniciacaoCientifica.setProfessores(professores);
            professores.forEach(professor -> professor.getIniciacoesCientificas().add(iniciacaoCientifica));
        }

        //Mesma lógica aqui.
        List<Topico> topicos = topicoService.getListTopicoById(
                iniciacaoCientifica.getTopicos().stream().map(Topico::getId).toList()
        );
        iniciacaoCientifica.setTopicos(topicos);
        topicos.forEach(topico -> topico.getIniciacoesCientificas().add(iniciacaoCientifica));

        if (!iniciacaoCientifica.getProfessores().contains(professorCriador)) {
            iniciacaoCientifica.getProfessores().add(professorCriador);
        }

        if (iniciacaoCientifica.getTopicos().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A iniciação científica precisa conter pelo menos um tópico"
            );
        }

        SituacaoCriacaoIC situacaoPendente = situacaoCriacaoService.getSituacaoCriacaoICByCodigo(SituacaoCriacaoIC.PENDENTE);
        iniciacaoCientifica.setSituacaoCriacao(situacaoPendente);

//        if (iniciacaoCientifica.getRemuneracao() != null && iniciacaoCientifica.getRemuneracao() == 0) {
//            iniciacaoCientifica.setRemuneracao();
//        }

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

    public IniciacaoCientifica getIniciacaoCientificaAtivosById(Integer id) {
        IniciacaoCientifica ic = getIniciacaoCientificaById(id);
        List<InscricaoIC> ativas = inscricaoICService.getInscricoesICAtivas(id);
        ic.setInscricoes(ativas);

        return ic;
    }

    public List<IniciacaoCientifica> findAllIniciacaoCientificaAceitasByProfessor(String matricula){
        Usuario professor = usuarioService.getUsuarioByMatriculaAndAssertCargoByNome(matricula, Cargo.PROFESSOR);
        
        SituacaoCriacaoIC situacaoAceita = situacaoCriacaoService.getSituacaoCriacaoICByCodigo(SituacaoCriacaoIC.ACEITA);

        List<IniciacaoCientifica> icsAtivasDoProfessor = iniciacaoCientificaRepository.findAllByProfessoresAndSituacaoCriacao(professor, situacaoAceita);

        icsAtivasDoProfessor.stream().forEach(ic->{
            List<InscricaoIC> inscricoesAtivas = new ArrayList<InscricaoIC>();
            ic.getInscricoes().stream().forEach(ins->{
                if(ins.getSituacaoInscricao().getCodigo().equals(SituacaoInscricao.ATIVO)){
                    inscricoesAtivas.add(ins);
                }
            });
            ic.setInscricoes(inscricoesAtivas);
        });
        
        return icsAtivasDoProfessor;
    }

    public IniciacaoCientifica addProfessorToIc (Integer icId, Usuario professor) {
        IniciacaoCientifica ic = getIniciacaoCientificaById(icId);
        List<Usuario> professoresIc = ic.getProfessores();
        if (professoresIc.contains(professor)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Professor já está vinculado a essa iniciação científica!");
        }
        professoresIc.add(professor);
        ic.setProfessores(professoresIc);

        return iniciacaoCientificaRepository.save(ic);
    }
}
