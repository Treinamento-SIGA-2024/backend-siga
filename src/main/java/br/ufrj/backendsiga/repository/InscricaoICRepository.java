package br.ufrj.backendsiga.repository;

import br.ufrj.backendsiga.model.entity.IniciacaoCientifica;
import br.ufrj.backendsiga.model.entity.InscricaoIC;
import br.ufrj.backendsiga.model.entity.SituacaoInscricao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscricaoICRepository extends JpaRepository<InscricaoIC, Integer> {

  List<InscricaoIC> findAllByIniciacaoCientificaAndSituacaoInscricao(IniciacaoCientifica iniciacaoCientifica, SituacaoInscricao situacaoInscricao);


}
