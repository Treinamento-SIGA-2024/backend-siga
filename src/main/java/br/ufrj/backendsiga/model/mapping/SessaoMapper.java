package br.ufrj.backendsiga.model.mapping;

import br.ufrj.backendsiga.model.dto.SessaoDTO;
import br.ufrj.backendsiga.model.entity.Sessao;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SessaoMapper {
    SessaoMapper INSTANCE = Mappers.getMapper(SessaoMapper.class);

    SessaoDTO toDto(Sessao sessao);
    Sessao toEntity(SessaoDTO sessaoDTO);
}
