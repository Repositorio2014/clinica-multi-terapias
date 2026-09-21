package com.clinica.multiterapias.web.rest;

import static com.clinica.multiterapias.domain.AgendaAsserts.*;
import static com.clinica.multiterapias.web.rest.TestUtil.createUpdateProxyForBean;
import static com.clinica.multiterapias.web.rest.TestUtil.sameInstant;
import static com.clinica.multiterapias.web.rest.TestUtil.sameNumber;
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
import com.clinica.multiterapias.domain.Sala;
import com.clinica.multiterapias.domain.enumeration.StatusAgendamento;
import com.clinica.multiterapias.repository.AgendaRepository;
import com.clinica.multiterapias.service.AgendaService;
import com.clinica.multiterapias.service.dto.AgendaDTO;
import com.clinica.multiterapias.service.mapper.AgendaMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link AgendaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AgendaResourceIT {

    private static final ZonedDateTime DEFAULT_DATA_HORA_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_HORA_INICIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_HORA_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DATA_HORA_FIM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_HORA_FIM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_HORA_FIM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final StatusAgendamento DEFAULT_STATUS = StatusAgendamento.AGENDADO;
    private static final StatusAgendamento UPDATED_STATUS = StatusAgendamento.CONFIRMADO;

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR_COBRADO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_COBRADO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_COBRADO = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/agenda";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AgendaRepository agendaRepository;

    @Mock
    private AgendaRepository agendaRepositoryMock;

    @Autowired
    private AgendaMapper agendaMapper;

    @Mock
    private AgendaService agendaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgendaMockMvc;

    private Agenda agenda;

    private Agenda insertedAgenda;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agenda createEntity(EntityManager em) {
        Agenda agenda = new Agenda()
            .dataHoraInicio(DEFAULT_DATA_HORA_INICIO)
            .dataHoraFim(DEFAULT_DATA_HORA_FIM)
            .status(DEFAULT_STATUS)
            .observacoes(DEFAULT_OBSERVACOES)
            .valorCobrado(DEFAULT_VALOR_COBRADO);
        // Add required entity
        Paciente paciente;
        if (TestUtil.findAll(em, Paciente.class).isEmpty()) {
            paciente = PacienteResourceIT.createEntity();
            em.persist(paciente);
            em.flush();
        } else {
            paciente = TestUtil.findAll(em, Paciente.class).get(0);
        }
        agenda.setPaciente(paciente);
        // Add required entity
        Profissional profissional;
        if (TestUtil.findAll(em, Profissional.class).isEmpty()) {
            profissional = ProfissionalResourceIT.createEntity();
            em.persist(profissional);
            em.flush();
        } else {
            profissional = TestUtil.findAll(em, Profissional.class).get(0);
        }
        agenda.setProfissional(profissional);
        // Add required entity
        Especialidade especialidade;
        if (TestUtil.findAll(em, Especialidade.class).isEmpty()) {
            especialidade = EspecialidadeResourceIT.createEntity();
            em.persist(especialidade);
            em.flush();
        } else {
            especialidade = TestUtil.findAll(em, Especialidade.class).get(0);
        }
        agenda.setEspecialidade(especialidade);
        return agenda;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agenda createUpdatedEntity(EntityManager em) {
        Agenda updatedAgenda = new Agenda()
            .dataHoraInicio(UPDATED_DATA_HORA_INICIO)
            .dataHoraFim(UPDATED_DATA_HORA_FIM)
            .status(UPDATED_STATUS)
            .observacoes(UPDATED_OBSERVACOES)
            .valorCobrado(UPDATED_VALOR_COBRADO);
        // Add required entity
        Paciente paciente;
        if (TestUtil.findAll(em, Paciente.class).isEmpty()) {
            paciente = PacienteResourceIT.createUpdatedEntity();
            em.persist(paciente);
            em.flush();
        } else {
            paciente = TestUtil.findAll(em, Paciente.class).get(0);
        }
        updatedAgenda.setPaciente(paciente);
        // Add required entity
        Profissional profissional;
        if (TestUtil.findAll(em, Profissional.class).isEmpty()) {
            profissional = ProfissionalResourceIT.createUpdatedEntity();
            em.persist(profissional);
            em.flush();
        } else {
            profissional = TestUtil.findAll(em, Profissional.class).get(0);
        }
        updatedAgenda.setProfissional(profissional);
        // Add required entity
        Especialidade especialidade;
        if (TestUtil.findAll(em, Especialidade.class).isEmpty()) {
            especialidade = EspecialidadeResourceIT.createUpdatedEntity();
            em.persist(especialidade);
            em.flush();
        } else {
            especialidade = TestUtil.findAll(em, Especialidade.class).get(0);
        }
        updatedAgenda.setEspecialidade(especialidade);
        return updatedAgenda;
    }

    @BeforeEach
    public void initTest() {
        agenda = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAgenda != null) {
            agendaRepository.delete(insertedAgenda);
            insertedAgenda = null;
        }
    }

    @Test
    @Transactional
    void createAgenda() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Agenda
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);
        var returnedAgendaDTO = om.readValue(
            restAgendaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AgendaDTO.class
        );

        // Validate the Agenda in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAgenda = agendaMapper.toEntity(returnedAgendaDTO);
        assertAgendaUpdatableFieldsEquals(returnedAgenda, getPersistedAgenda(returnedAgenda));

        insertedAgenda = returnedAgenda;
    }

    @Test
    @Transactional
    void createAgendaWithExistingId() throws Exception {
        // Create the Agenda with an existing ID
        agenda.setId(1L);
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agenda in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataHoraInicioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        agenda.setDataHoraInicio(null);

        // Create the Agenda, which fails.
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);

        restAgendaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataHoraFimIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        agenda.setDataHoraFim(null);

        // Create the Agenda, which fails.
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);

        restAgendaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        agenda.setStatus(null);

        // Create the Agenda, which fails.
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);

        restAgendaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAgenda() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList
        restAgendaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataHoraInicio").value(hasItem(sameInstant(DEFAULT_DATA_HORA_INICIO))))
            .andExpect(jsonPath("$.[*].dataHoraFim").value(hasItem(sameInstant(DEFAULT_DATA_HORA_FIM))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES)))
            .andExpect(jsonPath("$.[*].valorCobrado").value(hasItem(sameNumber(DEFAULT_VALOR_COBRADO))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAgendaWithEagerRelationshipsIsEnabled() throws Exception {
        when(agendaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAgendaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(agendaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAgendaWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(agendaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAgendaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(agendaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAgenda() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get the agenda
        restAgendaMockMvc
            .perform(get(ENTITY_API_URL_ID, agenda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agenda.getId().intValue()))
            .andExpect(jsonPath("$.dataHoraInicio").value(sameInstant(DEFAULT_DATA_HORA_INICIO)))
            .andExpect(jsonPath("$.dataHoraFim").value(sameInstant(DEFAULT_DATA_HORA_FIM)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES))
            .andExpect(jsonPath("$.valorCobrado").value(sameNumber(DEFAULT_VALOR_COBRADO)));
    }

    @Test
    @Transactional
    void getAgendaByIdFiltering() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        Long id = agenda.getId();

        defaultAgendaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAgendaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAgendaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAgendaByDataHoraInicioIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where dataHoraInicio equals to
        defaultAgendaFiltering("dataHoraInicio.equals=" + DEFAULT_DATA_HORA_INICIO, "dataHoraInicio.equals=" + UPDATED_DATA_HORA_INICIO);
    }

    @Test
    @Transactional
    void getAllAgendaByDataHoraInicioIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where dataHoraInicio in
        defaultAgendaFiltering(
            "dataHoraInicio.in=" + DEFAULT_DATA_HORA_INICIO + "," + UPDATED_DATA_HORA_INICIO,
            "dataHoraInicio.in=" + UPDATED_DATA_HORA_INICIO
        );
    }

    @Test
    @Transactional
    void getAllAgendaByDataHoraInicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where dataHoraInicio is not null
        defaultAgendaFiltering("dataHoraInicio.specified=true", "dataHoraInicio.specified=false");
    }

    @Test
    @Transactional
    void getAllAgendaByDataHoraInicioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where dataHoraInicio is greater than or equal to
        defaultAgendaFiltering(
            "dataHoraInicio.greaterThanOrEqual=" + DEFAULT_DATA_HORA_INICIO,
            "dataHoraInicio.greaterThanOrEqual=" + UPDATED_DATA_HORA_INICIO
        );
    }

    @Test
    @Transactional
    void getAllAgendaByDataHoraInicioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where dataHoraInicio is less than or equal to
        defaultAgendaFiltering(
            "dataHoraInicio.lessThanOrEqual=" + DEFAULT_DATA_HORA_INICIO,
            "dataHoraInicio.lessThanOrEqual=" + SMALLER_DATA_HORA_INICIO
        );
    }

    @Test
    @Transactional
    void getAllAgendaByDataHoraInicioIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where dataHoraInicio is less than
        defaultAgendaFiltering(
            "dataHoraInicio.lessThan=" + UPDATED_DATA_HORA_INICIO,
            "dataHoraInicio.lessThan=" + DEFAULT_DATA_HORA_INICIO
        );
    }

    @Test
    @Transactional
    void getAllAgendaByDataHoraInicioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where dataHoraInicio is greater than
        defaultAgendaFiltering(
            "dataHoraInicio.greaterThan=" + SMALLER_DATA_HORA_INICIO,
            "dataHoraInicio.greaterThan=" + DEFAULT_DATA_HORA_INICIO
        );
    }

    @Test
    @Transactional
    void getAllAgendaByDataHoraFimIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where dataHoraFim equals to
        defaultAgendaFiltering("dataHoraFim.equals=" + DEFAULT_DATA_HORA_FIM, "dataHoraFim.equals=" + UPDATED_DATA_HORA_FIM);
    }

    @Test
    @Transactional
    void getAllAgendaByDataHoraFimIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where dataHoraFim in
        defaultAgendaFiltering(
            "dataHoraFim.in=" + DEFAULT_DATA_HORA_FIM + "," + UPDATED_DATA_HORA_FIM,
            "dataHoraFim.in=" + UPDATED_DATA_HORA_FIM
        );
    }

    @Test
    @Transactional
    void getAllAgendaByDataHoraFimIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where dataHoraFim is not null
        defaultAgendaFiltering("dataHoraFim.specified=true", "dataHoraFim.specified=false");
    }

    @Test
    @Transactional
    void getAllAgendaByDataHoraFimIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where dataHoraFim is greater than or equal to
        defaultAgendaFiltering(
            "dataHoraFim.greaterThanOrEqual=" + DEFAULT_DATA_HORA_FIM,
            "dataHoraFim.greaterThanOrEqual=" + UPDATED_DATA_HORA_FIM
        );
    }

    @Test
    @Transactional
    void getAllAgendaByDataHoraFimIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where dataHoraFim is less than or equal to
        defaultAgendaFiltering(
            "dataHoraFim.lessThanOrEqual=" + DEFAULT_DATA_HORA_FIM,
            "dataHoraFim.lessThanOrEqual=" + SMALLER_DATA_HORA_FIM
        );
    }

    @Test
    @Transactional
    void getAllAgendaByDataHoraFimIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where dataHoraFim is less than
        defaultAgendaFiltering("dataHoraFim.lessThan=" + UPDATED_DATA_HORA_FIM, "dataHoraFim.lessThan=" + DEFAULT_DATA_HORA_FIM);
    }

    @Test
    @Transactional
    void getAllAgendaByDataHoraFimIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where dataHoraFim is greater than
        defaultAgendaFiltering("dataHoraFim.greaterThan=" + SMALLER_DATA_HORA_FIM, "dataHoraFim.greaterThan=" + DEFAULT_DATA_HORA_FIM);
    }

    @Test
    @Transactional
    void getAllAgendaByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where status equals to
        defaultAgendaFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAgendaByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where status in
        defaultAgendaFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAgendaByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where status is not null
        defaultAgendaFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllAgendaByObservacoesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where observacoes equals to
        defaultAgendaFiltering("observacoes.equals=" + DEFAULT_OBSERVACOES, "observacoes.equals=" + UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void getAllAgendaByObservacoesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where observacoes in
        defaultAgendaFiltering(
            "observacoes.in=" + DEFAULT_OBSERVACOES + "," + UPDATED_OBSERVACOES,
            "observacoes.in=" + UPDATED_OBSERVACOES
        );
    }

    @Test
    @Transactional
    void getAllAgendaByObservacoesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where observacoes is not null
        defaultAgendaFiltering("observacoes.specified=true", "observacoes.specified=false");
    }

    @Test
    @Transactional
    void getAllAgendaByObservacoesContainsSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where observacoes contains
        defaultAgendaFiltering("observacoes.contains=" + DEFAULT_OBSERVACOES, "observacoes.contains=" + UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void getAllAgendaByObservacoesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where observacoes does not contain
        defaultAgendaFiltering("observacoes.doesNotContain=" + UPDATED_OBSERVACOES, "observacoes.doesNotContain=" + DEFAULT_OBSERVACOES);
    }

    @Test
    @Transactional
    void getAllAgendaByValorCobradoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where valorCobrado equals to
        defaultAgendaFiltering("valorCobrado.equals=" + DEFAULT_VALOR_COBRADO, "valorCobrado.equals=" + UPDATED_VALOR_COBRADO);
    }

    @Test
    @Transactional
    void getAllAgendaByValorCobradoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where valorCobrado in
        defaultAgendaFiltering(
            "valorCobrado.in=" + DEFAULT_VALOR_COBRADO + "," + UPDATED_VALOR_COBRADO,
            "valorCobrado.in=" + UPDATED_VALOR_COBRADO
        );
    }

    @Test
    @Transactional
    void getAllAgendaByValorCobradoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where valorCobrado is not null
        defaultAgendaFiltering("valorCobrado.specified=true", "valorCobrado.specified=false");
    }

    @Test
    @Transactional
    void getAllAgendaByValorCobradoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where valorCobrado is greater than or equal to
        defaultAgendaFiltering(
            "valorCobrado.greaterThanOrEqual=" + DEFAULT_VALOR_COBRADO,
            "valorCobrado.greaterThanOrEqual=" + UPDATED_VALOR_COBRADO
        );
    }

    @Test
    @Transactional
    void getAllAgendaByValorCobradoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where valorCobrado is less than or equal to
        defaultAgendaFiltering(
            "valorCobrado.lessThanOrEqual=" + DEFAULT_VALOR_COBRADO,
            "valorCobrado.lessThanOrEqual=" + SMALLER_VALOR_COBRADO
        );
    }

    @Test
    @Transactional
    void getAllAgendaByValorCobradoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where valorCobrado is less than
        defaultAgendaFiltering("valorCobrado.lessThan=" + UPDATED_VALOR_COBRADO, "valorCobrado.lessThan=" + DEFAULT_VALOR_COBRADO);
    }

    @Test
    @Transactional
    void getAllAgendaByValorCobradoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList where valorCobrado is greater than
        defaultAgendaFiltering("valorCobrado.greaterThan=" + SMALLER_VALOR_COBRADO, "valorCobrado.greaterThan=" + DEFAULT_VALOR_COBRADO);
    }

    @Test
    @Transactional
    void getAllAgendaByPacienteIsEqualToSomething() throws Exception {
        Paciente paciente;
        if (TestUtil.findAll(em, Paciente.class).isEmpty()) {
            agendaRepository.saveAndFlush(agenda);
            paciente = PacienteResourceIT.createEntity();
        } else {
            paciente = TestUtil.findAll(em, Paciente.class).get(0);
        }
        em.persist(paciente);
        em.flush();
        agenda.setPaciente(paciente);
        agendaRepository.saveAndFlush(agenda);
        Long pacienteId = paciente.getId();
        // Get all the agendaList where paciente equals to pacienteId
        defaultAgendaShouldBeFound("pacienteId.equals=" + pacienteId);

        // Get all the agendaList where paciente equals to (pacienteId + 1)
        defaultAgendaShouldNotBeFound("pacienteId.equals=" + (pacienteId + 1));
    }

    @Test
    @Transactional
    void getAllAgendaByProfissionalIsEqualToSomething() throws Exception {
        Profissional profissional;
        if (TestUtil.findAll(em, Profissional.class).isEmpty()) {
            agendaRepository.saveAndFlush(agenda);
            profissional = ProfissionalResourceIT.createEntity();
        } else {
            profissional = TestUtil.findAll(em, Profissional.class).get(0);
        }
        em.persist(profissional);
        em.flush();
        agenda.setProfissional(profissional);
        agendaRepository.saveAndFlush(agenda);
        Long profissionalId = profissional.getId();
        // Get all the agendaList where profissional equals to profissionalId
        defaultAgendaShouldBeFound("profissionalId.equals=" + profissionalId);

        // Get all the agendaList where profissional equals to (profissionalId + 1)
        defaultAgendaShouldNotBeFound("profissionalId.equals=" + (profissionalId + 1));
    }

    @Test
    @Transactional
    void getAllAgendaBySalaIsEqualToSomething() throws Exception {
        Sala sala;
        if (TestUtil.findAll(em, Sala.class).isEmpty()) {
            agendaRepository.saveAndFlush(agenda);
            sala = SalaResourceIT.createEntity();
        } else {
            sala = TestUtil.findAll(em, Sala.class).get(0);
        }
        em.persist(sala);
        em.flush();
        agenda.setSala(sala);
        agendaRepository.saveAndFlush(agenda);
        Long salaId = sala.getId();
        // Get all the agendaList where sala equals to salaId
        defaultAgendaShouldBeFound("salaId.equals=" + salaId);

        // Get all the agendaList where sala equals to (salaId + 1)
        defaultAgendaShouldNotBeFound("salaId.equals=" + (salaId + 1));
    }

    @Test
    @Transactional
    void getAllAgendaByEspecialidadeIsEqualToSomething() throws Exception {
        Especialidade especialidade;
        if (TestUtil.findAll(em, Especialidade.class).isEmpty()) {
            agendaRepository.saveAndFlush(agenda);
            especialidade = EspecialidadeResourceIT.createEntity();
        } else {
            especialidade = TestUtil.findAll(em, Especialidade.class).get(0);
        }
        em.persist(especialidade);
        em.flush();
        agenda.setEspecialidade(especialidade);
        agendaRepository.saveAndFlush(agenda);
        Long especialidadeId = especialidade.getId();
        // Get all the agendaList where especialidade equals to especialidadeId
        defaultAgendaShouldBeFound("especialidadeId.equals=" + especialidadeId);

        // Get all the agendaList where especialidade equals to (especialidadeId + 1)
        defaultAgendaShouldNotBeFound("especialidadeId.equals=" + (especialidadeId + 1));
    }

    private void defaultAgendaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAgendaShouldBeFound(shouldBeFound);
        defaultAgendaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAgendaShouldBeFound(String filter) throws Exception {
        restAgendaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataHoraInicio").value(hasItem(sameInstant(DEFAULT_DATA_HORA_INICIO))))
            .andExpect(jsonPath("$.[*].dataHoraFim").value(hasItem(sameInstant(DEFAULT_DATA_HORA_FIM))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES)))
            .andExpect(jsonPath("$.[*].valorCobrado").value(hasItem(sameNumber(DEFAULT_VALOR_COBRADO))));

        // Check, that the count call also returns 1
        restAgendaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAgendaShouldNotBeFound(String filter) throws Exception {
        restAgendaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAgendaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAgenda() throws Exception {
        // Get the agenda
        restAgendaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAgenda() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agenda
        Agenda updatedAgenda = agendaRepository.findById(agenda.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAgenda are not directly saved in db
        em.detach(updatedAgenda);
        updatedAgenda
            .dataHoraInicio(UPDATED_DATA_HORA_INICIO)
            .dataHoraFim(UPDATED_DATA_HORA_FIM)
            .status(UPDATED_STATUS)
            .observacoes(UPDATED_OBSERVACOES)
            .valorCobrado(UPDATED_VALOR_COBRADO);
        AgendaDTO agendaDTO = agendaMapper.toDto(updatedAgenda);

        restAgendaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agendaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Agenda in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAgendaToMatchAllProperties(updatedAgenda);
    }

    @Test
    @Transactional
    void putNonExistingAgenda() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agenda.setId(longCount.incrementAndGet());

        // Create the Agenda
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agendaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agenda in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgenda() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agenda.setId(longCount.incrementAndGet());

        // Create the Agenda
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agendaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agenda in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgenda() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agenda.setId(longCount.incrementAndGet());

        // Create the Agenda
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agenda in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgendaWithPatch() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agenda using partial update
        Agenda partialUpdatedAgenda = new Agenda();
        partialUpdatedAgenda.setId(agenda.getId());

        partialUpdatedAgenda.observacoes(UPDATED_OBSERVACOES);

        restAgendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgenda.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgenda))
            )
            .andExpect(status().isOk());

        // Validate the Agenda in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgendaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAgenda, agenda), getPersistedAgenda(agenda));
    }

    @Test
    @Transactional
    void fullUpdateAgendaWithPatch() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agenda using partial update
        Agenda partialUpdatedAgenda = new Agenda();
        partialUpdatedAgenda.setId(agenda.getId());

        partialUpdatedAgenda
            .dataHoraInicio(UPDATED_DATA_HORA_INICIO)
            .dataHoraFim(UPDATED_DATA_HORA_FIM)
            .status(UPDATED_STATUS)
            .observacoes(UPDATED_OBSERVACOES)
            .valorCobrado(UPDATED_VALOR_COBRADO);

        restAgendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgenda.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgenda))
            )
            .andExpect(status().isOk());

        // Validate the Agenda in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgendaUpdatableFieldsEquals(partialUpdatedAgenda, getPersistedAgenda(partialUpdatedAgenda));
    }

    @Test
    @Transactional
    void patchNonExistingAgenda() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agenda.setId(longCount.incrementAndGet());

        // Create the Agenda
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, agendaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agendaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agenda in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgenda() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agenda.setId(longCount.incrementAndGet());

        // Create the Agenda
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agendaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agenda in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgenda() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agenda.setId(longCount.incrementAndGet());

        // Create the Agenda
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(agendaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agenda in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgenda() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the agenda
        restAgendaMockMvc
            .perform(delete(ENTITY_API_URL_ID, agenda.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return agendaRepository.count();
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

    protected Agenda getPersistedAgenda(Agenda agenda) {
        return agendaRepository.findById(agenda.getId()).orElseThrow();
    }

    protected void assertPersistedAgendaToMatchAllProperties(Agenda expectedAgenda) {
        assertAgendaAllPropertiesEquals(expectedAgenda, getPersistedAgenda(expectedAgenda));
    }

    protected void assertPersistedAgendaToMatchUpdatableProperties(Agenda expectedAgenda) {
        assertAgendaAllUpdatablePropertiesEquals(expectedAgenda, getPersistedAgenda(expectedAgenda));
    }
}
