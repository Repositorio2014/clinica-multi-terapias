package com.clinica.multiterapias.service.mapper;

import com.clinica.multiterapias.domain.Sala;
import com.clinica.multiterapias.service.dto.SalaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sala} and its DTO {@link SalaDTO}.
 */
@Mapper(componentModel = "spring")
public interface SalaMapper extends EntityMapper<SalaDTO, Sala> {}
