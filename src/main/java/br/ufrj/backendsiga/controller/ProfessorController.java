package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.dto.IniciacaoCientificaNestedDTO;
import br.ufrj.backendsiga.model.dto.UsuarioDTO;
import br.ufrj.backendsiga.model.entity.Cargo;
import br.ufrj.backendsiga.model.mapping.IniciacaoCientificaMapper;
import br.ufrj.backendsiga.model.mapping.UsuarioMapper;
import br.ufrj.backendsiga.service.IniciacaoCientificaService;
import br.ufrj.backendsiga.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/professor")
@RequiredArgsConstructor
public class ProfessorController {
    private final IniciacaoCientificaService iniciacaoCientificaService;
    private final UsuarioService usuarioService;

    @GetMapping()
    public List<UsuarioDTO> getAllProfessores () {
        return usuarioService
            .getAllByCargoName(Cargo.PROFESSOR)
            .stream()
            .map(UsuarioMapper.INSTANCE::toDTO)
            .toList();
    }

    @GetMapping("/{matricula}/iniciacoes_cientificas")
    public List<IniciacaoCientificaNestedDTO> listIniciacaoCientificaProfessor(@PathVariable String matricula) {
        return iniciacaoCientificaService
                .listIniciacaoCientificaByProfessorMatricula(matricula)
                .stream()
                .map(IniciacaoCientificaMapper.INSTANCE::toNestedDTO)
                .toList();
    }
}
