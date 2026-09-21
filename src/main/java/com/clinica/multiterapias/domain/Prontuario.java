package com.clinica.multiterapias.domain;

import com.clinica.multiterapias.domain.enumeration.TipoProntuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Prontuario.
 */
@Entity
@Table(name = "prontuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Prontuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoProntuario tipo;

    @NotNull
    @Column(name = "data_atendimento", nullable = false)
    private ZonedDateTime dataAtendimento;

    @NotNull
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Lob
    @Column(name = "conteudo", nullable = false)
    private String conteudo;

    @NotNull
    @Column(name = "confidencial", nullable = false)
    private Boolean confidencial;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "agendas", "prontuarios" }, allowSetters = true)
    private Paciente paciente;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "especialidades" }, allowSetters = true)
    private Profissional profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "paciente", "profissional", "sala", "especialidade" }, allowSetters = true)
    private Agenda agenda;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "profissionais" }, allowSetters = true)
    private Especialidade especialidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Prontuario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoProntuario getTipo() {
        return this.tipo;
    }

    public Prontuario tipo(TipoProntuario tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(TipoProntuario tipo) {
        this.tipo = tipo;
    }

    public ZonedDateTime getDataAtendimento() {
        return this.dataAtendimento;
    }

    public Prontuario dataAtendimento(ZonedDateTime dataAtendimento) {
        this.setDataAtendimento(dataAtendimento);
        return this;
    }

    public void setDataAtendimento(ZonedDateTime dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Prontuario titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return this.conteudo;
    }

    public Prontuario conteudo(String conteudo) {
        this.setConteudo(conteudo);
        return this;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Boolean getConfidencial() {
        return this.confidencial;
    }

    public Prontuario confidencial(Boolean confidencial) {
        this.setConfidencial(confidencial);
        return this;
    }

    public void setConfidencial(Boolean confidencial) {
        this.confidencial = confidencial;
    }

    public Paciente getPaciente() {
        return this.paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Prontuario paciente(Paciente paciente) {
        this.setPaciente(paciente);
        return this;
    }

    public Profissional getProfissional() {
        return this.profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public Prontuario profissional(Profissional profissional) {
        this.setProfissional(profissional);
        return this;
    }

    public Agenda getAgenda() {
        return this.agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public Prontuario agenda(Agenda agenda) {
        this.setAgenda(agenda);
        return this;
    }

    public Especialidade getEspecialidade() {
        return this.especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public Prontuario especialidade(Especialidade especialidade) {
        this.setEspecialidade(especialidade);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prontuario)) {
            return false;
        }
        return getId() != null && getId().equals(((Prontuario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prontuario{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", dataAtendimento='" + getDataAtendimento() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", conteudo='" + getConteudo() + "'" +
            ", confidencial='" + getConfidencial() + "'" +
            "}";
    }
}
