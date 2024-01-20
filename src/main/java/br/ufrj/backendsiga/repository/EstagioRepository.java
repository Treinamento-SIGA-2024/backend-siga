package br.ufrj.backendsiga.repository;

import br.ufrj.backendsiga.model.entity.Estagio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstagioRepository extends JpaRepository<Estagio,Integer> {
}
