package com.clinica.multiterapias.service.criteria;

import com.clinica.multiterapias.domain.enumeration.TipoProntuario;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.clinica.multiterapias.domain.Prontuario} entity. This class is used
 * in {@link com.clinica.multiterapias.web.rest.ProntuarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /prontuarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProntuarioCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TipoProntuario
     */
    public static class TipoProntuarioFilter extends Filter<TipoProntuario> {

        public TipoProntuarioFilter() {}

        public TipoProntuarioFilter(TipoProntuarioFilter filter) {
            super(filter);
        }

        @Override
        public TipoProntuarioFilter copy() {
            return new TipoProntuarioFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TipoProntuarioFilter tipo;

    private ZonedDateTimeFilter dataAtendimento;

    private StringFilter titulo;

    private BooleanFilter confidencial;

    private LongFilter pacienteId;

    private LongFilter profissionalId;

    private LongFilter agendaId;

    private LongFilter especialidadeId;

    private Boolean distinct;

    public ProntuarioCriteria() {}

    public ProntuarioCriteria(ProntuarioCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.tipo = other.optionalTipo().map(TipoProntuarioFilter::copy).orElse(null);
        this.dataAtendimento = other.optionalDataAtendimento().map(ZonedDateTimeFilter::copy).orElse(null);
        this.titulo = other.optionalTitulo().map(StringFilter::copy).orElse(null);
        this.confidencial = other.optionalConfidencial().map(BooleanFilter::copy).orElse(null);
        this.pacienteId = other.optionalPacienteId().map(LongFilter::copy).orElse(null);
        this.profissionalId = other.optionalProfissionalId().map(LongFilter::copy).orElse(null);
        this.agendaId = other.optionalAgendaId().map(LongFilter::copy).orElse(null);
        this.especialidadeId = other.optionalEspecialidadeId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ProntuarioCriteria copy() {
        return new ProntuarioCriteria(this);
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

    public TipoProntuarioFilter getTipo() {
        return tipo;
    }

    public Optional<TipoProntuarioFilter> optionalTipo() {
        return Optional.ofNullable(tipo);
    }

    public TipoProntuarioFilter tipo() {
        if (tipo == null) {
            setTipo(new TipoProntuarioFilter());
        }
        return tipo;
    }

    public void setTipo(TipoProntuarioFilter tipo) {
        this.tipo = tipo;
    }

    public ZonedDateTimeFilter getDataAtendimento() {
        return dataAtendimento;
    }

    public Optional<ZonedDateTimeFilter> optionalDataAtendimento() {
        return Optional.ofNullable(dataAtendimento);
    }

    public ZonedDateTimeFilter dataAtendimento() {
        if (dataAtendimento == null) {
            setDataAtendimento(new ZonedDateTimeFilter());
        }
        return dataAtendimento;
    }

    public void setDataAtendimento(ZonedDateTimeFilter dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public StringFilter getTitulo() {
        return titulo;
    }

    public Optional<StringFilter> optionalTitulo() {
        return Optional.ofNullable(titulo);
    }

    public StringFilter titulo() {
        if (titulo == null) {
            setTitulo(new StringFilter());
        }
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public BooleanFilter getConfidencial() {
        return confidencial;
    }

    public Optional<BooleanFilter> optionalConfidencial() {
        return Optional.ofNullable(confidencial);
    }

    public BooleanFilter confidencial() {
        if (confidencial == null) {
            setConfidencial(new BooleanFilter());
        }
        return confidencial;
    }

    public void setConfidencial(BooleanFilter confidencial) {
        this.confidencial = confidencial;
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

    public LongFilter getAgendaId() {
        return agendaId;
    }

    public Optional<LongFilter> optionalAgendaId() {
        return Optional.ofNullable(agendaId);
    }

    public LongFilter agendaId() {
        if (agendaId == null) {
            setAgendaId(new LongFilter());
        }
        return agendaId;
    }

    public void setAgendaId(LongFilter agendaId) {
        this.agendaId = agendaId;
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
        final ProntuarioCriteria that = (ProntuarioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(dataAtendimento, that.dataAtendimento) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(confidencial, that.confidencial) &&
            Objects.equals(pacienteId, that.pacienteId) &&
            Objects.equals(profissionalId, that.profissionalId) &&
            Objects.equals(agendaId, that.agendaId) &&
            Objects.equals(especialidadeId, that.especialidadeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            tipo,
            dataAtendimento,
            titulo,
            confidencial,
            pacienteId,
            profissionalId,
            agendaId,
            especialidadeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProntuarioCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalTipo().map(f -> "tipo=" + f + ", ").orElse("") +
            optionalDataAtendimento().map(f -> "dataAtendimento=" + f + ", ").orElse("") +
            optionalTitulo().map(f -> "titulo=" + f + ", ").orElse("") +
            optionalConfidencial().map(f -> "confidencial=" + f + ", ").orElse("") +
            optionalPacienteId().map(f -> "pacienteId=" + f + ", ").orElse("") +
            optionalProfissionalId().map(f -> "profissionalId=" + f + ", ").orElse("") +
            optionalAgendaId().map(f -> "agendaId=" + f + ", ").orElse("") +
            optionalEspecialidadeId().map(f -> "especialidadeId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
