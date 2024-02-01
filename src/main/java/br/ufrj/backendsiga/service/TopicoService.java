package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.entity.Topico;
import br.ufrj.backendsiga.repository.TopicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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
        Optional<Topico> topico = topicoRepository.findTopicoByNome(entity.getNome());
        if (topico.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este topico já existe.");
        }
        Topico novoTopico = new Topico();
        novoTopico.setNome(entity.getNome());

        return topicoRepository.save(novoTopico);
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
