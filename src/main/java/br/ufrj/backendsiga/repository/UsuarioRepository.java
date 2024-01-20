package br.ufrj.backendsiga.repository;

import br.ufrj.backendsiga.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
