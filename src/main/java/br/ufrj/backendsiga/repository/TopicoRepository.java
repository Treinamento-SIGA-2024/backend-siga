package br.ufrj.backendsiga.repository;

import br.ufrj.backendsiga.model.entity.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TopicoRepository extends JpaRepository<Topico, Integer> {
    
    Topico findTopicoByNome(String nome);
}
