package br.ufrj.backendsiga.model.mapping;

import br.ufrj.backendsiga.model.dto.TopicoDTO;
import br.ufrj.backendsiga.model.entity.Topico;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TopicoMapper {
    TopicoMapper INSTANCE = Mappers.getMapper(TopicoMapper.class);

    TopicoDTO toDTO(Topico topico);
}
