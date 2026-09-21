package com.clinica.multiterapias.web.rest;

import static com.clinica.multiterapias.domain.EspecialidadeAsserts.*;
import static com.clinica.multiterapias.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.clinica.multiterapias.IntegrationTest;
import com.clinica.multiterapias.domain.Especialidade;
import com.clinica.multiterapias.domain.enumeration.TipoEspecialidade;
import com.clinica.multiterapias.repository.EspecialidadeRepository;
import com.clinica.multiterapias.service.dto.EspecialidadeDTO;
import com.clinica.multiterapias.service.mapper.EspecialidadeMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EspecialidadeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EspecialidadeResourceIT {

    private static final TipoEspecialidade DEFAULT_NOME = TipoEspecialidade.PSIQUIATRIA;
    private static final TipoEspecialidade UPDATED_NOME = TipoEspecialidade.PSICOLOGIA;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/especialidades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private EspecialidadeMapper especialidadeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEspecialidadeMockMvc;

    private Especialidade especialidade;

    private Especialidade insertedEspecialidade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Especialidade createEntity() {
        return new Especialidade().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Especialidade createUpdatedEntity() {
        return new Especialidade().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
    }

    @BeforeEach
    public void initTest() {
        especialidade = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedEspecialidade != null) {
            especialidadeRepository.delete(insertedEspecialidade);
            insertedEspecialidade = null;
        }
    }

    @Test
    @Transactional
    void createEspecialidade() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Especialidade
        EspecialidadeDTO especialidadeDTO = especialidadeMapper.toDto(especialidade);
        var returnedEspecialidadeDTO = om.readValue(
            restEspecialidadeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(especialidadeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EspecialidadeDTO.class
        );

        // Validate the Especialidade in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEspecialidade = especialidadeMapper.toEntity(returnedEspecialidadeDTO);
        assertEspecialidadeUpdatableFieldsEquals(returnedEspecialidade, getPersistedEspecialidade(returnedEspecialidade));

        insertedEspecialidade = returnedEspecialidade;
    }

    @Test
    @Transactional
    void createEspecialidadeWithExistingId() throws Exception {
        // Create the Especialidade with an existing ID
        especialidade.setId(1L);
        EspecialidadeDTO especialidadeDTO = especialidadeMapper.toDto(especialidade);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEspecialidadeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(especialidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Especialidade in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        especialidade.setNome(null);

        // Create the Especialidade, which fails.
        EspecialidadeDTO especialidadeDTO = especialidadeMapper.toDto(especialidade);

        restEspecialidadeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(especialidadeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEspecialidades() throws Exception {
        // Initialize the database
        insertedEspecialidade = especialidadeRepository.saveAndFlush(especialidade);

        // Get all the especialidadeList
        restEspecialidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(especialidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getEspecialidade() throws Exception {
        // Initialize the database
        insertedEspecialidade = especialidadeRepository.saveAndFlush(especialidade);

        // Get the especialidade
        restEspecialidadeMockMvc
            .perform(get(ENTITY_API_URL_ID, especialidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(especialidade.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getNonExistingEspecialidade() throws Exception {
        // Get the especialidade
        restEspecialidadeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEspecialidade() throws Exception {
        // Initialize the database
        insertedEspecialidade = especialidadeRepository.saveAndFlush(especialidade);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the especialidade
        Especialidade updatedEspecialidade = especialidadeRepository.findById(especialidade.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEspecialidade are not directly saved in db
        em.detach(updatedEspecialidade);
        updatedEspecialidade.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        EspecialidadeDTO especialidadeDTO = especialidadeMapper.toDto(updatedEspecialidade);

        restEspecialidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, especialidadeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(especialidadeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Especialidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEspecialidadeToMatchAllProperties(updatedEspecialidade);
    }

    @Test
    @Transactional
    void putNonExistingEspecialidade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        especialidade.setId(longCount.incrementAndGet());

        // Create the Especialidade
        EspecialidadeDTO especialidadeDTO = especialidadeMapper.toDto(especialidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspecialidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, especialidadeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(especialidadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especialidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEspecialidade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        especialidade.setId(longCount.incrementAndGet());

        // Create the Especialidade
        EspecialidadeDTO especialidadeDTO = especialidadeMapper.toDto(especialidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(especialidadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especialidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEspecialidade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        especialidade.setId(longCount.incrementAndGet());

        // Create the Especialidade
        EspecialidadeDTO especialidadeDTO = especialidadeMapper.toDto(especialidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialidadeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(especialidadeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Especialidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEspecialidadeWithPatch() throws Exception {
        // Initialize the database
        insertedEspecialidade = especialidadeRepository.saveAndFlush(especialidade);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the especialidade using partial update
        Especialidade partialUpdatedEspecialidade = new Especialidade();
        partialUpdatedEspecialidade.setId(especialidade.getId());

        restEspecialidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEspecialidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEspecialidade))
            )
            .andExpect(status().isOk());

        // Validate the Especialidade in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEspecialidadeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEspecialidade, especialidade),
            getPersistedEspecialidade(especialidade)
        );
    }

    @Test
    @Transactional
    void fullUpdateEspecialidadeWithPatch() throws Exception {
        // Initialize the database
        insertedEspecialidade = especialidadeRepository.saveAndFlush(especialidade);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the especialidade using partial update
        Especialidade partialUpdatedEspecialidade = new Especialidade();
        partialUpdatedEspecialidade.setId(especialidade.getId());

        partialUpdatedEspecialidade.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restEspecialidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEspecialidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEspecialidade))
            )
            .andExpect(status().isOk());

        // Validate the Especialidade in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEspecialidadeUpdatableFieldsEquals(partialUpdatedEspecialidade, getPersistedEspecialidade(partialUpdatedEspecialidade));
    }

    @Test
    @Transactional
    void patchNonExistingEspecialidade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        especialidade.setId(longCount.incrementAndGet());

        // Create the Especialidade
        EspecialidadeDTO especialidadeDTO = especialidadeMapper.toDto(especialidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspecialidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, especialidadeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(especialidadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especialidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEspecialidade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        especialidade.setId(longCount.incrementAndGet());

        // Create the Especialidade
        EspecialidadeDTO especialidadeDTO = especialidadeMapper.toDto(especialidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(especialidadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especialidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEspecialidade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        especialidade.setId(longCount.incrementAndGet());

        // Create the Especialidade
        EspecialidadeDTO especialidadeDTO = especialidadeMapper.toDto(especialidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialidadeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(especialidadeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Especialidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEspecialidade() throws Exception {
        // Initialize the database
        insertedEspecialidade = especialidadeRepository.saveAndFlush(especialidade);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the especialidade
        restEspecialidadeMockMvc
            .perform(delete(ENTITY_API_URL_ID, especialidade.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return especialidadeRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Especialidade getPersistedEspecialidade(Especialidade especialidade) {
        return especialidadeRepository.findById(especialidade.getId()).orElseThrow();
    }

    protected void assertPersistedEspecialidadeToMatchAllProperties(Especialidade expectedEspecialidade) {
        assertEspecialidadeAllPropertiesEquals(expectedEspecialidade, getPersistedEspecialidade(expectedEspecialidade));
    }

    protected void assertPersistedEspecialidadeToMatchUpdatableProperties(Especialidade expectedEspecialidade) {
        assertEspecialidadeAllUpdatablePropertiesEquals(expectedEspecialidade, getPersistedEspecialidade(expectedEspecialidade));
    }
}
