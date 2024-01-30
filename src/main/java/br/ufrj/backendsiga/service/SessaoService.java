package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.dto.UsuarioLoginDTO;
import br.ufrj.backendsiga.model.entity.Sessao;
import br.ufrj.backendsiga.model.entity.Usuario;
import br.ufrj.backendsiga.repository.SessaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessaoService {
    private final UsuarioService usuarioService;
    private final SessaoRepository sessaoRepository;

    public Sessao login(UsuarioLoginDTO usuarioLoginDTO) {
        Usuario usuario = usuarioService.getUsuarioByMatriculaOrEmail(usuarioLoginDTO.getIdentificador());

        if (!usuario.getSenha().equals(usuarioLoginDTO.getSenha())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Senha inválida.");
        }

        Sessao sessao = new Sessao();
        sessao.setUsuario(usuario);
        sessao.setCriacao(LocalDateTime.now());
        sessao.setExpiraSegundos(3600);

        return sessaoRepository.save(sessao);
    }

    public Usuario validar(UUID id) {
        Optional<Sessao> optSessao = sessaoRepository.findById(id);
        if (optSessao.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sessão não encontrada");
        }
        Sessao sessao = optSessao.get();

        LocalDateTime inatividadeMaxima = sessao.getCriacao().plusSeconds(sessao.getExpiraSegundos());
        LocalDateTime agora = LocalDateTime.now();

        if (agora.isAfter(inatividadeMaxima)) {
            sessaoRepository.delete(sessao);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sessão expirada");
        }

        sessao.setCriacao(agora);
        sessaoRepository.save(sessao);

        return sessao.getUsuario();
    }
}
