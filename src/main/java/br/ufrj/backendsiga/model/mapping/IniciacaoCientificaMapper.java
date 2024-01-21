package br.ufrj.backendsiga.model.mapping;

import org.mapstruct.Mapper;
import br.ufrj.backendsiga.model.dto.*;
import br.ufrj.backendsiga.model.entity.*;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IniciacaoCientificaMapper {
    IniciacaoCientificaMapper INSTANCE = Mappers.getMapper(IniciacaoCientificaMapper.class);

    IniciacaoCientificaNestedDTO toNestedDTO(IniciacaoCientifica iniciacaoCientifica);
}
