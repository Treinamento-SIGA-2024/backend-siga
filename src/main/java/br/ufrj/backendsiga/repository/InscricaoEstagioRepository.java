package br.ufrj.backendsiga.repository;

import br.ufrj.backendsiga.model.entity.InscricaoEstagio;
import br.ufrj.backendsiga.model.entity.SituacaoInscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscricaoEstagioRepository extends JpaRepository<InscricaoEstagio,Integer> {

    List<InscricaoEstagio> findAllBySituacaoInscricao(SituacaoInscricao situacaoInscricao);
}