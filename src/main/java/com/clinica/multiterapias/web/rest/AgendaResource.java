package com.clinica.multiterapias.web.rest;

import com.clinica.multiterapias.repository.AgendaRepository;
import com.clinica.multiterapias.service.AgendaQueryService;
import com.clinica.multiterapias.service.AgendaService;
import com.clinica.multiterapias.service.criteria.AgendaCriteria;
import com.clinica.multiterapias.service.dto.AgendaDTO;
import com.clinica.multiterapias.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.clinica.multiterapias.domain.Agenda}.
 */
@RestController
@RequestMapping("/api/agenda")
public class AgendaResource {

    private static final Logger LOG = LoggerFactory.getLogger(AgendaResource.class);

    private static final String ENTITY_NAME = "agenda";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgendaService agendaService;

    private final AgendaRepository agendaRepository;

    private final AgendaQueryService agendaQueryService;

    public AgendaResource(AgendaService agendaService, AgendaRepository agendaRepository, AgendaQueryService agendaQueryService) {
        this.agendaService = agendaService;
        this.agendaRepository = agendaRepository;
        this.agendaQueryService = agendaQueryService;
    }

    /**
     * {@code POST  /agenda} : Create a new agenda.
     *
     * @param agendaDTO the agendaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agendaDTO, or with status {@code 400 (Bad Request)} if the agenda has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AgendaDTO> createAgenda(@Valid @RequestBody AgendaDTO agendaDTO) throws URISyntaxException {
        LOG.debug("REST request to save Agenda : {}", agendaDTO);
        if (agendaDTO.getId() != null) {
            throw new BadRequestAlertException("A new agenda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        agendaDTO = agendaService.save(agendaDTO);
        return ResponseEntity.created(new URI("/api/agenda/" + agendaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, agendaDTO.getId().toString()))
            .body(agendaDTO);
    }

    /**
     * {@code PUT  /agenda/:id} : Updates an existing agenda.
     *
     * @param id the id of the agendaDTO to save.
     * @param agendaDTO the agendaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendaDTO,
     * or with status {@code 400 (Bad Request)} if the agendaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agendaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AgendaDTO> updateAgenda(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AgendaDTO agendaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Agenda : {}, {}", id, agendaDTO);
        if (agendaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agendaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agendaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        agendaDTO = agendaService.update(agendaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agendaDTO.getId().toString()))
            .body(agendaDTO);
    }

    /**
     * {@code PATCH  /agenda/:id} : Partial updates given fields of an existing agenda, field will ignore if it is null
     *
     * @param id the id of the agendaDTO to save.
     * @param agendaDTO the agendaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendaDTO,
     * or with status {@code 400 (Bad Request)} if the agendaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the agendaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the agendaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AgendaDTO> partialUpdateAgenda(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AgendaDTO agendaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Agenda partially : {}, {}", id, agendaDTO);
        if (agendaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agendaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agendaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AgendaDTO> result = agendaService.partialUpdate(agendaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agendaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /agenda} : get all the agenda.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agenda in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AgendaDTO>> getAllAgenda(
        AgendaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Agenda by criteria: {}", criteria);

        Page<AgendaDTO> page = agendaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /agenda/count} : count all the agenda.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAgenda(AgendaCriteria criteria) {
        LOG.debug("REST request to count Agenda by criteria: {}", criteria);
        return ResponseEntity.ok().body(agendaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /agenda/:id} : get the "id" agenda.
     *
     * @param id the id of the agendaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agendaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AgendaDTO> getAgenda(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Agenda : {}", id);
        Optional<AgendaDTO> agendaDTO = agendaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agendaDTO);
    }

    /**
     * {@code DELETE  /agenda/:id} : delete the "id" agenda.
     *
     * @param id the id of the agendaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgenda(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Agenda : {}", id);
        agendaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
