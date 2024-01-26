package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.dto.*;
import br.ufrj.backendsiga.model.entity.Cargo;
import br.ufrj.backendsiga.model.entity.IniciacaoCientifica;
import br.ufrj.backendsiga.model.entity.InscricaoIC;
import br.ufrj.backendsiga.model.entity.SituacaoInscricao;
import br.ufrj.backendsiga.model.entity.Usuario;
import br.ufrj.backendsiga.model.mapping.InscricaoICMapper;
import br.ufrj.backendsiga.repository.IniciacaoCientificaRepository;
import br.ufrj.backendsiga.repository.InscricaoICRepository;
import br.ufrj.backendsiga.repository.InscricaoICRepository;
import br.ufrj.backendsiga.repository.SituacaoInscricaoRepository;
import br.ufrj.backendsiga.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InscricaoICService {
    private final UsuarioRepository usuarioRepository;
    private final IniciacaoCientificaRepository iniciacaoCientificaRepository;
    private final InscricaoICRepository inscricaoICRepository;
    private final SituacaoInscricaoRepository situacaoInscricaoRepository;

    public InscricaoICService(UsuarioRepository usuarioRepository,
            IniciacaoCientificaRepository iniciacaoCientificaRepository, 
            InscricaoICRepository inscricaoICRepository,
            SituacaoInscricaoRepository situacaoInscricaoRepository) {
        this.iniciacaoCientificaRepository = iniciacaoCientificaRepository;
        this.usuarioRepository = usuarioRepository;
        this.inscricaoICRepository = inscricaoICRepository;
        this.situacaoInscricaoRepository = situacaoInscricaoRepository;        
    }

    public List<InscricaoICPendentesDTO> findInscricoesICProfessor(String matricula, Integer icId){
        Usuario professor = usuarioRepository.findUsuarioByMatricula(matricula).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não existe no sistema."));
        //ToDo NOT FOUDN IC ID

        IniciacaoCientifica icProfessor = iniciacaoCientificaRepository.findById(icId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "IC não encontrado."));

        if(!icProfessor.getProfessores().contains(professor)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario não tem acesso a essa ic.");
        }


        SituacaoInscricao situacaoPendente = situacaoInscricaoRepository.findByCodigo("000").orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Situação de inscrição pendente não encontrada."));


        List<InscricaoIC> inscricoesPendentes = inscricaoICRepository.findAllByIniciacaoCientificaAndSituacaoInscricao(icProfessor, situacaoPendente);
        List<InscricaoICPendentesDTO> teste = inscricoesPendentes.stream().map(ic -> InscricaoICMapper.INSTANCE.toPendentesDTO(ic)).toList();

        return inscricoesPendentes.stream().map(ic -> InscricaoICMapper.INSTANCE.toPendentesDTO(ic)).toList();
    }

    public InscricaoIC alterarInscricaoAluno(Integer inscricaoId, AlterarSituacaoAlunoIcBodyDTO body){
        InscricaoIC inscricaoICAluno = inscricaoICRepository.findById(inscricaoId).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscrição da IC do aluno não encontrada."));
        Usuario professorAvaliador = usuarioRepository.findUsuarioByMatricula(body.getMatricula())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário professor não encontrado."));
        //ToDo verificar cargo do professor
        IniciacaoCientifica ic = iniciacaoCientificaRepository.findById(body.getIcId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ic não encontrada"));
        if(!ic.getProfessores().contains(professorAvaliador) || !ic.getInscricoes().contains(inscricaoICAluno)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario não permitido.");
        }
        SituacaoInscricao situacaoNova = situacaoInscricaoRepository.findByCodigo(body.getCodigo()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Situação não encontrada."));

        inscricaoICAluno.setSituacaoInscricao(situacaoNova);
        inscricaoICAluno.setProfessorAvaliador(professorAvaliador);
        return inscricaoICRepository.save(inscricaoICAluno);
    }

     public List<String> verificaCargoUsuario(Integer usuario_id) {
        Usuario usuario = usuarioRepository.findById(usuario_id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        List<Cargo> cargos = usuario.getCargos();

        return cargos.stream()
                    .map(Cargo::getNome)
                    .collect(Collectors.toList());
    }

    public boolean verificaRemuneracaoAluno(Optional<Usuario> aluno) {
        List<InscricaoIC> inscricoes = aluno.get().getInscricoesIC();

        for (InscricaoIC inscricao : inscricoes) {
            double remuneracao = inscricao.getIniciacaoCientifica().getRemuneracao();
            if (remuneracao > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean verificaEntradaDuplicada(Optional<Usuario> aluno, Integer ic_id) {
        List<InscricaoIC> inscricoes = aluno.get().getInscricoesIC();

        for(InscricaoIC inscricao : inscricoes) {
            Integer id = inscricao.getIniciacaoCientifica().getId();
            if (Objects.equals(id, ic_id)) return true;
        }
        return false;
    }

    public void criarInscricaoIC(Integer ic_id, Integer aluno_id) {

        final String CARGO_ALUNO = "Aluno";
        final String CODIGO_PADRAO = "000";

        List<String> cargosAlunoId = verificaCargoUsuario(aluno_id);

        if(!cargosAlunoId.contains(CARGO_ALUNO)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não corresponde ao acesso.");
        }

        Optional<Usuario> aluno = usuarioRepository.findById(aluno_id);

        IniciacaoCientifica IC = iniciacaoCientificaRepository.findById(ic_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "IC não encontrada."));

        boolean isRemunerado = IC.getRemuneracao() > 0;

        if (isRemunerado && verificaRemuneracaoAluno(aluno)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuário já possui uma IC remunerada.");
        }

        if(verificaEntradaDuplicada(aluno, ic_id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já está inscrito nessa IC");
        }

        Optional<SituacaoInscricao> situacao = situacaoInscricaoRepository.findByCodigo(CODIGO_PADRAO);

        InscricaoIC inscricaoIC = new InscricaoIC();

        inscricaoIC.setIniciacaoCientifica(IC);
        inscricaoIC.setAluno(aluno.get());
        inscricaoIC.setSituacaoInscricao(situacao.get());

        inscricaoICRepository.save(inscricaoIC);
    }

    public List<GetICDTO> verInscricoesIC(Integer aluno_id){

        final String CARGO_ALUNO = "Aluno";

        Optional<Usuario> aluno = usuarioRepository.findById(aluno_id);
        
        List<String> cargosAlunoId = verificaCargoUsuario(aluno_id);
        if(!cargosAlunoId.contains(CARGO_ALUNO)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O usuário não é um aluno");
        }

        List<InscricaoIC> inscricoes = aluno.get().getInscricoesIC();
        if(inscricoes.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O aluno não possui uma IC");
        }

        return inscricoes.stream().map(inscricao -> InscricaoICMapper.INSTANCE.toICDTO(inscricao)).toList();
    }

    public List<InscricaoIC> getInscricoesICAtivas(Integer icId) {
        IniciacaoCientifica ic = iniciacaoCientificaRepository.findById(icId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Iniciação Científica não encontrada!"));

        SituacaoInscricao ativas = situacaoInscricaoRepository.findByCodigo("001")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma inscrição ativa para essa IC!"));

        return inscricaoICRepository.findAllByIniciacaoCientificaAndSituacaoInscricao(ic, ativas);
    }
}