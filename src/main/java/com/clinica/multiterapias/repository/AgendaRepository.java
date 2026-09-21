package com.clinica.multiterapias.repository;

import com.clinica.multiterapias.domain.Agenda;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Agenda entity.
 */
@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long>, JpaSpecificationExecutor<Agenda> {
    default Optional<Agenda> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Agenda> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Agenda> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select agenda from Agenda agenda left join fetch agenda.paciente left join fetch agenda.profissional left join fetch agenda.sala left join fetch agenda.especialidade",
        countQuery = "select count(agenda) from Agenda agenda"
    )
    Page<Agenda> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select agenda from Agenda agenda left join fetch agenda.paciente left join fetch agenda.profissional left join fetch agenda.sala left join fetch agenda.especialidade"
    )
    List<Agenda> findAllWithToOneRelationships();

    @Query(
        "select agenda from Agenda agenda left join fetch agenda.paciente left join fetch agenda.profissional left join fetch agenda.sala left join fetch agenda.especialidade where agenda.id =:id"
    )
    Optional<Agenda> findOneWithToOneRelationships(@Param("id") Long id);
}
