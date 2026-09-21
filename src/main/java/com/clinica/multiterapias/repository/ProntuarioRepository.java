package com.clinica.multiterapias.repository;

import com.clinica.multiterapias.domain.Prontuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Prontuario entity.
 */
@Repository
public interface ProntuarioRepository extends JpaRepository<Prontuario, Long>, JpaSpecificationExecutor<Prontuario> {
    default Optional<Prontuario> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Prontuario> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Prontuario> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select prontuario from Prontuario prontuario left join fetch prontuario.paciente left join fetch prontuario.profissional left join fetch prontuario.especialidade",
        countQuery = "select count(prontuario) from Prontuario prontuario"
    )
    Page<Prontuario> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select prontuario from Prontuario prontuario left join fetch prontuario.paciente left join fetch prontuario.profissional left join fetch prontuario.especialidade"
    )
    List<Prontuario> findAllWithToOneRelationships();

    @Query(
        "select prontuario from Prontuario prontuario left join fetch prontuario.paciente left join fetch prontuario.profissional left join fetch prontuario.especialidade where prontuario.id =:id"
    )
    Optional<Prontuario> findOneWithToOneRelationships(@Param("id") Long id);
}
