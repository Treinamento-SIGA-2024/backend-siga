package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.entity.*;
import br.ufrj.backendsiga.repository.IniciacaoCientificaRepository;
import br.ufrj.backendsiga.repository.InscricaoICRepository;
import br.ufrj.backendsiga.repository.SituacaoInscricaoRepository;
import br.ufrj.backendsiga.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InscricaoICService {

    private final InscricaoICRepository inscricaoICRepository;
    private final UsuarioRepository usuarioRepository;
    private final IniciacaoCientificaRepository iniciacaoCientificaRepository;

    private final SituacaoInscricaoRepository situacaoInscricaoRepository;

    public InscricaoICService(InscricaoICRepository inscricaoICRepository, UsuarioRepository usuarioRepository, IniciacaoCientificaRepository iniciacaoCientificaRepository, SituacaoInscricaoRepository situacaoInscricaoRepository) {
        this.inscricaoICRepository = inscricaoICRepository;
        this.usuarioRepository = usuarioRepository;
        this.iniciacaoCientificaRepository = iniciacaoCientificaRepository;
        this.situacaoInscricaoRepository = situacaoInscricaoRepository;
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

    public InscricaoIC criarInscricaoIC(Integer ic_id, Integer aluno_id,
                                        Integer professor_id) {

        final String CARGO_ALUNO = "Aluno";
        final String CARGO_PROFESSOR = "Professor";
        final String CODIGO_PADRAO = "000";

        List<String> cargosAlunoId = verificaCargoUsuario(aluno_id);
        List<String> cargosProfessorId = verificaCargoUsuario(professor_id);

        if(!cargosAlunoId.contains(CARGO_ALUNO)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não corresponde ao acesso.");
        }

        if(!cargosProfessorId.contains(CARGO_PROFESSOR)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não corresponde ao acesso.");
        }

        Optional<Usuario> aluno = usuarioRepository.findById(aluno_id);

        Optional<Usuario> professor = usuarioRepository.findById(professor_id);

        IniciacaoCientifica IC = iniciacaoCientificaRepository.findById(ic_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "IC não encontrada."));

        boolean isRemunerado = IC.getRemuneracao() > 0;

        if (isRemunerado && verificaRemuneracaoAluno(aluno)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuário já possui uma IC remunerada.");
        }

        if(verificaEntradaDuplicada(aluno, ic_id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já está inscrito nessa IC");
        }

        SituacaoInscricao situacao = situacaoInscricaoRepository.findByCodigo(CODIGO_PADRAO);

        InscricaoIC inscricaoIC = new InscricaoIC();

        inscricaoIC.setIniciacaoCientifica(IC);
        inscricaoIC.setAluno(aluno.get());
        inscricaoIC.setProfessor(professor.get());
        inscricaoIC.setSituacaoInscricao(situacao);

        return inscricaoICRepository.save(inscricaoIC);
    }



}
