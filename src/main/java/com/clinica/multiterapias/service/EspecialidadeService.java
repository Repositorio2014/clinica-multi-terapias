package com.clinica.multiterapias.service;

import com.clinica.multiterapias.domain.Especialidade;
import com.clinica.multiterapias.repository.EspecialidadeRepository;
import com.clinica.multiterapias.service.dto.EspecialidadeDTO;
import com.clinica.multiterapias.service.mapper.EspecialidadeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.clinica.multiterapias.domain.Especialidade}.
 */
@Service
@Transactional
public class EspecialidadeService {

    private static final Logger LOG = LoggerFactory.getLogger(EspecialidadeService.class);

    private final EspecialidadeRepository especialidadeRepository;

    private final EspecialidadeMapper especialidadeMapper;

    public EspecialidadeService(EspecialidadeRepository especialidadeRepository, EspecialidadeMapper especialidadeMapper) {
        this.especialidadeRepository = especialidadeRepository;
        this.especialidadeMapper = especialidadeMapper;
    }

    /**
     * Save a especialidade.
     *
     * @param especialidadeDTO the entity to save.
     * @return the persisted entity.
     */
    public EspecialidadeDTO save(EspecialidadeDTO especialidadeDTO) {
        LOG.debug("Request to save Especialidade : {}", especialidadeDTO);
        Especialidade especialidade = especialidadeMapper.toEntity(especialidadeDTO);
        especialidade = especialidadeRepository.save(especialidade);
        return especialidadeMapper.toDto(especialidade);
    }

    /**
     * Update a especialidade.
     *
     * @param especialidadeDTO the entity to save.
     * @return the persisted entity.
     */
    public EspecialidadeDTO update(EspecialidadeDTO especialidadeDTO) {
        LOG.debug("Request to update Especialidade : {}", especialidadeDTO);
        Especialidade especialidade = especialidadeMapper.toEntity(especialidadeDTO);
        especialidade = especialidadeRepository.save(especialidade);
        return especialidadeMapper.toDto(especialidade);
    }

    /**
     * Partially update a especialidade.
     *
     * @param especialidadeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EspecialidadeDTO> partialUpdate(EspecialidadeDTO especialidadeDTO) {
        LOG.debug("Request to partially update Especialidade : {}", especialidadeDTO);

        return especialidadeRepository
            .findById(especialidadeDTO.getId())
            .map(existingEspecialidade -> {
                especialidadeMapper.partialUpdate(existingEspecialidade, especialidadeDTO);

                return existingEspecialidade;
            })
            .map(especialidadeRepository::save)
            .map(especialidadeMapper::toDto);
    }

    /**
     * Get all the especialidades.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EspecialidadeDTO> findAll() {
        LOG.debug("Request to get all Especialidades");
        return especialidadeRepository.findAll().stream().map(especialidadeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one especialidade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EspecialidadeDTO> findOne(Long id) {
        LOG.debug("Request to get Especialidade : {}", id);
        return especialidadeRepository.findById(id).map(especialidadeMapper::toDto);
    }

    /**
     * Delete the especialidade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Especialidade : {}", id);
        especialidadeRepository.deleteById(id);
    }
}
