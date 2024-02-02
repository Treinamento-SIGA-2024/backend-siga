package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.dto.TopicoCreateDTO;
import br.ufrj.backendsiga.model.dto.TopicoDTO;
import br.ufrj.backendsiga.model.entity.Topico;
import br.ufrj.backendsiga.model.mapping.TopicoMapper;
import br.ufrj.backendsiga.service.TopicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public TopicoDTO createTopico(@RequestBody TopicoCreateDTO novoTopico) {
        System.out.println("entrei");
        System.out.println(novoTopico);
        Topico entity = TopicoMapper.INSTANCE.toEntity(novoTopico);
        Topico createdTopico = topicoService.createTopico(entity);
        return TopicoMapper.INSTANCE.toDTO(createdTopico);
    }

    @PutMapping("{id}")
    public TopicoDTO updateTopico(@PathVariable Integer id, @RequestBody TopicoCreateDTO topicoCreateDTO) {
        Topico entity = TopicoMapper.INSTANCE.toEntity(topicoCreateDTO);
        Topico updatedTopico = topicoService.updateTopicoById(id, entity);
        return TopicoMapper.INSTANCE.toDTO(updatedTopico);
    }
    @DeleteMapping("{id}")
    public void deleteTopico(@PathVariable Integer id) {
        topicoService.deleteTopicoById(id);
    }
}
