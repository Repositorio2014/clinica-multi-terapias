package com.clinica.multiterapias.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.clinica.multiterapias.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProntuarioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProntuarioDTO.class);
        ProntuarioDTO prontuarioDTO1 = new ProntuarioDTO();
        prontuarioDTO1.setId(1L);
        ProntuarioDTO prontuarioDTO2 = new ProntuarioDTO();
        assertThat(prontuarioDTO1).isNotEqualTo(prontuarioDTO2);
        prontuarioDTO2.setId(prontuarioDTO1.getId());
        assertThat(prontuarioDTO1).isEqualTo(prontuarioDTO2);
        prontuarioDTO2.setId(2L);
        assertThat(prontuarioDTO1).isNotEqualTo(prontuarioDTO2);
        prontuarioDTO1.setId(null);
        assertThat(prontuarioDTO1).isNotEqualTo(prontuarioDTO2);
    }
}
