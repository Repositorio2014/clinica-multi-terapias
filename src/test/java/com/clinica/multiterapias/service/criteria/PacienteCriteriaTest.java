package com.clinica.multiterapias.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PacienteCriteriaTest {

    @Test
    void newPacienteCriteriaHasAllFiltersNullTest() {
        var pacienteCriteria = new PacienteCriteria();
        assertThat(pacienteCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void pacienteCriteriaFluentMethodsCreatesFiltersTest() {
        var pacienteCriteria = new PacienteCriteria();

        setAllFilters(pacienteCriteria);

        assertThat(pacienteCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void pacienteCriteriaCopyCreatesNullFilterTest() {
        var pacienteCriteria = new PacienteCriteria();
        var copy = pacienteCriteria.copy();

        assertThat(pacienteCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(pacienteCriteria)
        );
    }

    @Test
    void pacienteCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var pacienteCriteria = new PacienteCriteria();
        setAllFilters(pacienteCriteria);

        var copy = pacienteCriteria.copy();

        assertThat(pacienteCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(pacienteCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var pacienteCriteria = new PacienteCriteria();

        assertThat(pacienteCriteria).hasToString("PacienteCriteria{}");
    }

    private static void setAllFilters(PacienteCriteria pacienteCriteria) {
        pacienteCriteria.id();
        pacienteCriteria.nome();
        pacienteCriteria.cpf();
        pacienteCriteria.dataNascimento();
        pacienteCriteria.telefone();
        pacienteCriteria.email();
        pacienteCriteria.nomeResponsavel();
        pacienteCriteria.telefoneResponsavel();
        pacienteCriteria.endereco();
        pacienteCriteria.convenio();
        pacienteCriteria.ativo();
        pacienteCriteria.agendasId();
        pacienteCriteria.prontuariosId();
        pacienteCriteria.distinct();
    }

    private static Condition<PacienteCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getCpf()) &&
                condition.apply(criteria.getDataNascimento()) &&
                condition.apply(criteria.getTelefone()) &&
                condition.apply(criteria.getEmail()) &&
                condition.apply(criteria.getNomeResponsavel()) &&
                condition.apply(criteria.getTelefoneResponsavel()) &&
                condition.apply(criteria.getEndereco()) &&
                condition.apply(criteria.getConvenio()) &&
                condition.apply(criteria.getAtivo()) &&
                condition.apply(criteria.getAgendasId()) &&
                condition.apply(criteria.getProntuariosId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PacienteCriteria> copyFiltersAre(PacienteCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getCpf(), copy.getCpf()) &&
                condition.apply(criteria.getDataNascimento(), copy.getDataNascimento()) &&
                condition.apply(criteria.getTelefone(), copy.getTelefone()) &&
                condition.apply(criteria.getEmail(), copy.getEmail()) &&
                condition.apply(criteria.getNomeResponsavel(), copy.getNomeResponsavel()) &&
                condition.apply(criteria.getTelefoneResponsavel(), copy.getTelefoneResponsavel()) &&
                condition.apply(criteria.getEndereco(), copy.getEndereco()) &&
                condition.apply(criteria.getConvenio(), copy.getConvenio()) &&
                condition.apply(criteria.getAtivo(), copy.getAtivo()) &&
                condition.apply(criteria.getAgendasId(), copy.getAgendasId()) &&
                condition.apply(criteria.getProntuariosId(), copy.getProntuariosId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
