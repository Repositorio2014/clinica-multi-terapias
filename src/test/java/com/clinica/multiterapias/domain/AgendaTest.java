package com.clinica.multiterapias.domain;

import static com.clinica.multiterapias.domain.AgendaTestSamples.*;
import static com.clinica.multiterapias.domain.EspecialidadeTestSamples.*;
import static com.clinica.multiterapias.domain.PacienteTestSamples.*;
import static com.clinica.multiterapias.domain.ProfissionalTestSamples.*;
import static com.clinica.multiterapias.domain.SalaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.clinica.multiterapias.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AgendaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agenda.class);
        Agenda agenda1 = getAgendaSample1();
        Agenda agenda2 = new Agenda();
        assertThat(agenda1).isNotEqualTo(agenda2);

        agenda2.setId(agenda1.getId());
        assertThat(agenda1).isEqualTo(agenda2);

        agenda2 = getAgendaSample2();
        assertThat(agenda1).isNotEqualTo(agenda2);
    }

    @Test
    void pacienteTest() {
        Agenda agenda = getAgendaRandomSampleGenerator();
        Paciente pacienteBack = getPacienteRandomSampleGenerator();

        agenda.setPaciente(pacienteBack);
        assertThat(agenda.getPaciente()).isEqualTo(pacienteBack);

        agenda.paciente(null);
        assertThat(agenda.getPaciente()).isNull();
    }

    @Test
    void profissionalTest() {
        Agenda agenda = getAgendaRandomSampleGenerator();
        Profissional profissionalBack = getProfissionalRandomSampleGenerator();

        agenda.setProfissional(profissionalBack);
        assertThat(agenda.getProfissional()).isEqualTo(profissionalBack);

        agenda.profissional(null);
        assertThat(agenda.getProfissional()).isNull();
    }

    @Test
    void salaTest() {
        Agenda agenda = getAgendaRandomSampleGenerator();
        Sala salaBack = getSalaRandomSampleGenerator();

        agenda.setSala(salaBack);
        assertThat(agenda.getSala()).isEqualTo(salaBack);

        agenda.sala(null);
        assertThat(agenda.getSala()).isNull();
    }

    @Test
    void especialidadeTest() {
        Agenda agenda = getAgendaRandomSampleGenerator();
        Especialidade especialidadeBack = getEspecialidadeRandomSampleGenerator();

        agenda.setEspecialidade(especialidadeBack);
        assertThat(agenda.getEspecialidade()).isEqualTo(especialidadeBack);

        agenda.especialidade(null);
        assertThat(agenda.getEspecialidade()).isNull();
    }
}
