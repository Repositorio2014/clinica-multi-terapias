package com.clinica.multiterapias.service.mapper;

import static com.clinica.multiterapias.domain.ProfissionalAsserts.*;
import static com.clinica.multiterapias.domain.ProfissionalTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProfissionalMapperTest {

    private ProfissionalMapper profissionalMapper;

    @BeforeEach
    void setUp() {
        profissionalMapper = new ProfissionalMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getProfissionalSample1();
        var actual = profissionalMapper.toEntity(profissionalMapper.toDto(expected));
        assertProfissionalAllPropertiesEquals(expected, actual);
    }
}
