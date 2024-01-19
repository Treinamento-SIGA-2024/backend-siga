package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.entity.Cargo;
import br.ufrj.backendsiga.model.entity.Usuario;
import br.ufrj.backendsiga.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public Usuario getUsuarioByMatricula(String matricula) {
        Optional<Usuario> usuario = usuarioRepository.findByMatricula(matricula);
        if (usuario.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.");
        return usuario.get();
    }
    public void assertUsuarioCargo(Usuario usuario, Cargo cargo) {
        if (!usuario.getCargos().contains(cargo)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não possui cargo de " + cargo.getNome() + ".");
        }
    };
}