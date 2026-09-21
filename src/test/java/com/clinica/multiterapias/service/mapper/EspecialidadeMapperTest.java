package com.clinica.multiterapias.service.mapper;

import static com.clinica.multiterapias.domain.EspecialidadeAsserts.*;
import static com.clinica.multiterapias.domain.EspecialidadeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EspecialidadeMapperTest {

    private EspecialidadeMapper especialidadeMapper;

    @BeforeEach
    void setUp() {
        especialidadeMapper = new EspecialidadeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEspecialidadeSample1();
        var actual = especialidadeMapper.toEntity(especialidadeMapper.toDto(expected));
        assertEspecialidadeAllPropertiesEquals(expected, actual);
    }
}
