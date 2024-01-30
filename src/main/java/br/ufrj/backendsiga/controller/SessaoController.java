package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.dto.SessaoDTO;
import br.ufrj.backendsiga.model.dto.UsuarioLoginDTO;
import br.ufrj.backendsiga.model.dto.UsuarioSessaoDTO;
import br.ufrj.backendsiga.model.mapping.SessaoMapper;
import br.ufrj.backendsiga.model.mapping.UsuarioMapper;
import br.ufrj.backendsiga.service.SessaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/sessao")
public class SessaoController {
    private final SessaoService sessaoService;

    private final static SessaoMapper sessaoMapper = SessaoMapper.INSTANCE;
    private final static UsuarioMapper usuarioMapper = UsuarioMapper.INSTANCE;

    @PostMapping
    public SessaoDTO Logar(@RequestBody UsuarioLoginDTO usuarioLoginDTO) {
        return sessaoMapper.toDto(sessaoService.login(usuarioLoginDTO));
    }

    @GetMapping("/id/{id}")
    public UsuarioSessaoDTO validarSessao(@PathVariable String id) {
        return usuarioMapper.toSessaoDTO(sessaoService.validar(UUID.fromString(id)));
    }
}
