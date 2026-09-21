package com.clinica.multiterapias.service.dto;

import com.clinica.multiterapias.domain.enumeration.StatusAgendamento;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.clinica.multiterapias.domain.Agenda} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AgendaDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime dataHoraInicio;

    @NotNull
    private ZonedDateTime dataHoraFim;

    @NotNull
    private StatusAgendamento status;

    private String observacoes;

    private BigDecimal valorCobrado;

    @NotNull
    private PacienteDTO paciente;

    @NotNull
    private ProfissionalDTO profissional;

    private SalaDTO sala;

    @NotNull
    private EspecialidadeDTO especialidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(ZonedDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public ZonedDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(ZonedDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public BigDecimal getValorCobrado() {
        return valorCobrado;
    }

    public void setValorCobrado(BigDecimal valorCobrado) {
        this.valorCobrado = valorCobrado;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDTO paciente) {
        this.paciente = paciente;
    }

    public ProfissionalDTO getProfissional() {
        return profissional;
    }

    public void setProfissional(ProfissionalDTO profissional) {
        this.profissional = profissional;
    }

    public SalaDTO getSala() {
        return sala;
    }

    public void setSala(SalaDTO sala) {
        this.sala = sala;
    }

    public EspecialidadeDTO getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(EspecialidadeDTO especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgendaDTO)) {
            return false;
        }

        AgendaDTO agendaDTO = (AgendaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, agendaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgendaDTO{" +
            "id=" + getId() +
            ", dataHoraInicio='" + getDataHoraInicio() + "'" +
            ", dataHoraFim='" + getDataHoraFim() + "'" +
            ", status='" + getStatus() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", valorCobrado=" + getValorCobrado() +
            ", paciente=" + getPaciente() +
            ", profissional=" + getProfissional() +
            ", sala=" + getSala() +
            ", especialidade=" + getEspecialidade() +
            "}";
    }
}
