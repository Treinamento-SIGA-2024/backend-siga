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
        sessao.setUltimaAtividade(LocalDateTime.now());
        sessao.setExpiraSegundos(3600);

        return sessaoRepository.save(sessao);
    }

    public Usuario validate(String id) {
        LocalDateTime agora = LocalDateTime.now();

        if (id.equals("null")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Conteúdo restrito para usuários autenticados.");
        }
        UUID uuid = UUID.fromString(id);

        Optional<Sessao> optSessao = sessaoRepository.findById(uuid);
        if (optSessao.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sessão não encontrada.");
        }
        Sessao sessao = optSessao.get();

        boolean sessaoTemTimeout = sessao.getExpiraSegundos() != null;

        if (sessaoTemTimeout) {
            LocalDateTime inatividadeMaxima = sessao.getUltimaAtividade().plusSeconds(sessao.getExpiraSegundos());
            boolean sessaoExpirou = agora.isAfter(inatividadeMaxima);
            if (sessaoExpirou) {
                sessaoRepository.delete(sessao);
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sessão expirada");
            }
        }

        sessao.setUltimaAtividade(agora);
        sessaoRepository.save(sessao);

        return sessao.getUsuario();
    }

    public Usuario validateAndAssertCargoByNome(String id, String cargo) {
        Usuario usuario = validate(id);
        usuarioService.assertUsuarioCargoByNome(usuario, cargo);
        return usuario;
    }
}
