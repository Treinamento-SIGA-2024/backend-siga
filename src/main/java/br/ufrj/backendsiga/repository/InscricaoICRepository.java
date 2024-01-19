package br.ufrj.backendsiga.repository;

import br.ufrj.backendsiga.model.entity.InscricaoIC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InscricaoICRepository extends JpaRepository<InscricaoIC, Integer> {

}
