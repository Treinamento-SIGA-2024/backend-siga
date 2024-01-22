package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.entity.DTO.InscricaoICDTO;
import br.ufrj.backendsiga.entity.model.IniciacaoCientifica;
import br.ufrj.backendsiga.entity.model.InscricaoIC;
import br.ufrj.backendsiga.entity.model.SituacaoInscricao;
import br.ufrj.backendsiga.entity.model.Usuario;
import br.ufrj.backendsiga.repository.IniciacaoCientificaRepository;
import br.ufrj.backendsiga.repository.InscricaoIcRepository;
import br.ufrj.backendsiga.repository.SituacaoInscricaoRepository;
import br.ufrj.backendsiga.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

        IniciacaoCientifica icProfessor = iniciacaoCientificaRepository.findIniciacaoCientificaByProfessores(professor)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario não é participante dessa iniciação cientifica."));
        
        SituacaoInscricao teste3 = situacaoInscricaoRepository.findById(1).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Socorro"));        


        List<InscricaoIC> teste = inscricaoIcRepository.findInscricaoICByIniciacaoCientificaEqualsAndSituacaoInscricaoEquals(icProfessor, teste3);

        return teste;

        
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
        //return  inscricaoICAluno;
    }
}