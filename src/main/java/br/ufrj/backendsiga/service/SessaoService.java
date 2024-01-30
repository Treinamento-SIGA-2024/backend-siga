package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.dto.UsuarioLoginDTO;
import br.ufrj.backendsiga.model.entity.Sessao;
import br.ufrj.backendsiga.model.entity.Usuario;
import br.ufrj.backendsiga.repository.SessaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SessaoService {
    private final UsuarioService usuarioService;
    private final SessaoRepository sessaoRepository;

    public Sessao Login(UsuarioLoginDTO usuarioLoginDTO) {
        Usuario usuario = usuarioService.getUsuarioByMatriculaOrEmail(usuarioLoginDTO.getIdentificador());

        String senhaReal = usuario.getSenha();
        String senhaTentativa = usuarioLoginDTO.getSenha();

        if (!senhaReal.equals(senhaTentativa)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Senha inválida.");
        }

        Sessao sessao = new Sessao();
        sessao.setUsuario(usuario);
        sessao.setExpira_segundos(3600);

        sessaoRepository.saveAndFlush(sessao);

        return sessaoRepository.findById(sessao.getId()).get();
    }
}
