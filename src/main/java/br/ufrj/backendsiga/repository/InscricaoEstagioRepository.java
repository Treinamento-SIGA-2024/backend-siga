package br.ufrj.backendsiga.repository;

import br.ufrj.backendsiga.entity.model.Estagio;
import br.ufrj.backendsiga.entity.model.InscricaoEstagio;
import br.ufrj.backendsiga.entity.model.SituacaoInscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscricaoEstagioRepository extends JpaRepository<InscricaoEstagio,Integer> {

    List<InscricaoEstagio> findAllBySituacaoInscricao(SituacaoInscricao situacaoInscricao);
}