package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.dto.IniciacaoCientificaNestedDTO;
import br.ufrj.backendsiga.model.mapping.IniciacaoCientificaMapper;
import br.ufrj.backendsiga.service.IniciacaoCientificaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/professor")
@RequiredArgsConstructor
public class ProfessorController {
    public final IniciacaoCientificaService iniciacaoCientificaService;

    @GetMapping("/{matricula}/iniciacoes_cientificas")
    public List<IniciacaoCientificaNestedDTO> listIniciacaoCientificaProfessor(@PathVariable String matricula) {
        return iniciacaoCientificaService
                .listIniciacaoCientificaByProfessorMatricula(matricula)
                .stream()
                .map(IniciacaoCientificaMapper.INSTANCE::toNestedDTO)
                .toList();
    }
}