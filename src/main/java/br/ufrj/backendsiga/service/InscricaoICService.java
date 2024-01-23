package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.entity.IniciacaoCientifica;
import br.ufrj.backendsiga.model.entity.InscricaoIC;
import br.ufrj.backendsiga.model.entity.SituacaoInscricao;
import br.ufrj.backendsiga.model.entity.Usuario;
import br.ufrj.backendsiga.repository.IniciacaoCientificaRepository;
import br.ufrj.backendsiga.repository.InscricaoIcRepository;
import br.ufrj.backendsiga.repository.SituacaoInscricaoRepository;
import br.ufrj.backendsiga.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class InscricaoICService {
    private final UsuarioRepository usuarioRepository;
    private final IniciacaoCientificaRepository iniciacaoCientificaRepository;
    private final InscricaoIcRepository inscricaoIcRepository;
    private final SituacaoInscricaoRepository situacaoInscricaoRepository;

    public InscricaoICService(UsuarioRepository usuarioRepository,
            IniciacaoCientificaRepository iniciacaoCientificaRepository, 
            InscricaoIcRepository inscricaoIcRepository,
            SituacaoInscricaoRepository situacaoInscricaoRepository) {
        this.iniciacaoCientificaRepository = iniciacaoCientificaRepository;
        this.usuarioRepository = usuarioRepository;
        this.inscricaoIcRepository = inscricaoIcRepository;
        this.situacaoInscricaoRepository = situacaoInscricaoRepository;        
    }

    public List<InscricaoIC> findInscricoesICProfessor(String matricula, Integer icId){
        Usuario professor = usuarioRepository.findUsuarioByMatricula(matricula).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não existe no sistema."));
        //ToDo NOT FOUDN IC ID

        IniciacaoCientifica icProfessor = iniciacaoCientificaRepository.findById(icId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não é participante dessa iniciação cientifica."));

        if(!icProfessor.getProfessores().contains(professor)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario não tem acesso a essa ic.");
        }


        SituacaoInscricao teste = situacaoInscricaoRepository.findById(1).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Socorro"));


        List<InscricaoIC> teste2 = inscricaoIcRepository.findAllByIniciacaoCientifica(icProfessor);

        return teste2;

        
    }

    public InscricaoIC alterarInscricaoAluno(Integer inscricaoId, String matricula){
        InscricaoIC inscricaoICAluno = inscricaoIcRepository.findById(inscricaoId).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscrição da IC do aluno não encontrada."));
        Usuario professorAvaliador = usuarioRepository.findUsuarioByMatricula(matricula)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário professor não encontrado."));
        //ToDo verificar cargo do professor
        SituacaoInscricao situacaoAtivo = situacaoInscricaoRepository.findByCodigo("001");

        inscricaoICAluno.setSituacaoInscricao(situacaoAtivo);
        inscricaoICAluno.setProfessor(professorAvaliador);
        return inscricaoIcRepository.save(inscricaoICAluno);
    }
}