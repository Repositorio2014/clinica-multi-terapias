package com.clinica.multiterapias.web.rest;

import com.clinica.multiterapias.repository.ProfissionalRepository;
import com.clinica.multiterapias.service.ProfissionalService;
import com.clinica.multiterapias.service.dto.ProfissionalDTO;
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
 * REST controller for managing {@link com.clinica.multiterapias.domain.Profissional}.
 */
@RestController
@RequestMapping("/api/profissionals")
public class ProfissionalResource {

    private static final Logger LOG = LoggerFactory.getLogger(ProfissionalResource.class);

    private static final String ENTITY_NAME = "profissional";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfissionalService profissionalService;

    private final ProfissionalRepository profissionalRepository;

    public ProfissionalResource(ProfissionalService profissionalService, ProfissionalRepository profissionalRepository) {
        this.profissionalService = profissionalService;
        this.profissionalRepository = profissionalRepository;
    }

    /**
     * {@code POST  /profissionals} : Create a new profissional.
     *
     * @param profissionalDTO the profissionalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profissionalDTO, or with status {@code 400 (Bad Request)} if the profissional has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProfissionalDTO> createProfissional(@Valid @RequestBody ProfissionalDTO profissionalDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save Profissional : {}", profissionalDTO);
        if (profissionalDTO.getId() != null) {
            throw new BadRequestAlertException("A new profissional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        profissionalDTO = profissionalService.save(profissionalDTO);
        return ResponseEntity.created(new URI("/api/profissionals/" + profissionalDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, profissionalDTO.getId().toString()))
            .body(profissionalDTO);
    }

    /**
     * {@code PUT  /profissionals/:id} : Updates an existing profissional.
     *
     * @param id the id of the profissionalDTO to save.
     * @param profissionalDTO the profissionalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profissionalDTO,
     * or with status {@code 400 (Bad Request)} if the profissionalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profissionalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalDTO> updateProfissional(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProfissionalDTO profissionalDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Profissional : {}, {}", id, profissionalDTO);
        if (profissionalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profissionalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profissionalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        profissionalDTO = profissionalService.update(profissionalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profissionalDTO.getId().toString()))
            .body(profissionalDTO);
    }

    /**
     * {@code PATCH  /profissionals/:id} : Partial updates given fields of an existing profissional, field will ignore if it is null
     *
     * @param id the id of the profissionalDTO to save.
     * @param profissionalDTO the profissionalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profissionalDTO,
     * or with status {@code 400 (Bad Request)} if the profissionalDTO is not valid,
     * or with status {@code 404 (Not Found)} if the profissionalDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the profissionalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProfissionalDTO> partialUpdateProfissional(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProfissionalDTO profissionalDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Profissional partially : {}, {}", id, profissionalDTO);
        if (profissionalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profissionalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profissionalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProfissionalDTO> result = profissionalService.partialUpdate(profissionalDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profissionalDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /profissionals} : get all the profissionals.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profissionals in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProfissionalDTO>> getAllProfissionals(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Profissionals");
        Page<ProfissionalDTO> page;
        if (eagerload) {
            page = profissionalService.findAllWithEagerRelationships(pageable);
        } else {
            page = profissionalService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /profissionals/:id} : get the "id" profissional.
     *
     * @param id the id of the profissionalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profissionalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalDTO> getProfissional(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Profissional : {}", id);
        Optional<ProfissionalDTO> profissionalDTO = profissionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profissionalDTO);
    }

    /**
     * {@code DELETE  /profissionals/:id} : delete the "id" profissional.
     *
     * @param id the id of the profissionalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfissional(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Profissional : {}", id);
        profissionalService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
