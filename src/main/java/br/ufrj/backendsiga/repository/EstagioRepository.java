package br.ufrj.backendsiga.repository;

import br.ufrj.backendsiga.entity.model.Estagio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstagioRepository extends JpaRepository<Estagio,Integer> {
}
