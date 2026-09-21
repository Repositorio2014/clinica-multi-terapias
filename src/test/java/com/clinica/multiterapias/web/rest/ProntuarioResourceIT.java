package com.clinica.multiterapias.web.rest;

import static com.clinica.multiterapias.domain.ProntuarioAsserts.*;
import static com.clinica.multiterapias.web.rest.TestUtil.createUpdateProxyForBean;
import static com.clinica.multiterapias.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.clinica.multiterapias.IntegrationTest;
import com.clinica.multiterapias.domain.Agenda;
import com.clinica.multiterapias.domain.Especialidade;
import com.clinica.multiterapias.domain.Paciente;
import com.clinica.multiterapias.domain.Profissional;
import com.clinica.multiterapias.domain.Prontuario;
import com.clinica.multiterapias.domain.enumeration.TipoProntuario;
import com.clinica.multiterapias.repository.ProntuarioRepository;
import com.clinica.multiterapias.service.ProntuarioService;
import com.clinica.multiterapias.service.dto.ProntuarioDTO;
import com.clinica.multiterapias.service.mapper.ProntuarioMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link ProntuarioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProntuarioResourceIT {

    private static final TipoProntuario DEFAULT_TIPO = TipoProntuario.ANAMNESE;
    private static final TipoProntuario UPDATED_TIPO = TipoProntuario.EVOLUCAO;

    private static final ZonedDateTime DEFAULT_DATA_ATENDIMENTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_ATENDIMENTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_ATENDIMENTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEUDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTEUDO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONFIDENCIAL = false;
    private static final Boolean UPDATED_CONFIDENCIAL = true;

    private static final String ENTITY_API_URL = "/api/prontuarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Mock
    private ProntuarioRepository prontuarioRepositoryMock;

    @Autowired
    private ProntuarioMapper prontuarioMapper;

    @Mock
    private ProntuarioService prontuarioServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProntuarioMockMvc;

    private Prontuario prontuario;

    private Prontuario insertedProntuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prontuario createEntity(EntityManager em) {
        Prontuario prontuario = new Prontuario()
            .tipo(DEFAULT_TIPO)
            .dataAtendimento(DEFAULT_DATA_ATENDIMENTO)
            .titulo(DEFAULT_TITULO)
            .conteudo(DEFAULT_CONTEUDO)
            .confidencial(DEFAULT_CONFIDENCIAL);
        // Add required entity
        Paciente paciente;
        if (TestUtil.findAll(em, Paciente.class).isEmpty()) {
            paciente = PacienteResourceIT.createEntity();
            em.persist(paciente);
            em.flush();
        } else {
            paciente = TestUtil.findAll(em, Paciente.class).get(0);
        }
        prontuario.setPaciente(paciente);
        // Add required entity
        Profissional profissional;
        if (TestUtil.findAll(em, Profissional.class).isEmpty()) {
            profissional = ProfissionalResourceIT.createEntity();
            em.persist(profissional);
            em.flush();
        } else {
            profissional = TestUtil.findAll(em, Profissional.class).get(0);
        }
        prontuario.setProfissional(profissional);
        // Add required entity
        Especialidade especialidade;
        if (TestUtil.findAll(em, Especialidade.class).isEmpty()) {
            especialidade = EspecialidadeResourceIT.createEntity();
            em.persist(especialidade);
            em.flush();
        } else {
            especialidade = TestUtil.findAll(em, Especialidade.class).get(0);
        }
        prontuario.setEspecialidade(especialidade);
        return prontuario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prontuario createUpdatedEntity(EntityManager em) {
        Prontuario updatedProntuario = new Prontuario()
            .tipo(UPDATED_TIPO)
            .dataAtendimento(UPDATED_DATA_ATENDIMENTO)
            .titulo(UPDATED_TITULO)
            .conteudo(UPDATED_CONTEUDO)
            .confidencial(UPDATED_CONFIDENCIAL);
        // Add required entity
        Paciente paciente;
        if (TestUtil.findAll(em, Paciente.class).isEmpty()) {
            paciente = PacienteResourceIT.createUpdatedEntity();
            em.persist(paciente);
            em.flush();
        } else {
            paciente = TestUtil.findAll(em, Paciente.class).get(0);
        }
        updatedProntuario.setPaciente(paciente);
        // Add required entity
        Profissional profissional;
        if (TestUtil.findAll(em, Profissional.class).isEmpty()) {
            profissional = ProfissionalResourceIT.createUpdatedEntity();
            em.persist(profissional);
            em.flush();
        } else {
            profissional = TestUtil.findAll(em, Profissional.class).get(0);
        }
        updatedProntuario.setProfissional(profissional);
        // Add required entity
        Especialidade especialidade;
        if (TestUtil.findAll(em, Especialidade.class).isEmpty()) {
            especialidade = EspecialidadeResourceIT.createUpdatedEntity();
            em.persist(especialidade);
            em.flush();
        } else {
            especialidade = TestUtil.findAll(em, Especialidade.class).get(0);
        }
        updatedProntuario.setEspecialidade(especialidade);
        return updatedProntuario;
    }

    @BeforeEach
    public void initTest() {
        prontuario = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedProntuario != null) {
            prontuarioRepository.delete(insertedProntuario);
            insertedProntuario = null;
        }
    }

    @Test
    @Transactional
    void createProntuario() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Prontuario
        ProntuarioDTO prontuarioDTO = prontuarioMapper.toDto(prontuario);
        var returnedProntuarioDTO = om.readValue(
            restProntuarioMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prontuarioDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProntuarioDTO.class
        );

        // Validate the Prontuario in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProntuario = prontuarioMapper.toEntity(returnedProntuarioDTO);
        assertProntuarioUpdatableFieldsEquals(returnedProntuario, getPersistedProntuario(returnedProntuario));

        insertedProntuario = returnedProntuario;
    }

    @Test
    @Transactional
    void createProntuarioWithExistingId() throws Exception {
        // Create the Prontuario with an existing ID
        prontuario.setId(1L);
        ProntuarioDTO prontuarioDTO = prontuarioMapper.toDto(prontuario);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProntuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prontuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prontuario in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        prontuario.setTipo(null);

        // Create the Prontuario, which fails.
        ProntuarioDTO prontuarioDTO = prontuarioMapper.toDto(prontuario);

        restProntuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prontuarioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataAtendimentoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        prontuario.setDataAtendimento(null);

        // Create the Prontuario, which fails.
        ProntuarioDTO prontuarioDTO = prontuarioMapper.toDto(prontuario);

        restProntuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prontuarioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTituloIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        prontuario.setTitulo(null);

        // Create the Prontuario, which fails.
        ProntuarioDTO prontuarioDTO = prontuarioMapper.toDto(prontuario);

        restProntuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prontuarioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConfidencialIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        prontuario.setConfidencial(null);

        // Create the Prontuario, which fails.
        ProntuarioDTO prontuarioDTO = prontuarioMapper.toDto(prontuario);

        restProntuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prontuarioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProntuarios() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList
        restProntuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prontuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].dataAtendimento").value(hasItem(sameInstant(DEFAULT_DATA_ATENDIMENTO))))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(DEFAULT_CONTEUDO.toString())))
            .andExpect(jsonPath("$.[*].confidencial").value(hasItem(DEFAULT_CONFIDENCIAL.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProntuariosWithEagerRelationshipsIsEnabled() throws Exception {
        when(prontuarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProntuarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(prontuarioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProntuariosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(prontuarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProntuarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(prontuarioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProntuario() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get the prontuario
        restProntuarioMockMvc
            .perform(get(ENTITY_API_URL_ID, prontuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prontuario.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.dataAtendimento").value(sameInstant(DEFAULT_DATA_ATENDIMENTO)))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.conteudo").value(DEFAULT_CONTEUDO.toString()))
            .andExpect(jsonPath("$.confidencial").value(DEFAULT_CONFIDENCIAL.booleanValue()));
    }

    @Test
    @Transactional
    void getProntuariosByIdFiltering() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        Long id = prontuario.getId();

        defaultProntuarioFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultProntuarioFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultProntuarioFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProntuariosByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList where tipo equals to
        defaultProntuarioFiltering("tipo.equals=" + DEFAULT_TIPO, "tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllProntuariosByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList where tipo in
        defaultProntuarioFiltering("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO, "tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllProntuariosByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList where tipo is not null
        defaultProntuarioFiltering("tipo.specified=true", "tipo.specified=false");
    }

    @Test
    @Transactional
    void getAllProntuariosByDataAtendimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList where dataAtendimento equals to
        defaultProntuarioFiltering(
            "dataAtendimento.equals=" + DEFAULT_DATA_ATENDIMENTO,
            "dataAtendimento.equals=" + UPDATED_DATA_ATENDIMENTO
        );
    }

    @Test
    @Transactional
    void getAllProntuariosByDataAtendimentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList where dataAtendimento in
        defaultProntuarioFiltering(
            "dataAtendimento.in=" + DEFAULT_DATA_ATENDIMENTO + "," + UPDATED_DATA_ATENDIMENTO,
            "dataAtendimento.in=" + UPDATED_DATA_ATENDIMENTO
        );
    }

    @Test
    @Transactional
    void getAllProntuariosByDataAtendimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList where dataAtendimento is not null
        defaultProntuarioFiltering("dataAtendimento.specified=true", "dataAtendimento.specified=false");
    }

    @Test
    @Transactional
    void getAllProntuariosByDataAtendimentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList where dataAtendimento is greater than or equal to
        defaultProntuarioFiltering(
            "dataAtendimento.greaterThanOrEqual=" + DEFAULT_DATA_ATENDIMENTO,
            "dataAtendimento.greaterThanOrEqual=" + UPDATED_DATA_ATENDIMENTO
        );
    }

    @Test
    @Transactional
    void getAllProntuariosByDataAtendimentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList where dataAtendimento is less than or equal to
        defaultProntuarioFiltering(
            "dataAtendimento.lessThanOrEqual=" + DEFAULT_DATA_ATENDIMENTO,
            "dataAtendimento.lessThanOrEqual=" + SMALLER_DATA_ATENDIMENTO
        );
    }

    @Test
    @Transactional
    void getAllProntuariosByDataAtendimentoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList where dataAtendimento is less than
        defaultProntuarioFiltering(
            "dataAtendimento.lessThan=" + UPDATED_DATA_ATENDIMENTO,
            "dataAtendimento.lessThan=" + DEFAULT_DATA_ATENDIMENTO
        );
    }

    @Test
    @Transactional
    void getAllProntuariosByDataAtendimentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList where dataAtendimento is greater than
        defaultProntuarioFiltering(
            "dataAtendimento.greaterThan=" + SMALLER_DATA_ATENDIMENTO,
            "dataAtendimento.greaterThan=" + DEFAULT_DATA_ATENDIMENTO
        );
    }

    @Test
    @Transactional
    void getAllProntuariosByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList where titulo equals to
        defaultProntuarioFiltering("titulo.equals=" + DEFAULT_TITULO, "titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllProntuariosByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList where titulo in
        defaultProntuarioFiltering("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO, "titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllProntuariosByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList where titulo is not null
        defaultProntuarioFiltering("titulo.specified=true", "titulo.specified=false");
    }

    @Test
    @Transactional
    void getAllProntuariosByTituloContainsSomething() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList where titulo contains
        defaultProntuarioFiltering("titulo.contains=" + DEFAULT_TITULO, "titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllProntuariosByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList where titulo does not contain
        defaultProntuarioFiltering("titulo.doesNotContain=" + UPDATED_TITULO, "titulo.doesNotContain=" + DEFAULT_TITULO);
    }

    @Test
    @Transactional
    void getAllProntuariosByConfidencialIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList where confidencial equals to
        defaultProntuarioFiltering("confidencial.equals=" + DEFAULT_CONFIDENCIAL, "confidencial.equals=" + UPDATED_CONFIDENCIAL);
    }

    @Test
    @Transactional
    void getAllProntuariosByConfidencialIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList where confidencial in
        defaultProntuarioFiltering(
            "confidencial.in=" + DEFAULT_CONFIDENCIAL + "," + UPDATED_CONFIDENCIAL,
            "confidencial.in=" + UPDATED_CONFIDENCIAL
        );
    }

    @Test
    @Transactional
    void getAllProntuariosByConfidencialIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        // Get all the prontuarioList where confidencial is not null
        defaultProntuarioFiltering("confidencial.specified=true", "confidencial.specified=false");
    }

    @Test
    @Transactional
    void getAllProntuariosByPacienteIsEqualToSomething() throws Exception {
        Paciente paciente;
        if (TestUtil.findAll(em, Paciente.class).isEmpty()) {
            prontuarioRepository.saveAndFlush(prontuario);
            paciente = PacienteResourceIT.createEntity();
        } else {
            paciente = TestUtil.findAll(em, Paciente.class).get(0);
        }
        em.persist(paciente);
        em.flush();
        prontuario.setPaciente(paciente);
        prontuarioRepository.saveAndFlush(prontuario);
        Long pacienteId = paciente.getId();
        // Get all the prontuarioList where paciente equals to pacienteId
        defaultProntuarioShouldBeFound("pacienteId.equals=" + pacienteId);

        // Get all the prontuarioList where paciente equals to (pacienteId + 1)
        defaultProntuarioShouldNotBeFound("pacienteId.equals=" + (pacienteId + 1));
    }

    @Test
    @Transactional
    void getAllProntuariosByProfissionalIsEqualToSomething() throws Exception {
        Profissional profissional;
        if (TestUtil.findAll(em, Profissional.class).isEmpty()) {
            prontuarioRepository.saveAndFlush(prontuario);
            profissional = ProfissionalResourceIT.createEntity();
        } else {
            profissional = TestUtil.findAll(em, Profissional.class).get(0);
        }
        em.persist(profissional);
        em.flush();
        prontuario.setProfissional(profissional);
        prontuarioRepository.saveAndFlush(prontuario);
        Long profissionalId = profissional.getId();
        // Get all the prontuarioList where profissional equals to profissionalId
        defaultProntuarioShouldBeFound("profissionalId.equals=" + profissionalId);

        // Get all the prontuarioList where profissional equals to (profissionalId + 1)
        defaultProntuarioShouldNotBeFound("profissionalId.equals=" + (profissionalId + 1));
    }

    @Test
    @Transactional
    void getAllProntuariosByAgendaIsEqualToSomething() throws Exception {
        Agenda agenda;
        if (TestUtil.findAll(em, Agenda.class).isEmpty()) {
            prontuarioRepository.saveAndFlush(prontuario);
            agenda = AgendaResourceIT.createEntity(em);
        } else {
            agenda = TestUtil.findAll(em, Agenda.class).get(0);
        }
        em.persist(agenda);
        em.flush();
        prontuario.setAgenda(agenda);
        prontuarioRepository.saveAndFlush(prontuario);
        Long agendaId = agenda.getId();
        // Get all the prontuarioList where agenda equals to agendaId
        defaultProntuarioShouldBeFound("agendaId.equals=" + agendaId);

        // Get all the prontuarioList where agenda equals to (agendaId + 1)
        defaultProntuarioShouldNotBeFound("agendaId.equals=" + (agendaId + 1));
    }

    @Test
    @Transactional
    void getAllProntuariosByEspecialidadeIsEqualToSomething() throws Exception {
        Especialidade especialidade;
        if (TestUtil.findAll(em, Especialidade.class).isEmpty()) {
            prontuarioRepository.saveAndFlush(prontuario);
            especialidade = EspecialidadeResourceIT.createEntity();
        } else {
            especialidade = TestUtil.findAll(em, Especialidade.class).get(0);
        }
        em.persist(especialidade);
        em.flush();
        prontuario.setEspecialidade(especialidade);
        prontuarioRepository.saveAndFlush(prontuario);
        Long especialidadeId = especialidade.getId();
        // Get all the prontuarioList where especialidade equals to especialidadeId
        defaultProntuarioShouldBeFound("especialidadeId.equals=" + especialidadeId);

        // Get all the prontuarioList where especialidade equals to (especialidadeId + 1)
        defaultProntuarioShouldNotBeFound("especialidadeId.equals=" + (especialidadeId + 1));
    }

    private void defaultProntuarioFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultProntuarioShouldBeFound(shouldBeFound);
        defaultProntuarioShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProntuarioShouldBeFound(String filter) throws Exception {
        restProntuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prontuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].dataAtendimento").value(hasItem(sameInstant(DEFAULT_DATA_ATENDIMENTO))))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(DEFAULT_CONTEUDO.toString())))
            .andExpect(jsonPath("$.[*].confidencial").value(hasItem(DEFAULT_CONFIDENCIAL.booleanValue())));

        // Check, that the count call also returns 1
        restProntuarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProntuarioShouldNotBeFound(String filter) throws Exception {
        restProntuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProntuarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProntuario() throws Exception {
        // Get the prontuario
        restProntuarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProntuario() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prontuario
        Prontuario updatedProntuario = prontuarioRepository.findById(prontuario.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProntuario are not directly saved in db
        em.detach(updatedProntuario);
        updatedProntuario
            .tipo(UPDATED_TIPO)
            .dataAtendimento(UPDATED_DATA_ATENDIMENTO)
            .titulo(UPDATED_TITULO)
            .conteudo(UPDATED_CONTEUDO)
            .confidencial(UPDATED_CONFIDENCIAL);
        ProntuarioDTO prontuarioDTO = prontuarioMapper.toDto(updatedProntuario);

        restProntuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prontuarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prontuarioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Prontuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProntuarioToMatchAllProperties(updatedProntuario);
    }

    @Test
    @Transactional
    void putNonExistingProntuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prontuario.setId(longCount.incrementAndGet());

        // Create the Prontuario
        ProntuarioDTO prontuarioDTO = prontuarioMapper.toDto(prontuario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProntuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prontuarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prontuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prontuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProntuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prontuario.setId(longCount.incrementAndGet());

        // Create the Prontuario
        ProntuarioDTO prontuarioDTO = prontuarioMapper.toDto(prontuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProntuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prontuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prontuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProntuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prontuario.setId(longCount.incrementAndGet());

        // Create the Prontuario
        ProntuarioDTO prontuarioDTO = prontuarioMapper.toDto(prontuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProntuarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prontuarioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prontuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProntuarioWithPatch() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prontuario using partial update
        Prontuario partialUpdatedProntuario = new Prontuario();
        partialUpdatedProntuario.setId(prontuario.getId());

        partialUpdatedProntuario.dataAtendimento(UPDATED_DATA_ATENDIMENTO).conteudo(UPDATED_CONTEUDO).confidencial(UPDATED_CONFIDENCIAL);

        restProntuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProntuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProntuario))
            )
            .andExpect(status().isOk());

        // Validate the Prontuario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProntuarioUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProntuario, prontuario),
            getPersistedProntuario(prontuario)
        );
    }

    @Test
    @Transactional
    void fullUpdateProntuarioWithPatch() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prontuario using partial update
        Prontuario partialUpdatedProntuario = new Prontuario();
        partialUpdatedProntuario.setId(prontuario.getId());

        partialUpdatedProntuario
            .tipo(UPDATED_TIPO)
            .dataAtendimento(UPDATED_DATA_ATENDIMENTO)
            .titulo(UPDATED_TITULO)
            .conteudo(UPDATED_CONTEUDO)
            .confidencial(UPDATED_CONFIDENCIAL);

        restProntuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProntuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProntuario))
            )
            .andExpect(status().isOk());

        // Validate the Prontuario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProntuarioUpdatableFieldsEquals(partialUpdatedProntuario, getPersistedProntuario(partialUpdatedProntuario));
    }

    @Test
    @Transactional
    void patchNonExistingProntuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prontuario.setId(longCount.incrementAndGet());

        // Create the Prontuario
        ProntuarioDTO prontuarioDTO = prontuarioMapper.toDto(prontuario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProntuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prontuarioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prontuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prontuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProntuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prontuario.setId(longCount.incrementAndGet());

        // Create the Prontuario
        ProntuarioDTO prontuarioDTO = prontuarioMapper.toDto(prontuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProntuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prontuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prontuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProntuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prontuario.setId(longCount.incrementAndGet());

        // Create the Prontuario
        ProntuarioDTO prontuarioDTO = prontuarioMapper.toDto(prontuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProntuarioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(prontuarioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prontuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProntuario() throws Exception {
        // Initialize the database
        insertedProntuario = prontuarioRepository.saveAndFlush(prontuario);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the prontuario
        restProntuarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, prontuario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return prontuarioRepository.count();
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

    protected Prontuario getPersistedProntuario(Prontuario prontuario) {
        return prontuarioRepository.findById(prontuario.getId()).orElseThrow();
    }

    protected void assertPersistedProntuarioToMatchAllProperties(Prontuario expectedProntuario) {
        assertProntuarioAllPropertiesEquals(expectedProntuario, getPersistedProntuario(expectedProntuario));
    }

    protected void assertPersistedProntuarioToMatchUpdatableProperties(Prontuario expectedProntuario) {
        assertProntuarioAllUpdatablePropertiesEquals(expectedProntuario, getPersistedProntuario(expectedProntuario));
    }
}
