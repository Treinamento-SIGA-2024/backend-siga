package br.ufrj.backendsiga.repository;

import br.ufrj.backendsiga.model.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, UUID> {

}
