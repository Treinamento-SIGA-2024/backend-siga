package br.ufrj.backendsiga.repository;

import br.ufrj.backendsiga.model.entity.IniciacaoCientifica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IniciacaoCientificaRepository extends JpaRepository<IniciacaoCientifica, Integer> {
}
