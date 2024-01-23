package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.entity.Topico;
import br.ufrj.backendsiga.repository.TopicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicoService {
    private final TopicoRepository topicoRepository;

    List<Topico> getListTopicoById(List<Integer> ids) {
        return topicoRepository.findAllById(ids);
    }
    List<Topico> getListTopico() {
        return topicoRepository.findAll();
    }
}
