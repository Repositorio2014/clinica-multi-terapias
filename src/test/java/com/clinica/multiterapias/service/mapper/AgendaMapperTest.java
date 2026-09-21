package com.clinica.multiterapias.service.mapper;

import static com.clinica.multiterapias.domain.AgendaAsserts.*;
import static com.clinica.multiterapias.domain.AgendaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AgendaMapperTest {

    private AgendaMapper agendaMapper;

    @BeforeEach
    void setUp() {
        agendaMapper = new AgendaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAgendaSample1();
        var actual = agendaMapper.toEntity(agendaMapper.toDto(expected));
        assertAgendaAllPropertiesEquals(expected, actual);
    }
}
