package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.entity.Cargo;
import br.ufrj.backendsiga.model.entity.Usuario;
import br.ufrj.backendsiga.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final CargoService cargoService;

    public List<Usuario> getListUsuarioByIdAndAssertCargoByNome(List<Integer> ids, String cargo) {
        List<Usuario> usuarios = usuarioRepository.findAllById(ids);
        usuarios.forEach(usuario -> assertUsuarioCargoByNome(usuario, Cargo.PROFESSOR));
        return usuarios;
    }

    public Usuario getUsuarioByMatriculaAndAssertCargoByNome(String matricula, String cargo) {
        Usuario usuario = getUsuarioByMatricula(matricula);
        assertUsuarioCargoByNome(usuario, cargo);
        return usuario;
    }
    public Usuario getUsuarioByMatriculaAndAssertCargo(String matricula, Cargo cargo) {
        Usuario usuario = getUsuarioByMatricula(matricula);
        assertUsuarioCargo(usuario, cargo);
        return usuario;
    }


    public void assertUsuarioCargoByNome(Usuario usuario, String cargo) {
        assertUsuarioCargo(usuario, cargoService.getCargoByNome(cargo));
    }
    public void assertUsuarioCargo(Usuario usuario, Cargo cargo) {
        if (!usuario.getCargos().contains(cargo)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Usuário de matrícula " +
                    usuario.getMatricula() +
                    " e cargos: " +
                    usuario.getCargos().stream().map(c -> c.getNome() + ", ").collect(Collectors.joining(", ")) +
                    "não possui cargo de " +
                    cargo.getNome() + "."
            );
        }
    };

    public List<Usuario> getAllByCargoName (String nomeCargo) {
        List<Usuario> users;
        List<Usuario> retorno = new ArrayList<Usuario>();
        Cargo cargoBuscado = cargoService.getCargoByNome(nomeCargo);

        users = usuarioRepository.findAll();

        for (Usuario user : users) {
            if(user.getCargos().contains(cargoBuscado)) {
                retorno.add(user);
            }
        }

        return retorno;
    }

    public Usuario getUsuarioByMatricula(String matricula) {
        Optional<Usuario> usuario = usuarioRepository.findByMatricula(matricula);
        if (usuario.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Usuário de matrícula " +
                            matricula +
                            " não encontrado."
            );
        }
        return usuario.get();
    }

    public Usuario getUsuarioByMatriculaOrEmail(String identificador) {
        Optional<Usuario> usuario = usuarioRepository.findByMatriculaOrEmail(identificador, identificador);
        if (usuario.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    STR."Usuário de matrícula ou email \"\{identificador}\" não encontrado."
            );
        }
        return usuario.get();
    }

}