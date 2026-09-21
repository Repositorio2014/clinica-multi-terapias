package com.clinica.multiterapias.domain;

import static com.clinica.multiterapias.domain.AgendaTestSamples.*;
import static com.clinica.multiterapias.domain.EspecialidadeTestSamples.*;
import static com.clinica.multiterapias.domain.PacienteTestSamples.*;
import static com.clinica.multiterapias.domain.ProfissionalTestSamples.*;
import static com.clinica.multiterapias.domain.ProntuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.clinica.multiterapias.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProntuarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prontuario.class);
        Prontuario prontuario1 = getProntuarioSample1();
        Prontuario prontuario2 = new Prontuario();
        assertThat(prontuario1).isNotEqualTo(prontuario2);

        prontuario2.setId(prontuario1.getId());
        assertThat(prontuario1).isEqualTo(prontuario2);

        prontuario2 = getProntuarioSample2();
        assertThat(prontuario1).isNotEqualTo(prontuario2);
    }

    @Test
    void pacienteTest() {
        Prontuario prontuario = getProntuarioRandomSampleGenerator();
        Paciente pacienteBack = getPacienteRandomSampleGenerator();

        prontuario.setPaciente(pacienteBack);
        assertThat(prontuario.getPaciente()).isEqualTo(pacienteBack);

        prontuario.paciente(null);
        assertThat(prontuario.getPaciente()).isNull();
    }

    @Test
    void profissionalTest() {
        Prontuario prontuario = getProntuarioRandomSampleGenerator();
        Profissional profissionalBack = getProfissionalRandomSampleGenerator();

        prontuario.setProfissional(profissionalBack);
        assertThat(prontuario.getProfissional()).isEqualTo(profissionalBack);

        prontuario.profissional(null);
        assertThat(prontuario.getProfissional()).isNull();
    }

    @Test
    void agendaTest() {
        Prontuario prontuario = getProntuarioRandomSampleGenerator();
        Agenda agendaBack = getAgendaRandomSampleGenerator();

        prontuario.setAgenda(agendaBack);
        assertThat(prontuario.getAgenda()).isEqualTo(agendaBack);

        prontuario.agenda(null);
        assertThat(prontuario.getAgenda()).isNull();
    }

    @Test
    void especialidadeTest() {
        Prontuario prontuario = getProntuarioRandomSampleGenerator();
        Especialidade especialidadeBack = getEspecialidadeRandomSampleGenerator();

        prontuario.setEspecialidade(especialidadeBack);
        assertThat(prontuario.getEspecialidade()).isEqualTo(especialidadeBack);

        prontuario.especialidade(null);
        assertThat(prontuario.getEspecialidade()).isNull();
    }
}
