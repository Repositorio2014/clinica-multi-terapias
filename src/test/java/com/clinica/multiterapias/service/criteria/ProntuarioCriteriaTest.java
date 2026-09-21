package com.clinica.multiterapias.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ProntuarioCriteriaTest {

    @Test
    void newProntuarioCriteriaHasAllFiltersNullTest() {
        var prontuarioCriteria = new ProntuarioCriteria();
        assertThat(prontuarioCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void prontuarioCriteriaFluentMethodsCreatesFiltersTest() {
        var prontuarioCriteria = new ProntuarioCriteria();

        setAllFilters(prontuarioCriteria);

        assertThat(prontuarioCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void prontuarioCriteriaCopyCreatesNullFilterTest() {
        var prontuarioCriteria = new ProntuarioCriteria();
        var copy = prontuarioCriteria.copy();

        assertThat(prontuarioCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(prontuarioCriteria)
        );
    }

    @Test
    void prontuarioCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var prontuarioCriteria = new ProntuarioCriteria();
        setAllFilters(prontuarioCriteria);

        var copy = prontuarioCriteria.copy();

        assertThat(prontuarioCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(prontuarioCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var prontuarioCriteria = new ProntuarioCriteria();

        assertThat(prontuarioCriteria).hasToString("ProntuarioCriteria{}");
    }

    private static void setAllFilters(ProntuarioCriteria prontuarioCriteria) {
        prontuarioCriteria.id();
        prontuarioCriteria.tipo();
        prontuarioCriteria.dataAtendimento();
        prontuarioCriteria.titulo();
        prontuarioCriteria.confidencial();
        prontuarioCriteria.pacienteId();
        prontuarioCriteria.profissionalId();
        prontuarioCriteria.agendaId();
        prontuarioCriteria.especialidadeId();
        prontuarioCriteria.distinct();
    }

    private static Condition<ProntuarioCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getTipo()) &&
                condition.apply(criteria.getDataAtendimento()) &&
                condition.apply(criteria.getTitulo()) &&
                condition.apply(criteria.getConfidencial()) &&
                condition.apply(criteria.getPacienteId()) &&
                condition.apply(criteria.getProfissionalId()) &&
                condition.apply(criteria.getAgendaId()) &&
                condition.apply(criteria.getEspecialidadeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ProntuarioCriteria> copyFiltersAre(ProntuarioCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getTipo(), copy.getTipo()) &&
                condition.apply(criteria.getDataAtendimento(), copy.getDataAtendimento()) &&
                condition.apply(criteria.getTitulo(), copy.getTitulo()) &&
                condition.apply(criteria.getConfidencial(), copy.getConfidencial()) &&
                condition.apply(criteria.getPacienteId(), copy.getPacienteId()) &&
                condition.apply(criteria.getProfissionalId(), copy.getProfissionalId()) &&
                condition.apply(criteria.getAgendaId(), copy.getAgendaId()) &&
                condition.apply(criteria.getEspecialidadeId(), copy.getEspecialidadeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
