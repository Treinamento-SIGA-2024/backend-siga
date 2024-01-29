package br.ufrj.backendsiga.repository;

import br.ufrj.backendsiga.model.entity.IniciacaoCientifica;
import br.ufrj.backendsiga.model.entity.SituacaoCriacaoIC;
import br.ufrj.backendsiga.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IniciacaoCientificaRepository extends JpaRepository<IniciacaoCientifica, Integer> {
    
    List<IniciacaoCientifica> findAllByProfessoresIsContaining(Usuario professor);

    Optional<IniciacaoCientifica> findAllByProfessores(Usuario professores);

    List<IniciacaoCientifica> findAllBySituacaoCriacao(SituacaoCriacaoIC situacao);
}
