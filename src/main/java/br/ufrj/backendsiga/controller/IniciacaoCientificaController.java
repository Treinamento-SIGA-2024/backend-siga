package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.entity.IniciacaoCientifica;
import br.ufrj.backendsiga.repository.IniciacaoCientificaRepository;
import br.ufrj.backendsiga.service.IniciacaoCientificaService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ic")
@RequiredArgsConstructor
@CrossOrigin
public class IniciacaoCientificaController {

    private final IniciacaoCientificaRepository iniciacaoCientificaRepository;
    private final IniciacaoCientificaService iniciacaoCientificaService;

    @GetMapping()
    public List<IniciacaoCientifica> findAllIniciacaoCientifica() {
        return iniciacaoCientificaRepository.findAll();
    }

    @GetMapping("/{icId}")
    public IniciacaoCientifica getIniciacaoCientificaById(@PathVariable String icId) {
        return iniciacaoCientificaService.getIniciacaoCientificaById(Integer.parseInt(icId));
    }

    @GetMapping("/professor/{matricula}")
    public List<IniciacaoCientifica> listIniciacaoCientificaProfessor(@PathVariable String matricula) {
        return iniciacaoCientificaService.listIniciacaoCientificaByProfessorMatricula(matricula);
    }
}
