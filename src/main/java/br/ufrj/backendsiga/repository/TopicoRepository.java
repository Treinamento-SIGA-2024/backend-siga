package br.ufrj.backendsiga.repository;

import br.ufrj.backendsiga.model.entity.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TopicoRepository extends JpaRepository<Topico, Integer> {
    
    Optional<Topico> findTopicoByNome(String nome);

}
