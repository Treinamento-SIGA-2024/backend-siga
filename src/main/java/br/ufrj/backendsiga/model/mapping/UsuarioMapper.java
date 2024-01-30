package br.ufrj.backendsiga.model.mapping;

import br.ufrj.backendsiga.model.dto.UsuarioDTO;
import br.ufrj.backendsiga.model.dto.UsuarioLoginDTO;
import br.ufrj.backendsiga.model.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    UsuarioDTO toDTO(Usuario usuario);
    Usuario toEntity(UsuarioLoginDTO usuarioLoginDTO);
}