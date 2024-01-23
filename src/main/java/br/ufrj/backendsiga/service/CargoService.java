package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.entity.Cargo;
import br.ufrj.backendsiga.repository.CargoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CargoService {
    private final CargoRepository cargoRepository;

    public Cargo getCargoByNome(String nome) {
        Optional<Cargo> cargo = cargoRepository.findCargoByNome(nome);
        if (cargo.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cargo não existe");
        return cargo.get();
    }
}
