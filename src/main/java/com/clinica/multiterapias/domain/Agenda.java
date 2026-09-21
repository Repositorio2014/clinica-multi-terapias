package com.clinica.multiterapias.domain;

import com.clinica.multiterapias.domain.enumeration.StatusAgendamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Agenda.
 */
@Entity
@Table(name = "agenda")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Agenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "data_hora_inicio", nullable = false)
    private ZonedDateTime dataHoraInicio;

    @NotNull
    @Column(name = "data_hora_fim", nullable = false)
    private ZonedDateTime dataHoraFim;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusAgendamento status;

    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "valor_cobrado", precision = 21, scale = 2)
    private BigDecimal valorCobrado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "agendas", "prontuarios" }, allowSetters = true)
    private Paciente paciente;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "especialidades" }, allowSetters = true)
    private Profissional profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sala sala;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "profissionais" }, allowSetters = true)
    private Especialidade especialidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Agenda id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDataHoraInicio() {
        return this.dataHoraInicio;
    }

    public Agenda dataHoraInicio(ZonedDateTime dataHoraInicio) {
        this.setDataHoraInicio(dataHoraInicio);
        return this;
    }

    public void setDataHoraInicio(ZonedDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public ZonedDateTime getDataHoraFim() {
        return this.dataHoraFim;
    }

    public Agenda dataHoraFim(ZonedDateTime dataHoraFim) {
        this.setDataHoraFim(dataHoraFim);
        return this;
    }

    public void setDataHoraFim(ZonedDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public StatusAgendamento getStatus() {
        return this.status;
    }

    public Agenda status(StatusAgendamento status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public Agenda observacoes(String observacoes) {
        this.setObservacoes(observacoes);
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public BigDecimal getValorCobrado() {
        return this.valorCobrado;
    }

    public Agenda valorCobrado(BigDecimal valorCobrado) {
        this.setValorCobrado(valorCobrado);
        return this;
    }

    public void setValorCobrado(BigDecimal valorCobrado) {
        this.valorCobrado = valorCobrado;
    }

    public Paciente getPaciente() {
        return this.paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Agenda paciente(Paciente paciente) {
        this.setPaciente(paciente);
        return this;
    }

    public Profissional getProfissional() {
        return this.profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public Agenda profissional(Profissional profissional) {
        this.setProfissional(profissional);
        return this;
    }

    public Sala getSala() {
        return this.sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Agenda sala(Sala sala) {
        this.setSala(sala);
        return this;
    }

    public Especialidade getEspecialidade() {
        return this.especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public Agenda especialidade(Especialidade especialidade) {
        this.setEspecialidade(especialidade);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agenda)) {
            return false;
        }
        return getId() != null && getId().equals(((Agenda) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agenda{" +
            "id=" + getId() +
            ", dataHoraInicio='" + getDataHoraInicio() + "'" +
            ", dataHoraFim='" + getDataHoraFim() + "'" +
            ", status='" + getStatus() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", valorCobrado=" + getValorCobrado() +
            "}";
    }
}
