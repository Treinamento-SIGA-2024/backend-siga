package br.ufrj.backendsiga.controller;

import br.ufrj.backendsiga.model.dto.IniciacaoCientificaCreateDTO;
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
@RequestMapping("/iniciacao_cientifica")
@RequiredArgsConstructor
public class IniciacaoCientificaController {
    private final IniciacaoCientificaService iniciacaoCientificaService;

    //@PostMapping()
    //public IniciacaoCientifica createIniciacaoCientifica(@RequestBody IniciacaoCientificaCreateDTO iniciacaoCientificaCreateDTO) {
    //}
}
