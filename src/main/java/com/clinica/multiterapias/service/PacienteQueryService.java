package com.clinica.multiterapias.service;

import com.clinica.multiterapias.domain.*; // for static metamodels
import com.clinica.multiterapias.domain.Paciente;
import com.clinica.multiterapias.repository.PacienteRepository;
import com.clinica.multiterapias.service.criteria.PacienteCriteria;
import com.clinica.multiterapias.service.dto.PacienteDTO;
import com.clinica.multiterapias.service.mapper.PacienteMapper;
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
 * Service for executing complex queries for {@link Paciente} entities in the database.
 * The main input is a {@link PacienteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PacienteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PacienteQueryService extends QueryService<Paciente> {

    private static final Logger LOG = LoggerFactory.getLogger(PacienteQueryService.class);

    private final PacienteRepository pacienteRepository;

    private final PacienteMapper pacienteMapper;

    public PacienteQueryService(PacienteRepository pacienteRepository, PacienteMapper pacienteMapper) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper;
    }

    /**
     * Return a {@link Page} of {@link PacienteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PacienteDTO> findByCriteria(PacienteCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Paciente> specification = createSpecification(criteria);
        return pacienteRepository.findAll(specification, page).map(pacienteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PacienteCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Paciente> specification = createSpecification(criteria);
        return pacienteRepository.count(specification);
    }

    /**
     * Function to convert {@link PacienteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Paciente> createSpecification(PacienteCriteria criteria) {
        Specification<Paciente> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Paciente_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Paciente_.nome));
            }
            if (criteria.getCpf() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCpf(), Paciente_.cpf));
            }
            if (criteria.getDataNascimento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataNascimento(), Paciente_.dataNascimento));
            }
            if (criteria.getTelefone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefone(), Paciente_.telefone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Paciente_.email));
            }
            if (criteria.getNomeResponsavel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeResponsavel(), Paciente_.nomeResponsavel));
            }
            if (criteria.getTelefoneResponsavel() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getTelefoneResponsavel(), Paciente_.telefoneResponsavel)
                );
            }
            if (criteria.getEndereco() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndereco(), Paciente_.endereco));
            }
            if (criteria.getConvenio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConvenio(), Paciente_.convenio));
            }
            if (criteria.getAtivo() != null) {
                specification = specification.and(buildSpecification(criteria.getAtivo(), Paciente_.ativo));
            }
            if (criteria.getAgendasId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getAgendasId(), root -> root.join(Paciente_.agendas, JoinType.LEFT).get(Agenda_.id))
                );
            }
            if (criteria.getProntuariosId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getProntuariosId(), root ->
                        root.join(Paciente_.prontuarios, JoinType.LEFT).get(Prontuario_.id)
                    )
                );
            }
        }
        return specification;
    }
}
