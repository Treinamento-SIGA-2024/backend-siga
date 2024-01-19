package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.entity.IniciacaoCientifica;
import br.ufrj.backendsiga.service.IniciacaoCientificaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ic")
@RequiredArgsConstructor
public class IniciacaoCientificaController {
    private final IniciacaoCientificaService iniciacaoCientificaService;

    @GetMapping("/professor/{matricula}")
    public List<IniciacaoCientifica> listIniciacaoCientificaProfessor(@PathVariable String matricula) {
        return iniciacaoCientificaService.listIniciacaoCientificaByProfessorMatricula(matricula);
    }
}
