package com.clinica.multiterapias.service;

import com.clinica.multiterapias.domain.*; // for static metamodels
import com.clinica.multiterapias.domain.Agenda;
import com.clinica.multiterapias.repository.AgendaRepository;
import com.clinica.multiterapias.service.criteria.AgendaCriteria;
import com.clinica.multiterapias.service.dto.AgendaDTO;
import com.clinica.multiterapias.service.mapper.AgendaMapper;
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
 * Service for executing complex queries for {@link Agenda} entities in the database.
 * The main input is a {@link AgendaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link AgendaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AgendaQueryService extends QueryService<Agenda> {

    private static final Logger LOG = LoggerFactory.getLogger(AgendaQueryService.class);

    private final AgendaRepository agendaRepository;

    private final AgendaMapper agendaMapper;

    public AgendaQueryService(AgendaRepository agendaRepository, AgendaMapper agendaMapper) {
        this.agendaRepository = agendaRepository;
        this.agendaMapper = agendaMapper;
    }

    /**
     * Return a {@link Page} of {@link AgendaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AgendaDTO> findByCriteria(AgendaCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Agenda> specification = createSpecification(criteria);
        return agendaRepository.findAll(specification, page).map(agendaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AgendaCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Agenda> specification = createSpecification(criteria);
        return agendaRepository.count(specification);
    }

    /**
     * Function to convert {@link AgendaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Agenda> createSpecification(AgendaCriteria criteria) {
        Specification<Agenda> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Agenda_.id));
            }
            if (criteria.getDataHoraInicio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataHoraInicio(), Agenda_.dataHoraInicio));
            }
            if (criteria.getDataHoraFim() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataHoraFim(), Agenda_.dataHoraFim));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Agenda_.status));
            }
            if (criteria.getObservacoes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservacoes(), Agenda_.observacoes));
            }
            if (criteria.getValorCobrado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorCobrado(), Agenda_.valorCobrado));
            }
            if (criteria.getPacienteId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getPacienteId(), root -> root.join(Agenda_.paciente, JoinType.LEFT).get(Paciente_.id))
                );
            }
            if (criteria.getProfissionalId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getProfissionalId(), root ->
                        root.join(Agenda_.profissional, JoinType.LEFT).get(Profissional_.id)
                    )
                );
            }
            if (criteria.getSalaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getSalaId(), root -> root.join(Agenda_.sala, JoinType.LEFT).get(Sala_.id))
                );
            }
            if (criteria.getEspecialidadeId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEspecialidadeId(), root ->
                        root.join(Agenda_.especialidade, JoinType.LEFT).get(Especialidade_.id)
                    )
                );
            }
        }
        return specification;
    }
}
