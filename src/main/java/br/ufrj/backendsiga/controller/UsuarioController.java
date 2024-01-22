package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.entity.Usuario;
import br.ufrj.backendsiga.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


//apenas para fins de testes com usuarios
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

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
