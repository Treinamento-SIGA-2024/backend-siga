package br.ufrj.backendsiga.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufrj.backendsiga.model.entity.IniciacaoCientifica;
import br.ufrj.backendsiga.model.entity.InscricaoIC;


@Repository
public interface InscricaoIcRepository extends JpaRepository<InscricaoIC, Integer> {
  /*
  @Query(
    "SELECT u.nome, u.matricula, ic.nome ,si.descricao FROM inscricao_ic ii JOIN usuario u ON u.id = ii.aluno_id JOIN iniciacao_cientifica ic ON ic.id = ii.ic_id JOIN situacao_inscricao si ON si.id = ii.situacao_id WHERE ic.id = :icId AND ii.situacao_id = :situacaoId"
  )
  value = "SELECT new br.ufrj.backendsiga.entity.DTO.InscricaoICDTO(u.nome, u.matricula, ic.nome, si.descricao) FROM InscricaoIC ii JOIN ii.aluno u JOIN ii.ic ic JOIN ii.situacao si WHERE ic.id = :icId AND ii.situacao.id = :situacaoId"
  InscricaoICDTO findInscricoesIcsParaAprovacaoDoProfessor(@Param("icId") Integer icId, @Param("situacaoId") Integer situacaoId);*/
  /* @Query(
    value = "SELECT u.nome, u.matricula, ic.nome , si.descricao FROM inscricao_ic ii JOIN usuario u ON u.id = ii.aluno_id JOIN iniciacao_cientifica ic ON ic.id = ii.ic_id JOIN situacao_inscricao si ON si.id = ii.situacao_id WHERE ic.id = ?1 AND ii.situacao_id = ?2",
    nativeQuery = true
  ) 
  List<InscricaoICDTO> findInscricoesIcsParaAprovacaoDoProfessor(Integer icId, Integer situacaoId); */


  List<InscricaoIC> findAllByIniciacaoCientifica(IniciacaoCientifica iniciacaoCientifica);
//No converter found capable of converting from type [org.springframework.data.jpa.repository.query.AbstractJpaQuery$TupleConverter$TupleBackedMap] to type [br.ufrj.backendsiga.entity.DTO.InscricaoICDTO]

/*   @Query(
    "SELECT u.nome, u.matricula, ic.nome, si.descricao " +
    "FROM InscricaoIC ii " +
    "JOIN ii.aluno u " +
    "JOIN ii.iniciacaoCientifica ic " +
    "JOIN ii.situacaoInscricao si " +
    "WHERE ic.id = :icId AND ii.situacaoInscricao.id = :situacaoId"
)
Object findInscricoesIcsParaAprovacaoDoProfessor(
    @Param("icId") Integer icId,
    @Param("situacaoId") Integer situacaoId
); */

/* @SqlResultSetMapping(
    name = "InscricaoICDTOMapping",
    classes = @ConstructorResult(
        targetClass = InscricaoICDTO.class,
        columns = {
            @ColumnResult(name = "nome", type = String.class),
            @ColumnResult(name = "matricula", type = String.class),
            @ColumnResult(name = "nomeIc", type = String.class),
            @ColumnResult(name = "descricao", type = String.class)
        }
    )
)
@NamedNativeQuery(
    name = "InscricaoICRepository.findInscricoesIcsParaAprovacaoDoProfessor",
    query = "SELECT u.nome AS nome, u.matricula AS matricula, ic.nome AS nomeIc, si.descricao AS descricao FROM inscricao_ic ii JOIN usuario u ON u.id = ii.aluno_id JOIN iniciacao_cientifica ic ON ic.id = ii.ic_id JOIN situacao_inscricao si ON si.id = ii.situacao_id WHERE ic.id = :icId AND ii.situacao_id = :situacaoId",
    resultSetMapping = "InscricaoICDTOMapping"
)
public interface InscricaoICRepository extends JpaRepository<InscricaoIC, Integer> {

    List<InscricaoICDTO> findInscricoesIcsParaAprovacaoDoProfessor(@Param("icId") Integer icId, @Param("situacaoId") Integer situacaoId);
}
 */

}
