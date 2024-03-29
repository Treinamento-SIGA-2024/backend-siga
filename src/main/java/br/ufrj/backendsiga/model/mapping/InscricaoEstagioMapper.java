package br.ufrj.backendsiga.model.mapping;

import br.ufrj.backendsiga.model.dto.getEstagioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.ufrj.backendsiga.model.dto.InscricaoEstagioPendentesDTO;
import br.ufrj.backendsiga.model.entity.InscricaoEstagio;

@Mapper
public interface InscricaoEstagioMapper {
    InscricaoEstagioMapper INSTANCE = Mappers.getMapper(InscricaoEstagioMapper.class);
    
    InscricaoEstagioPendentesDTO toPendentesDTO(InscricaoEstagio inscricaoEstagio);

    getEstagioDTO toEstagioDTO(InscricaoEstagio inscricaoEstagio);

}
