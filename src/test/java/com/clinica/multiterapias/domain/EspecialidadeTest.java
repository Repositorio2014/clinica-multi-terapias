package com.clinica.multiterapias.domain;

import static com.clinica.multiterapias.domain.EspecialidadeTestSamples.*;
import static com.clinica.multiterapias.domain.ProfissionalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.clinica.multiterapias.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EspecialidadeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Especialidade.class);
        Especialidade especialidade1 = getEspecialidadeSample1();
        Especialidade especialidade2 = new Especialidade();
        assertThat(especialidade1).isNotEqualTo(especialidade2);

        especialidade2.setId(especialidade1.getId());
        assertThat(especialidade1).isEqualTo(especialidade2);

        especialidade2 = getEspecialidadeSample2();
        assertThat(especialidade1).isNotEqualTo(especialidade2);
    }

    @Test
    void profissionaisTest() {
        Especialidade especialidade = getEspecialidadeRandomSampleGenerator();
        Profissional profissionalBack = getProfissionalRandomSampleGenerator();

        especialidade.addProfissionais(profissionalBack);
        assertThat(especialidade.getProfissionais()).containsOnly(profissionalBack);
        assertThat(profissionalBack.getEspecialidades()).containsOnly(especialidade);

        especialidade.removeProfissionais(profissionalBack);
        assertThat(especialidade.getProfissionais()).doesNotContain(profissionalBack);
        assertThat(profissionalBack.getEspecialidades()).doesNotContain(especialidade);

        especialidade.profissionais(new HashSet<>(Set.of(profissionalBack)));
        assertThat(especialidade.getProfissionais()).containsOnly(profissionalBack);
        assertThat(profissionalBack.getEspecialidades()).containsOnly(especialidade);

        especialidade.setProfissionais(new HashSet<>());
        assertThat(especialidade.getProfissionais()).doesNotContain(profissionalBack);
        assertThat(profissionalBack.getEspecialidades()).doesNotContain(especialidade);
    }
}
