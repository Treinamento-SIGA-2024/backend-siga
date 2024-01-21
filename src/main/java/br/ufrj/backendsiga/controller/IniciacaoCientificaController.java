package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.dto.IniciacaoCientificaNestedDTO;
import br.ufrj.backendsiga.model.dto.TopicoDTO;
import br.ufrj.backendsiga.model.entity.IniciacaoCientifica;
import br.ufrj.backendsiga.model.entity.Topico;
import br.ufrj.backendsiga.model.mapping.IniciacaoCientificaMapper;
import br.ufrj.backendsiga.service.IniciacaoCientificaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ic")
@RequiredArgsConstructor
public class IniciacaoCientificaController {
    private final IniciacaoCientificaService iniciacaoCientificaService;

    @GetMapping("/professor/{matricula}")
    public List<IniciacaoCientificaNestedDTO> listIniciacaoCientificaProfessor(@PathVariable String matricula) {
        return iniciacaoCientificaService
                .listIniciacaoCientificaByProfessorMatricula(matricula)
                .stream()
                .map(IniciacaoCientificaMapper.INSTANCE::toNestedDTO)
                .toList();
    }

   /* @PostMapping()
    public IniciacaoCientifica createIniciacaoCientifica(@RequestBody IniciacaoCientifica iniciacaoCientifica) {

    }*/
}
