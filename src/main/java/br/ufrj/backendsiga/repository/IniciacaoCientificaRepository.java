package br.ufrj.backendsiga.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufrj.backendsiga.model.entity.IniciacaoCientifica;
import br.ufrj.backendsiga.model.entity.Usuario;

@Repository
public interface IniciacaoCientificaRepository extends JpaRepository<IniciacaoCientifica, Integer> {

/*   @Query("SELECT ic.id, u.id FROM iniciacao_cientifica ic " +
      "JOIN r_professor_ic rpi ON rpi.ic_id = ic.id " +
      "JOIN usuario u ON u.id = rpi.professor_id " +
      "JOIN inscricao_ic ii ON ii.ic_id = ic.id " +
      "WHERE u.id = 2 AND ic.id = 1;")
  IniciacaoCientifica findIniciacaoCientificaById(); */

  Optional<IniciacaoCientifica> findAllByProfessores(Usuario professores);

}
