package com.clinica.multiterapias.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.clinica.multiterapias.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProfissionalDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfissionalDTO.class);
        ProfissionalDTO profissionalDTO1 = new ProfissionalDTO();
        profissionalDTO1.setId(1L);
        ProfissionalDTO profissionalDTO2 = new ProfissionalDTO();
        assertThat(profissionalDTO1).isNotEqualTo(profissionalDTO2);
        profissionalDTO2.setId(profissionalDTO1.getId());
        assertThat(profissionalDTO1).isEqualTo(profissionalDTO2);
        profissionalDTO2.setId(2L);
        assertThat(profissionalDTO1).isNotEqualTo(profissionalDTO2);
        profissionalDTO1.setId(null);
        assertThat(profissionalDTO1).isNotEqualTo(profissionalDTO2);
    }
}
