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

    public List<Topico> getListTopicoById(List<Integer> ids) {
        return topicoRepository.findAllById(ids);
    }
    public List<Topico> getListTopico() {
        return topicoRepository.findAll();
    }
}
