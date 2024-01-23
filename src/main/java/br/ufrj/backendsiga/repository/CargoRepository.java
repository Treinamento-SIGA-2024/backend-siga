package br.ufrj.backendsiga.repository;

import br.ufrj.backendsiga.model.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {

    Optional<Cargo> findCargoByNome(String nome);
}
