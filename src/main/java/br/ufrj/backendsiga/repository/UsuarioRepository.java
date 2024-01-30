package br.ufrj.backendsiga.repository;

import br.ufrj.backendsiga.model.entity.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByMatricula(String matricula);

    Optional<Usuario> findUsuarioByMatricula(String matricula);

    Optional<Usuario> findByMatriculaOrEmail(String matricula, String email);
}