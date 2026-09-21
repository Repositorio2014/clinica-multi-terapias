package com.clinica.multiterapias.service.mapper;

import com.clinica.multiterapias.domain.Especialidade;
import com.clinica.multiterapias.domain.Profissional;
import com.clinica.multiterapias.service.dto.EspecialidadeDTO;
import com.clinica.multiterapias.service.dto.ProfissionalDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Especialidade} and its DTO {@link EspecialidadeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EspecialidadeMapper extends EntityMapper<EspecialidadeDTO, Especialidade> {
    @Mapping(target = "profissionais", source = "profissionais", qualifiedByName = "profissionalIdSet")
    EspecialidadeDTO toDto(Especialidade s);

    @Mapping(target = "profissionais", ignore = true)
    @Mapping(target = "removeProfissionais", ignore = true)
    Especialidade toEntity(EspecialidadeDTO especialidadeDTO);

    @Named("profissionalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProfissionalDTO toDtoProfissionalId(Profissional profissional);

    @Named("profissionalIdSet")
    default Set<ProfissionalDTO> toDtoProfissionalIdSet(Set<Profissional> profissional) {
        return profissional.stream().map(this::toDtoProfissionalId).collect(Collectors.toSet());
    }
}
