package com.clinica.multiterapias.web.rest;

import com.clinica.multiterapias.repository.ProntuarioRepository;
import com.clinica.multiterapias.service.ProntuarioQueryService;
import com.clinica.multiterapias.service.ProntuarioService;
import com.clinica.multiterapias.service.criteria.ProntuarioCriteria;
import com.clinica.multiterapias.service.dto.ProntuarioDTO;
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
 * REST controller for managing {@link com.clinica.multiterapias.domain.Prontuario}.
 */
@RestController
@RequestMapping("/api/prontuarios")
public class ProntuarioResource {

    private static final Logger LOG = LoggerFactory.getLogger(ProntuarioResource.class);

    private static final String ENTITY_NAME = "prontuario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProntuarioService prontuarioService;

    private final ProntuarioRepository prontuarioRepository;

    private final ProntuarioQueryService prontuarioQueryService;

    public ProntuarioResource(
        ProntuarioService prontuarioService,
        ProntuarioRepository prontuarioRepository,
        ProntuarioQueryService prontuarioQueryService
    ) {
        this.prontuarioService = prontuarioService;
        this.prontuarioRepository = prontuarioRepository;
        this.prontuarioQueryService = prontuarioQueryService;
    }

    /**
     * {@code POST  /prontuarios} : Create a new prontuario.
     *
     * @param prontuarioDTO the prontuarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prontuarioDTO, or with status {@code 400 (Bad Request)} if the prontuario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProntuarioDTO> createProntuario(@Valid @RequestBody ProntuarioDTO prontuarioDTO) throws URISyntaxException {
        LOG.debug("REST request to save Prontuario : {}", prontuarioDTO);
        if (prontuarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new prontuario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        prontuarioDTO = prontuarioService.save(prontuarioDTO);
        return ResponseEntity.created(new URI("/api/prontuarios/" + prontuarioDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, prontuarioDTO.getId().toString()))
            .body(prontuarioDTO);
    }

    /**
     * {@code PUT  /prontuarios/:id} : Updates an existing prontuario.
     *
     * @param id the id of the prontuarioDTO to save.
     * @param prontuarioDTO the prontuarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prontuarioDTO,
     * or with status {@code 400 (Bad Request)} if the prontuarioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prontuarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProntuarioDTO> updateProntuario(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProntuarioDTO prontuarioDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Prontuario : {}, {}", id, prontuarioDTO);
        if (prontuarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prontuarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prontuarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        prontuarioDTO = prontuarioService.update(prontuarioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prontuarioDTO.getId().toString()))
            .body(prontuarioDTO);
    }

    /**
     * {@code PATCH  /prontuarios/:id} : Partial updates given fields of an existing prontuario, field will ignore if it is null
     *
     * @param id the id of the prontuarioDTO to save.
     * @param prontuarioDTO the prontuarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prontuarioDTO,
     * or with status {@code 400 (Bad Request)} if the prontuarioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the prontuarioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the prontuarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProntuarioDTO> partialUpdateProntuario(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProntuarioDTO prontuarioDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Prontuario partially : {}, {}", id, prontuarioDTO);
        if (prontuarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prontuarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prontuarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProntuarioDTO> result = prontuarioService.partialUpdate(prontuarioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prontuarioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /prontuarios} : get all the prontuarios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prontuarios in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProntuarioDTO>> getAllProntuarios(
        ProntuarioCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Prontuarios by criteria: {}", criteria);

        Page<ProntuarioDTO> page = prontuarioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prontuarios/count} : count all the prontuarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countProntuarios(ProntuarioCriteria criteria) {
        LOG.debug("REST request to count Prontuarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(prontuarioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /prontuarios/:id} : get the "id" prontuario.
     *
     * @param id the id of the prontuarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prontuarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProntuarioDTO> getProntuario(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Prontuario : {}", id);
        Optional<ProntuarioDTO> prontuarioDTO = prontuarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prontuarioDTO);
    }

    /**
     * {@code DELETE  /prontuarios/:id} : delete the "id" prontuario.
     *
     * @param id the id of the prontuarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProntuario(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Prontuario : {}", id);
        prontuarioService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
