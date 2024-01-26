package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.entity.Cargo;
import br.ufrj.backendsiga.model.entity.Usuario;
import br.ufrj.backendsiga.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


//apenas para fins de testes com usuarios
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    @GetMapping("/{usuario_id}")
    public Usuario getUser(@PathVariable Integer usuario_id) {
        Optional<Usuario> usuarioOpcional = usuarioRepository.findById(usuario_id);

        if(usuarioOpcional.isPresent()) {
            Usuario usuario = usuarioOpcional.get();
            return usuario;
        }

        return null;
    }
    @GetMapping("/")
    public List<Usuario> getUsers() {
        return usuarioRepository.findAll();
    }
}
