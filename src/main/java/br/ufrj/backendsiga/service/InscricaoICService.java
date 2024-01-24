package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.dto.*;
import br.ufrj.backendsiga.model.entity.IniciacaoCientifica;
import br.ufrj.backendsiga.model.entity.InscricaoIC;
import br.ufrj.backendsiga.model.entity.SituacaoInscricao;
import br.ufrj.backendsiga.model.entity.Usuario;
import br.ufrj.backendsiga.model.mapping.InscricaoICMapper;
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


        List<InscricaoIC> inscricoesPendentes = inscricaoIcRepository.findAllByIniciacaoCientificaAndSituacaoInscricao(icProfessor, situacaoPendente);

        return inscricoesPendentes.stream().map(ic -> InscricaoICMapper.INSTANCE.toPendentesDTO(ic)).toList();
    }

    public InscricaoIC alterarInscricaoAluno(Integer inscricaoId, AlterarSituacaoAlunoIcBodyDTO body){
        InscricaoIC inscricaoICAluno = inscricaoIcRepository.findById(inscricaoId).
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
        return inscricaoIcRepository.save(inscricaoICAluno);
    }
}