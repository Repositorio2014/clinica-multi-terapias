package com.clinica.multiterapias.service.mapper;

import com.clinica.multiterapias.domain.Agenda;
import com.clinica.multiterapias.domain.Especialidade;
import com.clinica.multiterapias.domain.Paciente;
import com.clinica.multiterapias.domain.Profissional;
import com.clinica.multiterapias.domain.Prontuario;
import com.clinica.multiterapias.service.dto.AgendaDTO;
import com.clinica.multiterapias.service.dto.EspecialidadeDTO;
import com.clinica.multiterapias.service.dto.PacienteDTO;
import com.clinica.multiterapias.service.dto.ProfissionalDTO;
import com.clinica.multiterapias.service.dto.ProntuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Prontuario} and its DTO {@link ProntuarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProntuarioMapper extends EntityMapper<ProntuarioDTO, Prontuario> {
    @Mapping(target = "paciente", source = "paciente", qualifiedByName = "pacienteNome")
    @Mapping(target = "profissional", source = "profissional", qualifiedByName = "profissionalNome")
    @Mapping(target = "agenda", source = "agenda", qualifiedByName = "agendaId")
    @Mapping(target = "especialidade", source = "especialidade", qualifiedByName = "especialidadeNome")
    ProntuarioDTO toDto(Prontuario s);

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

    @Named("agendaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AgendaDTO toDtoAgendaId(Agenda agenda);

    @Named("especialidadeNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    EspecialidadeDTO toDtoEspecialidadeNome(Especialidade especialidade);
}
