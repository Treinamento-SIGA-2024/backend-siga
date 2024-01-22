package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.entity.Estagio;
import br.ufrj.backendsiga.repository.EstagioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EstagioService {
    private final EstagioRepository estagioRepository;

    public EstagioService(EstagioRepository estagioRepository) {
        this.estagioRepository = estagioRepository;
    }

    public List<Estagio> listAll() {
        return estagioRepository.findAll();
    }

    public Estagio getEstagioById(Integer id) {
        Estagio estagio = estagioRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Estágio não encontrado.")
        );

        return estagio;
    }

}
