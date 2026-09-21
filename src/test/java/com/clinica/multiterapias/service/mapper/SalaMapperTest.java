package com.clinica.multiterapias.service.mapper;

import static com.clinica.multiterapias.domain.SalaAsserts.*;
import static com.clinica.multiterapias.domain.SalaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SalaMapperTest {

    private SalaMapper salaMapper;

    @BeforeEach
    void setUp() {
        salaMapper = new SalaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSalaSample1();
        var actual = salaMapper.toEntity(salaMapper.toDto(expected));
        assertSalaAllPropertiesEquals(expected, actual);
    }
}
