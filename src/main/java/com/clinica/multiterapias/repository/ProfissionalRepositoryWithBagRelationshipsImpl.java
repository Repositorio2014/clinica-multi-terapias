package com.clinica.multiterapias.repository;

import com.clinica.multiterapias.domain.Profissional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ProfissionalRepositoryWithBagRelationshipsImpl implements ProfissionalRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String PROFISSIONALS_PARAMETER = "profissionals";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Profissional> fetchBagRelationships(Optional<Profissional> profissional) {
        return profissional.map(this::fetchEspecialidades);
    }

    @Override
    public Page<Profissional> fetchBagRelationships(Page<Profissional> profissionals) {
        return new PageImpl<>(
            fetchBagRelationships(profissionals.getContent()),
            profissionals.getPageable(),
            profissionals.getTotalElements()
        );
    }

    @Override
    public List<Profissional> fetchBagRelationships(List<Profissional> profissionals) {
        return Optional.of(profissionals).map(this::fetchEspecialidades).orElse(Collections.emptyList());
    }

    Profissional fetchEspecialidades(Profissional result) {
        return entityManager
            .createQuery(
                "select profissional from Profissional profissional left join fetch profissional.especialidades where profissional.id = :id",
                Profissional.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Profissional> fetchEspecialidades(List<Profissional> profissionals) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, profissionals.size()).forEach(index -> order.put(profissionals.get(index).getId(), index));
        List<Profissional> result = entityManager
            .createQuery(
                "select profissional from Profissional profissional left join fetch profissional.especialidades where profissional in :profissionals",
                Profissional.class
            )
            .setParameter(PROFISSIONALS_PARAMETER, profissionals)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
