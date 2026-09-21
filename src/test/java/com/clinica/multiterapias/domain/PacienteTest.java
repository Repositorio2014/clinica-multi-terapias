package com.clinica.multiterapias.domain;

import static com.clinica.multiterapias.domain.AgendaTestSamples.*;
import static com.clinica.multiterapias.domain.PacienteTestSamples.*;
import static com.clinica.multiterapias.domain.ProntuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.clinica.multiterapias.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PacienteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paciente.class);
        Paciente paciente1 = getPacienteSample1();
        Paciente paciente2 = new Paciente();
        assertThat(paciente1).isNotEqualTo(paciente2);

        paciente2.setId(paciente1.getId());
        assertThat(paciente1).isEqualTo(paciente2);

        paciente2 = getPacienteSample2();
        assertThat(paciente1).isNotEqualTo(paciente2);
    }

    @Test
    void agendasTest() {
        Paciente paciente = getPacienteRandomSampleGenerator();
        Agenda agendaBack = getAgendaRandomSampleGenerator();

        paciente.addAgendas(agendaBack);
        assertThat(paciente.getAgendas()).containsOnly(agendaBack);
        assertThat(agendaBack.getPaciente()).isEqualTo(paciente);

        paciente.removeAgendas(agendaBack);
        assertThat(paciente.getAgendas()).doesNotContain(agendaBack);
        assertThat(agendaBack.getPaciente()).isNull();

        paciente.agendas(new HashSet<>(Set.of(agendaBack)));
        assertThat(paciente.getAgendas()).containsOnly(agendaBack);
        assertThat(agendaBack.getPaciente()).isEqualTo(paciente);

        paciente.setAgendas(new HashSet<>());
        assertThat(paciente.getAgendas()).doesNotContain(agendaBack);
        assertThat(agendaBack.getPaciente()).isNull();
    }

    @Test
    void prontuariosTest() {
        Paciente paciente = getPacienteRandomSampleGenerator();
        Prontuario prontuarioBack = getProntuarioRandomSampleGenerator();

        paciente.addProntuarios(prontuarioBack);
        assertThat(paciente.getProntuarios()).containsOnly(prontuarioBack);
        assertThat(prontuarioBack.getPaciente()).isEqualTo(paciente);

        paciente.removeProntuarios(prontuarioBack);
        assertThat(paciente.getProntuarios()).doesNotContain(prontuarioBack);
        assertThat(prontuarioBack.getPaciente()).isNull();

        paciente.prontuarios(new HashSet<>(Set.of(prontuarioBack)));
        assertThat(paciente.getProntuarios()).containsOnly(prontuarioBack);
        assertThat(prontuarioBack.getPaciente()).isEqualTo(paciente);

        paciente.setProntuarios(new HashSet<>());
        assertThat(paciente.getProntuarios()).doesNotContain(prontuarioBack);
        assertThat(prontuarioBack.getPaciente()).isNull();
    }
}
