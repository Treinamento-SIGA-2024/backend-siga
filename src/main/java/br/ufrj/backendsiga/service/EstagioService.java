package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.entity.model.Estagio;
import br.ufrj.backendsiga.repository.EstagioRepository;
import org.springframework.stereotype.Service;

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

}
