package com.clinica.multiterapias.service.mapper;

import com.clinica.multiterapias.domain.Especialidade;
import com.clinica.multiterapias.domain.Profissional;
import com.clinica.multiterapias.service.dto.EspecialidadeDTO;
import com.clinica.multiterapias.service.dto.ProfissionalDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profissional} and its DTO {@link ProfissionalDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProfissionalMapper extends EntityMapper<ProfissionalDTO, Profissional> {
    @Mapping(target = "especialidades", source = "especialidades", qualifiedByName = "especialidadeIdSet")
    ProfissionalDTO toDto(Profissional s);

    @Mapping(target = "removeEspecialidades", ignore = true)
    Profissional toEntity(ProfissionalDTO profissionalDTO);

    @Named("especialidadeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EspecialidadeDTO toDtoEspecialidadeId(Especialidade especialidade);

    @Named("especialidadeIdSet")
    default Set<EspecialidadeDTO> toDtoEspecialidadeIdSet(Set<Especialidade> especialidade) {
        return especialidade.stream().map(this::toDtoEspecialidadeId).collect(Collectors.toSet());
    }
}
