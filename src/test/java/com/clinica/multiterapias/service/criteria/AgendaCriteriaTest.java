package com.clinica.multiterapias.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AgendaCriteriaTest {

    @Test
    void newAgendaCriteriaHasAllFiltersNullTest() {
        var agendaCriteria = new AgendaCriteria();
        assertThat(agendaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void agendaCriteriaFluentMethodsCreatesFiltersTest() {
        var agendaCriteria = new AgendaCriteria();

        setAllFilters(agendaCriteria);

        assertThat(agendaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void agendaCriteriaCopyCreatesNullFilterTest() {
        var agendaCriteria = new AgendaCriteria();
        var copy = agendaCriteria.copy();

        assertThat(agendaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(agendaCriteria)
        );
    }

    @Test
    void agendaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var agendaCriteria = new AgendaCriteria();
        setAllFilters(agendaCriteria);

        var copy = agendaCriteria.copy();

        assertThat(agendaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(agendaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var agendaCriteria = new AgendaCriteria();

        assertThat(agendaCriteria).hasToString("AgendaCriteria{}");
    }

    private static void setAllFilters(AgendaCriteria agendaCriteria) {
        agendaCriteria.id();
        agendaCriteria.dataHoraInicio();
        agendaCriteria.dataHoraFim();
        agendaCriteria.status();
        agendaCriteria.observacoes();
        agendaCriteria.valorCobrado();
        agendaCriteria.pacienteId();
        agendaCriteria.profissionalId();
        agendaCriteria.salaId();
        agendaCriteria.especialidadeId();
        agendaCriteria.distinct();
    }

    private static Condition<AgendaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getDataHoraInicio()) &&
                condition.apply(criteria.getDataHoraFim()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getObservacoes()) &&
                condition.apply(criteria.getValorCobrado()) &&
                condition.apply(criteria.getPacienteId()) &&
                condition.apply(criteria.getProfissionalId()) &&
                condition.apply(criteria.getSalaId()) &&
                condition.apply(criteria.getEspecialidadeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AgendaCriteria> copyFiltersAre(AgendaCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getDataHoraInicio(), copy.getDataHoraInicio()) &&
                condition.apply(criteria.getDataHoraFim(), copy.getDataHoraFim()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getObservacoes(), copy.getObservacoes()) &&
                condition.apply(criteria.getValorCobrado(), copy.getValorCobrado()) &&
                condition.apply(criteria.getPacienteId(), copy.getPacienteId()) &&
                condition.apply(criteria.getProfissionalId(), copy.getProfissionalId()) &&
                condition.apply(criteria.getSalaId(), copy.getSalaId()) &&
                condition.apply(criteria.getEspecialidadeId(), copy.getEspecialidadeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
