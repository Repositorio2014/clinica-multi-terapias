package com.clinica.multiterapias.web.rest;

import static com.clinica.multiterapias.domain.ProfissionalAsserts.*;
import static com.clinica.multiterapias.web.rest.TestUtil.createUpdateProxyForBean;
import static com.clinica.multiterapias.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.clinica.multiterapias.IntegrationTest;
import com.clinica.multiterapias.domain.Profissional;
import com.clinica.multiterapias.repository.ProfissionalRepository;
import com.clinica.multiterapias.service.ProfissionalService;
import com.clinica.multiterapias.service.dto.ProfissionalDTO;
import com.clinica.multiterapias.service.mapper.ProfissionalMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProfissionalResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProfissionalResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRO_CONSELHO = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRO_CONSELHO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final BigDecimal DEFAULT_VALOR_SESSAO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_SESSAO = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/profissionals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Mock
    private ProfissionalRepository profissionalRepositoryMock;

    @Autowired
    private ProfissionalMapper profissionalMapper;

    @Mock
    private ProfissionalService profissionalServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfissionalMockMvc;

    private Profissional profissional;

    private Profissional insertedProfissional;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profissional createEntity() {
        return new Profissional()
            .nome(DEFAULT_NOME)
            .cpf(DEFAULT_CPF)
            .registroConselho(DEFAULT_REGISTRO_CONSELHO)
            .telefone(DEFAULT_TELEFONE)
            .email(DEFAULT_EMAIL)
            .ativo(DEFAULT_ATIVO)
            .valorSessao(DEFAULT_VALOR_SESSAO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profissional createUpdatedEntity() {
        return new Profissional()
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .registroConselho(UPDATED_REGISTRO_CONSELHO)
            .telefone(UPDATED_TELEFONE)
            .email(UPDATED_EMAIL)
            .ativo(UPDATED_ATIVO)
            .valorSessao(UPDATED_VALOR_SESSAO);
    }

    @BeforeEach
    public void initTest() {
        profissional = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedProfissional != null) {
            profissionalRepository.delete(insertedProfissional);
            insertedProfissional = null;
        }
    }

    @Test
    @Transactional
    void createProfissional() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Profissional
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);
        var returnedProfissionalDTO = om.readValue(
            restProfissionalMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profissionalDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProfissionalDTO.class
        );

        // Validate the Profissional in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProfissional = profissionalMapper.toEntity(returnedProfissionalDTO);
        assertProfissionalUpdatableFieldsEquals(returnedProfissional, getPersistedProfissional(returnedProfissional));

        insertedProfissional = returnedProfissional;
    }

    @Test
    @Transactional
    void createProfissionalWithExistingId() throws Exception {
        // Create the Profissional with an existing ID
        profissional.setId(1L);
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfissionalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profissionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profissional in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        profissional.setNome(null);

        // Create the Profissional, which fails.
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);

        restProfissionalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profissionalDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCpfIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        profissional.setCpf(null);

        // Create the Profissional, which fails.
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);

        restProfissionalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profissionalDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRegistroConselhoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        profissional.setRegistroConselho(null);

        // Create the Profissional, which fails.
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);

        restProfissionalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profissionalDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefoneIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        profissional.setTelefone(null);

        // Create the Profissional, which fails.
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);

        restProfissionalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profissionalDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        profissional.setEmail(null);

        // Create the Profissional, which fails.
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);

        restProfissionalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profissionalDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAtivoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        profissional.setAtivo(null);

        // Create the Profissional, which fails.
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);

        restProfissionalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profissionalDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProfissionals() throws Exception {
        // Initialize the database
        insertedProfissional = profissionalRepository.saveAndFlush(profissional);

        // Get all the profissionalList
        restProfissionalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profissional.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].registroConselho").value(hasItem(DEFAULT_REGISTRO_CONSELHO)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].valorSessao").value(hasItem(sameNumber(DEFAULT_VALOR_SESSAO))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProfissionalsWithEagerRelationshipsIsEnabled() throws Exception {
        when(profissionalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProfissionalMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(profissionalServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProfissionalsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(profissionalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProfissionalMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(profissionalRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProfissional() throws Exception {
        // Initialize the database
        insertedProfissional = profissionalRepository.saveAndFlush(profissional);

        // Get the profissional
        restProfissionalMockMvc
            .perform(get(ENTITY_API_URL_ID, profissional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profissional.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.registroConselho").value(DEFAULT_REGISTRO_CONSELHO))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.valorSessao").value(sameNumber(DEFAULT_VALOR_SESSAO)));
    }

    @Test
    @Transactional
    void getNonExistingProfissional() throws Exception {
        // Get the profissional
        restProfissionalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProfissional() throws Exception {
        // Initialize the database
        insertedProfissional = profissionalRepository.saveAndFlush(profissional);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the profissional
        Profissional updatedProfissional = profissionalRepository.findById(profissional.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProfissional are not directly saved in db
        em.detach(updatedProfissional);
        updatedProfissional
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .registroConselho(UPDATED_REGISTRO_CONSELHO)
            .telefone(UPDATED_TELEFONE)
            .email(UPDATED_EMAIL)
            .ativo(UPDATED_ATIVO)
            .valorSessao(UPDATED_VALOR_SESSAO);
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(updatedProfissional);

        restProfissionalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profissionalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(profissionalDTO))
            )
            .andExpect(status().isOk());

        // Validate the Profissional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProfissionalToMatchAllProperties(updatedProfissional);
    }

    @Test
    @Transactional
    void putNonExistingProfissional() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profissional.setId(longCount.incrementAndGet());

        // Create the Profissional
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfissionalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profissionalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(profissionalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profissional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProfissional() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profissional.setId(longCount.incrementAndGet());

        // Create the Profissional
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfissionalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(profissionalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profissional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProfissional() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profissional.setId(longCount.incrementAndGet());

        // Create the Profissional
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfissionalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profissionalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profissional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProfissionalWithPatch() throws Exception {
        // Initialize the database
        insertedProfissional = profissionalRepository.saveAndFlush(profissional);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the profissional using partial update
        Profissional partialUpdatedProfissional = new Profissional();
        partialUpdatedProfissional.setId(profissional.getId());

        partialUpdatedProfissional.cpf(UPDATED_CPF).registroConselho(UPDATED_REGISTRO_CONSELHO).email(UPDATED_EMAIL).ativo(UPDATED_ATIVO);

        restProfissionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfissional.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProfissional))
            )
            .andExpect(status().isOk());

        // Validate the Profissional in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProfissionalUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProfissional, profissional),
            getPersistedProfissional(profissional)
        );
    }

    @Test
    @Transactional
    void fullUpdateProfissionalWithPatch() throws Exception {
        // Initialize the database
        insertedProfissional = profissionalRepository.saveAndFlush(profissional);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the profissional using partial update
        Profissional partialUpdatedProfissional = new Profissional();
        partialUpdatedProfissional.setId(profissional.getId());

        partialUpdatedProfissional
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .registroConselho(UPDATED_REGISTRO_CONSELHO)
            .telefone(UPDATED_TELEFONE)
            .email(UPDATED_EMAIL)
            .ativo(UPDATED_ATIVO)
            .valorSessao(UPDATED_VALOR_SESSAO);

        restProfissionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfissional.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProfissional))
            )
            .andExpect(status().isOk());

        // Validate the Profissional in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProfissionalUpdatableFieldsEquals(partialUpdatedProfissional, getPersistedProfissional(partialUpdatedProfissional));
    }

    @Test
    @Transactional
    void patchNonExistingProfissional() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profissional.setId(longCount.incrementAndGet());

        // Create the Profissional
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfissionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, profissionalDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(profissionalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profissional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProfissional() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profissional.setId(longCount.incrementAndGet());

        // Create the Profissional
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfissionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(profissionalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profissional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProfissional() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profissional.setId(longCount.incrementAndGet());

        // Create the Profissional
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfissionalMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(profissionalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profissional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProfissional() throws Exception {
        // Initialize the database
        insertedProfissional = profissionalRepository.saveAndFlush(profissional);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the profissional
        restProfissionalMockMvc
            .perform(delete(ENTITY_API_URL_ID, profissional.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return profissionalRepository.count();
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

    protected Profissional getPersistedProfissional(Profissional profissional) {
        return profissionalRepository.findById(profissional.getId()).orElseThrow();
    }

    protected void assertPersistedProfissionalToMatchAllProperties(Profissional expectedProfissional) {
        assertProfissionalAllPropertiesEquals(expectedProfissional, getPersistedProfissional(expectedProfissional));
    }

    protected void assertPersistedProfissionalToMatchUpdatableProperties(Profissional expectedProfissional) {
        assertProfissionalAllUpdatablePropertiesEquals(expectedProfissional, getPersistedProfissional(expectedProfissional));
    }
}
