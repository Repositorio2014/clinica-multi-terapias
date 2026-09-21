package com.clinica.multiterapias.service.criteria;

import com.clinica.multiterapias.domain.enumeration.StatusAgendamento;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.clinica.multiterapias.domain.Agenda} entity. This class is used
 * in {@link com.clinica.multiterapias.web.rest.AgendaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /agenda?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AgendaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering StatusAgendamento
     */
    public static class StatusAgendamentoFilter extends Filter<StatusAgendamento> {

        public StatusAgendamentoFilter() {}

        public StatusAgendamentoFilter(StatusAgendamentoFilter filter) {
            super(filter);
        }

        @Override
        public StatusAgendamentoFilter copy() {
            return new StatusAgendamentoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter dataHoraInicio;

    private ZonedDateTimeFilter dataHoraFim;

    private StatusAgendamentoFilter status;

    private StringFilter observacoes;

    private BigDecimalFilter valorCobrado;

    private LongFilter pacienteId;

    private LongFilter profissionalId;

    private LongFilter salaId;

    private LongFilter especialidadeId;

    private Boolean distinct;

    public AgendaCriteria() {}

    public AgendaCriteria(AgendaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.dataHoraInicio = other.optionalDataHoraInicio().map(ZonedDateTimeFilter::copy).orElse(null);
        this.dataHoraFim = other.optionalDataHoraFim().map(ZonedDateTimeFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(StatusAgendamentoFilter::copy).orElse(null);
        this.observacoes = other.optionalObservacoes().map(StringFilter::copy).orElse(null);
        this.valorCobrado = other.optionalValorCobrado().map(BigDecimalFilter::copy).orElse(null);
        this.pacienteId = other.optionalPacienteId().map(LongFilter::copy).orElse(null);
        this.profissionalId = other.optionalProfissionalId().map(LongFilter::copy).orElse(null);
        this.salaId = other.optionalSalaId().map(LongFilter::copy).orElse(null);
        this.especialidadeId = other.optionalEspecialidadeId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AgendaCriteria copy() {
        return new AgendaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getDataHoraInicio() {
        return dataHoraInicio;
    }

    public Optional<ZonedDateTimeFilter> optionalDataHoraInicio() {
        return Optional.ofNullable(dataHoraInicio);
    }

    public ZonedDateTimeFilter dataHoraInicio() {
        if (dataHoraInicio == null) {
            setDataHoraInicio(new ZonedDateTimeFilter());
        }
        return dataHoraInicio;
    }

    public void setDataHoraInicio(ZonedDateTimeFilter dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public ZonedDateTimeFilter getDataHoraFim() {
        return dataHoraFim;
    }

    public Optional<ZonedDateTimeFilter> optionalDataHoraFim() {
        return Optional.ofNullable(dataHoraFim);
    }

    public ZonedDateTimeFilter dataHoraFim() {
        if (dataHoraFim == null) {
            setDataHoraFim(new ZonedDateTimeFilter());
        }
        return dataHoraFim;
    }

    public void setDataHoraFim(ZonedDateTimeFilter dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public StatusAgendamentoFilter getStatus() {
        return status;
    }

    public Optional<StatusAgendamentoFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public StatusAgendamentoFilter status() {
        if (status == null) {
            setStatus(new StatusAgendamentoFilter());
        }
        return status;
    }

    public void setStatus(StatusAgendamentoFilter status) {
        this.status = status;
    }

    public StringFilter getObservacoes() {
        return observacoes;
    }

    public Optional<StringFilter> optionalObservacoes() {
        return Optional.ofNullable(observacoes);
    }

    public StringFilter observacoes() {
        if (observacoes == null) {
            setObservacoes(new StringFilter());
        }
        return observacoes;
    }

    public void setObservacoes(StringFilter observacoes) {
        this.observacoes = observacoes;
    }

    public BigDecimalFilter getValorCobrado() {
        return valorCobrado;
    }

    public Optional<BigDecimalFilter> optionalValorCobrado() {
        return Optional.ofNullable(valorCobrado);
    }

    public BigDecimalFilter valorCobrado() {
        if (valorCobrado == null) {
            setValorCobrado(new BigDecimalFilter());
        }
        return valorCobrado;
    }

    public void setValorCobrado(BigDecimalFilter valorCobrado) {
        this.valorCobrado = valorCobrado;
    }

    public LongFilter getPacienteId() {
        return pacienteId;
    }

    public Optional<LongFilter> optionalPacienteId() {
        return Optional.ofNullable(pacienteId);
    }

    public LongFilter pacienteId() {
        if (pacienteId == null) {
            setPacienteId(new LongFilter());
        }
        return pacienteId;
    }

    public void setPacienteId(LongFilter pacienteId) {
        this.pacienteId = pacienteId;
    }

    public LongFilter getProfissionalId() {
        return profissionalId;
    }

    public Optional<LongFilter> optionalProfissionalId() {
        return Optional.ofNullable(profissionalId);
    }

    public LongFilter profissionalId() {
        if (profissionalId == null) {
            setProfissionalId(new LongFilter());
        }
        return profissionalId;
    }

    public void setProfissionalId(LongFilter profissionalId) {
        this.profissionalId = profissionalId;
    }

    public LongFilter getSalaId() {
        return salaId;
    }

    public Optional<LongFilter> optionalSalaId() {
        return Optional.ofNullable(salaId);
    }

    public LongFilter salaId() {
        if (salaId == null) {
            setSalaId(new LongFilter());
        }
        return salaId;
    }

    public void setSalaId(LongFilter salaId) {
        this.salaId = salaId;
    }

    public LongFilter getEspecialidadeId() {
        return especialidadeId;
    }

    public Optional<LongFilter> optionalEspecialidadeId() {
        return Optional.ofNullable(especialidadeId);
    }

    public LongFilter especialidadeId() {
        if (especialidadeId == null) {
            setEspecialidadeId(new LongFilter());
        }
        return especialidadeId;
    }

    public void setEspecialidadeId(LongFilter especialidadeId) {
        this.especialidadeId = especialidadeId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AgendaCriteria that = (AgendaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dataHoraInicio, that.dataHoraInicio) &&
            Objects.equals(dataHoraFim, that.dataHoraFim) &&
            Objects.equals(status, that.status) &&
            Objects.equals(observacoes, that.observacoes) &&
            Objects.equals(valorCobrado, that.valorCobrado) &&
            Objects.equals(pacienteId, that.pacienteId) &&
            Objects.equals(profissionalId, that.profissionalId) &&
            Objects.equals(salaId, that.salaId) &&
            Objects.equals(especialidadeId, that.especialidadeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            dataHoraInicio,
            dataHoraFim,
            status,
            observacoes,
            valorCobrado,
            pacienteId,
            profissionalId,
            salaId,
            especialidadeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgendaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDataHoraInicio().map(f -> "dataHoraInicio=" + f + ", ").orElse("") +
            optionalDataHoraFim().map(f -> "dataHoraFim=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalObservacoes().map(f -> "observacoes=" + f + ", ").orElse("") +
            optionalValorCobrado().map(f -> "valorCobrado=" + f + ", ").orElse("") +
            optionalPacienteId().map(f -> "pacienteId=" + f + ", ").orElse("") +
            optionalProfissionalId().map(f -> "profissionalId=" + f + ", ").orElse("") +
            optionalSalaId().map(f -> "salaId=" + f + ", ").orElse("") +
            optionalEspecialidadeId().map(f -> "especialidadeId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
