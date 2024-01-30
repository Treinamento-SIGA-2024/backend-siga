package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.dto.SessaoDTO;
import br.ufrj.backendsiga.model.dto.UsuarioLoginDTO;
import br.ufrj.backendsiga.model.mapping.SessaoMapper;
import br.ufrj.backendsiga.model.mapping.UsuarioMapper;
import br.ufrj.backendsiga.service.SessaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/sessao")
public class SessaoController {
    private final SessaoService sessaoService;

    private final static SessaoMapper sessaoMapper = SessaoMapper.INSTANCE;

    @PostMapping
    public SessaoDTO Logar(@RequestBody UsuarioLoginDTO usuarioLoginDTO) {
        return sessaoMapper.toDto(sessaoService.Login(usuarioLoginDTO));
    }
}
