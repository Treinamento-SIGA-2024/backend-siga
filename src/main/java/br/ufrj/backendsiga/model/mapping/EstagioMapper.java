package br.ufrj.backendsiga.model.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EstagioMapper {
    EstagioMapper INSTANCE = Mappers.getMapper(EstagioMapper.class);


}
