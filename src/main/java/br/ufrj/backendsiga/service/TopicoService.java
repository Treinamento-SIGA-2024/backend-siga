package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.entity.Topico;
import br.ufrj.backendsiga.repository.TopicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Topico createTopico(Topico entity) {
        if (topicoRepository.findById(entity.getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este topico já existe.");
        }
        return topicoRepository.save(entity);
    }

    public Topico updateTopicoById(Integer id, Topico topico) {
        Topico updatedTopico = topicoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topico não encontrado."));
        updatedTopico.setNome(topico.getNome());
        return topicoRepository.save(updatedTopico);

    }

    public void deleteTopicoById(Integer id) {
        if (!topicoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Topico não encontrado.");
        }
        topicoRepository.deleteById(id);
    }
}
