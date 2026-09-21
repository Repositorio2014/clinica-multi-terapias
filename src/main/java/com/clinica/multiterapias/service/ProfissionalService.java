package com.clinica.multiterapias.service;

import com.clinica.multiterapias.domain.Profissional;
import com.clinica.multiterapias.repository.ProfissionalRepository;
import com.clinica.multiterapias.service.dto.ProfissionalDTO;
import com.clinica.multiterapias.service.mapper.ProfissionalMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.clinica.multiterapias.domain.Profissional}.
 */
@Service
@Transactional
public class ProfissionalService {

    private static final Logger LOG = LoggerFactory.getLogger(ProfissionalService.class);

    private final ProfissionalRepository profissionalRepository;

    private final ProfissionalMapper profissionalMapper;

    public ProfissionalService(ProfissionalRepository profissionalRepository, ProfissionalMapper profissionalMapper) {
        this.profissionalRepository = profissionalRepository;
        this.profissionalMapper = profissionalMapper;
    }

    /**
     * Save a profissional.
     *
     * @param profissionalDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfissionalDTO save(ProfissionalDTO profissionalDTO) {
        LOG.debug("Request to save Profissional : {}", profissionalDTO);
        Profissional profissional = profissionalMapper.toEntity(profissionalDTO);
        profissional = profissionalRepository.save(profissional);
        return profissionalMapper.toDto(profissional);
    }

    /**
     * Update a profissional.
     *
     * @param profissionalDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfissionalDTO update(ProfissionalDTO profissionalDTO) {
        LOG.debug("Request to update Profissional : {}", profissionalDTO);
        Profissional profissional = profissionalMapper.toEntity(profissionalDTO);
        profissional = profissionalRepository.save(profissional);
        return profissionalMapper.toDto(profissional);
    }

    /**
     * Partially update a profissional.
     *
     * @param profissionalDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProfissionalDTO> partialUpdate(ProfissionalDTO profissionalDTO) {
        LOG.debug("Request to partially update Profissional : {}", profissionalDTO);

        return profissionalRepository
            .findById(profissionalDTO.getId())
            .map(existingProfissional -> {
                profissionalMapper.partialUpdate(existingProfissional, profissionalDTO);

                return existingProfissional;
            })
            .map(profissionalRepository::save)
            .map(profissionalMapper::toDto);
    }

    /**
     * Get all the profissionals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfissionalDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Profissionals");
        return profissionalRepository.findAll(pageable).map(profissionalMapper::toDto);
    }

    /**
     * Get all the profissionals with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProfissionalDTO> findAllWithEagerRelationships(Pageable pageable) {
        return profissionalRepository.findAllWithEagerRelationships(pageable).map(profissionalMapper::toDto);
    }

    /**
     * Get one profissional by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProfissionalDTO> findOne(Long id) {
        LOG.debug("Request to get Profissional : {}", id);
        return profissionalRepository.findOneWithEagerRelationships(id).map(profissionalMapper::toDto);
    }

    /**
     * Delete the profissional by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Profissional : {}", id);
        profissionalRepository.deleteById(id);
    }
}
