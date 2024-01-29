package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.dto.TopicoDTO;
import br.ufrj.backendsiga.model.entity.Topico;
import br.ufrj.backendsiga.model.mapping.TopicoMapper;
import br.ufrj.backendsiga.service.TopicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/topicos")
public class TopicosController {
    private final TopicoService topicoService;
    private final static TopicoMapper mapper = TopicoMapper.INSTANCE;

    @GetMapping()
    public List<TopicoDTO> getTopicos() {
        return topicoService.getListTopico().stream()
                .map(mapper::toDTO).toList();
    }
}
