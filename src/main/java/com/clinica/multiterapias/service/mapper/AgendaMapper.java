package com.clinica.multiterapias.service.mapper;

import com.clinica.multiterapias.domain.Agenda;
import com.clinica.multiterapias.domain.Especialidade;
import com.clinica.multiterapias.domain.Paciente;
import com.clinica.multiterapias.domain.Profissional;
import com.clinica.multiterapias.domain.Sala;
import com.clinica.multiterapias.service.dto.AgendaDTO;
import com.clinica.multiterapias.service.dto.EspecialidadeDTO;
import com.clinica.multiterapias.service.dto.PacienteDTO;
import com.clinica.multiterapias.service.dto.ProfissionalDTO;
import com.clinica.multiterapias.service.dto.SalaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Agenda} and its DTO {@link AgendaDTO}.
 */
@Mapper(componentModel = "spring")
public interface AgendaMapper extends EntityMapper<AgendaDTO, Agenda> {
    @Mapping(target = "paciente", source = "paciente", qualifiedByName = "pacienteNome")
    @Mapping(target = "profissional", source = "profissional", qualifiedByName = "profissionalNome")
    @Mapping(target = "sala", source = "sala", qualifiedByName = "salaNome")
    @Mapping(target = "especialidade", source = "especialidade", qualifiedByName = "especialidadeNome")
    AgendaDTO toDto(Agenda s);

    @Named("pacienteNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PacienteDTO toDtoPacienteNome(Paciente paciente);

    @Named("profissionalNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    ProfissionalDTO toDtoProfissionalNome(Profissional profissional);

    @Named("salaNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    SalaDTO toDtoSalaNome(Sala sala);

    @Named("especialidadeNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    EspecialidadeDTO toDtoEspecialidadeNome(Especialidade especialidade);
}
