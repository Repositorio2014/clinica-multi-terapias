package com.clinica.multiterapias.repository;

import com.clinica.multiterapias.domain.Profissional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ProfissionalRepositoryWithBagRelationships {
    Optional<Profissional> fetchBagRelationships(Optional<Profissional> profissional);

    List<Profissional> fetchBagRelationships(List<Profissional> profissionals);

    Page<Profissional> fetchBagRelationships(Page<Profissional> profissionals);
}
