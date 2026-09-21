package com.clinica.multiterapias.service.mapper;

import static com.clinica.multiterapias.domain.ProntuarioAsserts.*;
import static com.clinica.multiterapias.domain.ProntuarioTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProntuarioMapperTest {

    private ProntuarioMapper prontuarioMapper;

    @BeforeEach
    void setUp() {
        prontuarioMapper = new ProntuarioMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getProntuarioSample1();
        var actual = prontuarioMapper.toEntity(prontuarioMapper.toDto(expected));
        assertProntuarioAllPropertiesEquals(expected, actual);
    }
}
