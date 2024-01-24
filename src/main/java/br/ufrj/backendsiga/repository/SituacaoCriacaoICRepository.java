package br.ufrj.backendsiga.repository;

import br.ufrj.backendsiga.model.entity.SituacaoCriacaoIC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SituacaoCriacaoICRepository extends JpaRepository<SituacaoCriacaoIC, Integer> {
    Optional<SituacaoCriacaoIC> findByCodigo(String codigo);
}
