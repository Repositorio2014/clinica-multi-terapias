package com.clinica.multiterapias.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.clinica.multiterapias.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AgendaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgendaDTO.class);
        AgendaDTO agendaDTO1 = new AgendaDTO();
        agendaDTO1.setId(1L);
        AgendaDTO agendaDTO2 = new AgendaDTO();
        assertThat(agendaDTO1).isNotEqualTo(agendaDTO2);
        agendaDTO2.setId(agendaDTO1.getId());
        assertThat(agendaDTO1).isEqualTo(agendaDTO2);
        agendaDTO2.setId(2L);
        assertThat(agendaDTO1).isNotEqualTo(agendaDTO2);
        agendaDTO1.setId(null);
        assertThat(agendaDTO1).isNotEqualTo(agendaDTO2);
    }
}
