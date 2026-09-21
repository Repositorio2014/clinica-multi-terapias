package com.clinica.multiterapias.service.mapper;

import com.clinica.multiterapias.domain.Paciente;
import com.clinica.multiterapias.service.dto.PacienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Paciente} and its DTO {@link PacienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface PacienteMapper extends EntityMapper<PacienteDTO, Paciente> {}
