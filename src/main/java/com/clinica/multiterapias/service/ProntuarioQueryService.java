package com.clinica.multiterapias.service;

import com.clinica.multiterapias.domain.*; // for static metamodels
import com.clinica.multiterapias.domain.Prontuario;
import com.clinica.multiterapias.repository.ProntuarioRepository;
import com.clinica.multiterapias.service.criteria.ProntuarioCriteria;
import com.clinica.multiterapias.service.dto.ProntuarioDTO;
import com.clinica.multiterapias.service.mapper.ProntuarioMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Prontuario} entities in the database.
 * The main input is a {@link ProntuarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ProntuarioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProntuarioQueryService extends QueryService<Prontuario> {

    private static final Logger LOG = LoggerFactory.getLogger(ProntuarioQueryService.class);

    private final ProntuarioRepository prontuarioRepository;

    private final ProntuarioMapper prontuarioMapper;

    public ProntuarioQueryService(ProntuarioRepository prontuarioRepository, ProntuarioMapper prontuarioMapper) {
        this.prontuarioRepository = prontuarioRepository;
        this.prontuarioMapper = prontuarioMapper;
    }

    /**
     * Return a {@link Page} of {@link ProntuarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProntuarioDTO> findByCriteria(ProntuarioCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Prontuario> specification = createSpecification(criteria);
        return prontuarioRepository.findAll(specification, page).map(prontuarioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProntuarioCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Prontuario> specification = createSpecification(criteria);
        return prontuarioRepository.count(specification);
    }

    /**
     * Function to convert {@link ProntuarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Prontuario> createSpecification(ProntuarioCriteria criteria) {
        Specification<Prontuario> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Prontuario_.id));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildSpecification(criteria.getTipo(), Prontuario_.tipo));
            }
            if (criteria.getDataAtendimento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataAtendimento(), Prontuario_.dataAtendimento));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), Prontuario_.titulo));
            }
            if (criteria.getConfidencial() != null) {
                specification = specification.and(buildSpecification(criteria.getConfidencial(), Prontuario_.confidencial));
            }
            if (criteria.getPacienteId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getPacienteId(), root -> root.join(Prontuario_.paciente, JoinType.LEFT).get(Paciente_.id))
                );
            }
            if (criteria.getProfissionalId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getProfissionalId(), root ->
                        root.join(Prontuario_.profissional, JoinType.LEFT).get(Profissional_.id)
                    )
                );
            }
            if (criteria.getAgendaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getAgendaId(), root -> root.join(Prontuario_.agenda, JoinType.LEFT).get(Agenda_.id))
                );
            }
            if (criteria.getEspecialidadeId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEspecialidadeId(), root ->
                        root.join(Prontuario_.especialidade, JoinType.LEFT).get(Especialidade_.id)
                    )
                );
            }
        }
        return specification;
    }
}
