package br.ufrj.backendsiga.repository;

import br.ufrj.backendsiga.model.entity.IniciacaoCientifica;
import br.ufrj.backendsiga.model.entity.SituacaoCriacaoIC;
import br.ufrj.backendsiga.model.entity.SituacaoInscricao;
import br.ufrj.backendsiga.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    List<IniciacaoCientifica> findAllByProfessoresAndSituacaoCriacao(Usuario professores, SituacaoCriacaoIC situacaoCriacaoIC);
    
    boolean existsByNome(String nome);

    @Query(
        value = "SELECT ic.* FROM iniciacao_cientifica ic JOIN inscricao_ic ii ON ic.id=ii.ic_id WHERE ii.aluno_id = :alunoId AND (ii.situacao_id  = 1 OR ii.situacao_id  = 2);",
        nativeQuery = true
    )
    List<IniciacaoCientifica> findIcsAlunoPendenteOuAceita(@Param("alunoId") Integer alunoId);
}
