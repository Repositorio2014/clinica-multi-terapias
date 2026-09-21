package com.clinica.multiterapias.service;

import com.clinica.multiterapias.domain.Sala;
import com.clinica.multiterapias.repository.SalaRepository;
import com.clinica.multiterapias.service.dto.SalaDTO;
import com.clinica.multiterapias.service.mapper.SalaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.clinica.multiterapias.domain.Sala}.
 */
@Service
@Transactional
public class SalaService {

    private static final Logger LOG = LoggerFactory.getLogger(SalaService.class);

    private final SalaRepository salaRepository;

    private final SalaMapper salaMapper;

    public SalaService(SalaRepository salaRepository, SalaMapper salaMapper) {
        this.salaRepository = salaRepository;
        this.salaMapper = salaMapper;
    }

    /**
     * Save a sala.
     *
     * @param salaDTO the entity to save.
     * @return the persisted entity.
     */
    public SalaDTO save(SalaDTO salaDTO) {
        LOG.debug("Request to save Sala : {}", salaDTO);
        Sala sala = salaMapper.toEntity(salaDTO);
        sala = salaRepository.save(sala);
        return salaMapper.toDto(sala);
    }

    /**
     * Update a sala.
     *
     * @param salaDTO the entity to save.
     * @return the persisted entity.
     */
    public SalaDTO update(SalaDTO salaDTO) {
        LOG.debug("Request to update Sala : {}", salaDTO);
        Sala sala = salaMapper.toEntity(salaDTO);
        sala = salaRepository.save(sala);
        return salaMapper.toDto(sala);
    }

    /**
     * Partially update a sala.
     *
     * @param salaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SalaDTO> partialUpdate(SalaDTO salaDTO) {
        LOG.debug("Request to partially update Sala : {}", salaDTO);

        return salaRepository
            .findById(salaDTO.getId())
            .map(existingSala -> {
                salaMapper.partialUpdate(existingSala, salaDTO);

                return existingSala;
            })
            .map(salaRepository::save)
            .map(salaMapper::toDto);
    }

    /**
     * Get all the salas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SalaDTO> findAll() {
        LOG.debug("Request to get all Salas");
        return salaRepository.findAll().stream().map(salaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sala by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalaDTO> findOne(Long id) {
        LOG.debug("Request to get Sala : {}", id);
        return salaRepository.findById(id).map(salaMapper::toDto);
    }

    /**
     * Delete the sala by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Sala : {}", id);
        salaRepository.deleteById(id);
    }
}
