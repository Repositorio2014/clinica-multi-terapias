package com.clinica.multiterapias.domain;

import static com.clinica.multiterapias.domain.SalaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.clinica.multiterapias.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sala.class);
        Sala sala1 = getSalaSample1();
        Sala sala2 = new Sala();
        assertThat(sala1).isNotEqualTo(sala2);

        sala2.setId(sala1.getId());
        assertThat(sala1).isEqualTo(sala2);

        sala2 = getSalaSample2();
        assertThat(sala1).isNotEqualTo(sala2);
    }
}
