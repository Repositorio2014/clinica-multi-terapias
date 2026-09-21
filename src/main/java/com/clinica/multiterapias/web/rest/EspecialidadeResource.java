package com.clinica.multiterapias.web.rest;

import com.clinica.multiterapias.repository.EspecialidadeRepository;
import com.clinica.multiterapias.service.EspecialidadeService;
import com.clinica.multiterapias.service.dto.EspecialidadeDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.clinica.multiterapias.domain.Especialidade}.
 */
@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadeResource {

    private static final Logger LOG = LoggerFactory.getLogger(EspecialidadeResource.class);

    private static final String ENTITY_NAME = "especialidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EspecialidadeService especialidadeService;

    private final EspecialidadeRepository especialidadeRepository;

    public EspecialidadeResource(EspecialidadeService especialidadeService, EspecialidadeRepository especialidadeRepository) {
        this.especialidadeService = especialidadeService;
        this.especialidadeRepository = especialidadeRepository;
    }

    /**
     * {@code POST  /especialidades} : Create a new especialidade.
     *
     * @param especialidadeDTO the especialidadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new especialidadeDTO, or with status {@code 400 (Bad Request)} if the especialidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EspecialidadeDTO> createEspecialidade(@Valid @RequestBody EspecialidadeDTO especialidadeDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save Especialidade : {}", especialidadeDTO);
        if (especialidadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new especialidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        especialidadeDTO = especialidadeService.save(especialidadeDTO);
        return ResponseEntity.created(new URI("/api/especialidades/" + especialidadeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, especialidadeDTO.getId().toString()))
            .body(especialidadeDTO);
    }

    /**
     * {@code PUT  /especialidades/:id} : Updates an existing especialidade.
     *
     * @param id the id of the especialidadeDTO to save.
     * @param especialidadeDTO the especialidadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated especialidadeDTO,
     * or with status {@code 400 (Bad Request)} if the especialidadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the especialidadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadeDTO> updateEspecialidade(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EspecialidadeDTO especialidadeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Especialidade : {}, {}", id, especialidadeDTO);
        if (especialidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, especialidadeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!especialidadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        especialidadeDTO = especialidadeService.update(especialidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, especialidadeDTO.getId().toString()))
            .body(especialidadeDTO);
    }

    /**
     * {@code PATCH  /especialidades/:id} : Partial updates given fields of an existing especialidade, field will ignore if it is null
     *
     * @param id the id of the especialidadeDTO to save.
     * @param especialidadeDTO the especialidadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated especialidadeDTO,
     * or with status {@code 400 (Bad Request)} if the especialidadeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the especialidadeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the especialidadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EspecialidadeDTO> partialUpdateEspecialidade(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EspecialidadeDTO especialidadeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Especialidade partially : {}, {}", id, especialidadeDTO);
        if (especialidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, especialidadeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!especialidadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EspecialidadeDTO> result = especialidadeService.partialUpdate(especialidadeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, especialidadeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /especialidades} : get all the especialidades.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of especialidades in body.
     */
    @GetMapping("")
    public List<EspecialidadeDTO> getAllEspecialidades() {
        LOG.debug("REST request to get all Especialidades");
        return especialidadeService.findAll();
    }

    /**
     * {@code GET  /especialidades/:id} : get the "id" especialidade.
     *
     * @param id the id of the especialidadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the especialidadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeDTO> getEspecialidade(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Especialidade : {}", id);
        Optional<EspecialidadeDTO> especialidadeDTO = especialidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(especialidadeDTO);
    }

    /**
     * {@code DELETE  /especialidades/:id} : delete the "id" especialidade.
     *
     * @param id the id of the especialidadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEspecialidade(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Especialidade : {}", id);
        especialidadeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
