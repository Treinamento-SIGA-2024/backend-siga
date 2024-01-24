package br.ufrj.backendsiga.repository;

import br.ufrj.backendsiga.model.entity.SituacaoInscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SituacaoInscricaoRepository extends JpaRepository<SituacaoInscricao, Integer>{
    Optional<SituacaoInscricao> findByCodigo(String codigo);
}
