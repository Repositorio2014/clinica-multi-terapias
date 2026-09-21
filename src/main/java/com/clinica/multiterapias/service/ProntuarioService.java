package com.clinica.multiterapias.service;

import com.clinica.multiterapias.domain.Prontuario;
import com.clinica.multiterapias.repository.ProntuarioRepository;
import com.clinica.multiterapias.service.dto.ProntuarioDTO;
import com.clinica.multiterapias.service.mapper.ProntuarioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.clinica.multiterapias.domain.Prontuario}.
 */
@Service
@Transactional
public class ProntuarioService {

    private static final Logger LOG = LoggerFactory.getLogger(ProntuarioService.class);

    private final ProntuarioRepository prontuarioRepository;

    private final ProntuarioMapper prontuarioMapper;

    public ProntuarioService(ProntuarioRepository prontuarioRepository, ProntuarioMapper prontuarioMapper) {
        this.prontuarioRepository = prontuarioRepository;
        this.prontuarioMapper = prontuarioMapper;
    }

    /**
     * Save a prontuario.
     *
     * @param prontuarioDTO the entity to save.
     * @return the persisted entity.
     */
    public ProntuarioDTO save(ProntuarioDTO prontuarioDTO) {
        LOG.debug("Request to save Prontuario : {}", prontuarioDTO);
        Prontuario prontuario = prontuarioMapper.toEntity(prontuarioDTO);
        prontuario = prontuarioRepository.save(prontuario);
        return prontuarioMapper.toDto(prontuario);
    }

    /**
     * Update a prontuario.
     *
     * @param prontuarioDTO the entity to save.
     * @return the persisted entity.
     */
    public ProntuarioDTO update(ProntuarioDTO prontuarioDTO) {
        LOG.debug("Request to update Prontuario : {}", prontuarioDTO);
        Prontuario prontuario = prontuarioMapper.toEntity(prontuarioDTO);
        prontuario = prontuarioRepository.save(prontuario);
        return prontuarioMapper.toDto(prontuario);
    }

    /**
     * Partially update a prontuario.
     *
     * @param prontuarioDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProntuarioDTO> partialUpdate(ProntuarioDTO prontuarioDTO) {
        LOG.debug("Request to partially update Prontuario : {}", prontuarioDTO);

        return prontuarioRepository
            .findById(prontuarioDTO.getId())
            .map(existingProntuario -> {
                prontuarioMapper.partialUpdate(existingProntuario, prontuarioDTO);

                return existingProntuario;
            })
            .map(prontuarioRepository::save)
            .map(prontuarioMapper::toDto);
    }

    /**
     * Get all the prontuarios with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProntuarioDTO> findAllWithEagerRelationships(Pageable pageable) {
        return prontuarioRepository.findAllWithEagerRelationships(pageable).map(prontuarioMapper::toDto);
    }

    /**
     * Get one prontuario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProntuarioDTO> findOne(Long id) {
        LOG.debug("Request to get Prontuario : {}", id);
        return prontuarioRepository.findOneWithEagerRelationships(id).map(prontuarioMapper::toDto);
    }

    /**
     * Delete the prontuario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Prontuario : {}", id);
        prontuarioRepository.deleteById(id);
    }
}
