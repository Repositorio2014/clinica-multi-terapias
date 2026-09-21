package com.clinica.multiterapias.service;

import com.clinica.multiterapias.domain.Agenda;
import com.clinica.multiterapias.repository.AgendaRepository;
import com.clinica.multiterapias.service.dto.AgendaDTO;
import com.clinica.multiterapias.service.mapper.AgendaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.clinica.multiterapias.domain.Agenda}.
 */
@Service
@Transactional
public class AgendaService {

    private static final Logger LOG = LoggerFactory.getLogger(AgendaService.class);

    private final AgendaRepository agendaRepository;

    private final AgendaMapper agendaMapper;

    public AgendaService(AgendaRepository agendaRepository, AgendaMapper agendaMapper) {
        this.agendaRepository = agendaRepository;
        this.agendaMapper = agendaMapper;
    }

    /**
     * Save a agenda.
     *
     * @param agendaDTO the entity to save.
     * @return the persisted entity.
     */
    public AgendaDTO save(AgendaDTO agendaDTO) {
        LOG.debug("Request to save Agenda : {}", agendaDTO);
        Agenda agenda = agendaMapper.toEntity(agendaDTO);
        agenda = agendaRepository.save(agenda);
        return agendaMapper.toDto(agenda);
    }

    /**
     * Update a agenda.
     *
     * @param agendaDTO the entity to save.
     * @return the persisted entity.
     */
    public AgendaDTO update(AgendaDTO agendaDTO) {
        LOG.debug("Request to update Agenda : {}", agendaDTO);
        Agenda agenda = agendaMapper.toEntity(agendaDTO);
        agenda = agendaRepository.save(agenda);
        return agendaMapper.toDto(agenda);
    }

    /**
     * Partially update a agenda.
     *
     * @param agendaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AgendaDTO> partialUpdate(AgendaDTO agendaDTO) {
        LOG.debug("Request to partially update Agenda : {}", agendaDTO);

        return agendaRepository
            .findById(agendaDTO.getId())
            .map(existingAgenda -> {
                agendaMapper.partialUpdate(existingAgenda, agendaDTO);

                return existingAgenda;
            })
            .map(agendaRepository::save)
            .map(agendaMapper::toDto);
    }

    /**
     * Get all the agenda with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AgendaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return agendaRepository.findAllWithEagerRelationships(pageable).map(agendaMapper::toDto);
    }

    /**
     * Get one agenda by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AgendaDTO> findOne(Long id) {
        LOG.debug("Request to get Agenda : {}", id);
        return agendaRepository.findOneWithEagerRelationships(id).map(agendaMapper::toDto);
    }

    /**
     * Delete the agenda by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Agenda : {}", id);
        agendaRepository.deleteById(id);
    }
}
