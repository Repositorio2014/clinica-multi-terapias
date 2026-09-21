package com.clinica.multiterapias.service.dto;

import com.clinica.multiterapias.domain.enumeration.TipoProntuario;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.clinica.multiterapias.domain.Prontuario} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProntuarioDTO implements Serializable {

    private Long id;

    @NotNull
    private TipoProntuario tipo;

    @NotNull
    private ZonedDateTime dataAtendimento;

    @NotNull
    private String titulo;

    @Lob
    private String conteudo;

    @NotNull
    private Boolean confidencial;

    @NotNull
    private PacienteDTO paciente;

    @NotNull
    private ProfissionalDTO profissional;

    private AgendaDTO agenda;

    @NotNull
    private EspecialidadeDTO especialidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoProntuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoProntuario tipo) {
        this.tipo = tipo;
    }

    public ZonedDateTime getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(ZonedDateTime dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Boolean getConfidencial() {
        return confidencial;
    }

    public void setConfidencial(Boolean confidencial) {
        this.confidencial = confidencial;
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

    public AgendaDTO getAgenda() {
        return agenda;
    }

    public void setAgenda(AgendaDTO agenda) {
        this.agenda = agenda;
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
        if (!(o instanceof ProntuarioDTO)) {
            return false;
        }

        ProntuarioDTO prontuarioDTO = (ProntuarioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, prontuarioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProntuarioDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", dataAtendimento='" + getDataAtendimento() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", conteudo='" + getConteudo() + "'" +
            ", confidencial='" + getConfidencial() + "'" +
            ", paciente=" + getPaciente() +
            ", profissional=" + getProfissional() +
            ", agenda=" + getAgenda() +
            ", especialidade=" + getEspecialidade() +
            "}";
    }
}
