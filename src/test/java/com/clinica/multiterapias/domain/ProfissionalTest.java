package com.clinica.multiterapias.domain;

import static com.clinica.multiterapias.domain.EspecialidadeTestSamples.*;
import static com.clinica.multiterapias.domain.ProfissionalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.clinica.multiterapias.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProfissionalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profissional.class);
        Profissional profissional1 = getProfissionalSample1();
        Profissional profissional2 = new Profissional();
        assertThat(profissional1).isNotEqualTo(profissional2);

        profissional2.setId(profissional1.getId());
        assertThat(profissional1).isEqualTo(profissional2);

        profissional2 = getProfissionalSample2();
        assertThat(profissional1).isNotEqualTo(profissional2);
    }

    @Test
    void especialidadesTest() {
        Profissional profissional = getProfissionalRandomSampleGenerator();
        Especialidade especialidadeBack = getEspecialidadeRandomSampleGenerator();

        profissional.addEspecialidades(especialidadeBack);
        assertThat(profissional.getEspecialidades()).containsOnly(especialidadeBack);

        profissional.removeEspecialidades(especialidadeBack);
        assertThat(profissional.getEspecialidades()).doesNotContain(especialidadeBack);

        profissional.especialidades(new HashSet<>(Set.of(especialidadeBack)));
        assertThat(profissional.getEspecialidades()).containsOnly(especialidadeBack);

        profissional.setEspecialidades(new HashSet<>());
        assertThat(profissional.getEspecialidades()).doesNotContain(especialidadeBack);
    }
}
