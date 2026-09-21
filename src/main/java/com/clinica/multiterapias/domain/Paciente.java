package com.clinica.multiterapias.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Paciente.
 */
@Entity
@Table(name = "paciente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotNull
    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "email")
    private String email;

    @Column(name = "nome_responsavel")
    private String nomeResponsavel;

    @Column(name = "telefone_responsavel")
    private String telefoneResponsavel;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "convenio")
    private String convenio;

    @Lob
    @Column(name = "observacoes")
    private String observacoes;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paciente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "paciente", "profissional", "sala", "especialidade" }, allowSetters = true)
    private Set<Agenda> agendas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paciente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "paciente", "profissional", "agenda", "especialidade" }, allowSetters = true)
    private Set<Prontuario> prontuarios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Paciente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Paciente nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Paciente cpf(String cpf) {
        this.setCpf(cpf);
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public Paciente dataNascimento(LocalDate dataNascimento) {
        this.setDataNascimento(dataNascimento);
        return this;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Paciente telefone(String telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return this.email;
    }

    public Paciente email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomeResponsavel() {
        return this.nomeResponsavel;
    }

    public Paciente nomeResponsavel(String nomeResponsavel) {
        this.setNomeResponsavel(nomeResponsavel);
        return this;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getTelefoneResponsavel() {
        return this.telefoneResponsavel;
    }

    public Paciente telefoneResponsavel(String telefoneResponsavel) {
        this.setTelefoneResponsavel(telefoneResponsavel);
        return this;
    }

    public void setTelefoneResponsavel(String telefoneResponsavel) {
        this.telefoneResponsavel = telefoneResponsavel;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public Paciente endereco(String endereco) {
        this.setEndereco(endereco);
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getConvenio() {
        return this.convenio;
    }

    public Paciente convenio(String convenio) {
        this.setConvenio(convenio);
        return this;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public Paciente observacoes(String observacoes) {
        this.setObservacoes(observacoes);
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public Paciente ativo(Boolean ativo) {
        this.setAtivo(ativo);
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Set<Agenda> getAgendas() {
        return this.agendas;
    }

    public void setAgendas(Set<Agenda> agenda) {
        if (this.agendas != null) {
            this.agendas.forEach(i -> i.setPaciente(null));
        }
        if (agenda != null) {
            agenda.forEach(i -> i.setPaciente(this));
        }
        this.agendas = agenda;
    }

    public Paciente agendas(Set<Agenda> agenda) {
        this.setAgendas(agenda);
        return this;
    }

    public Paciente addAgendas(Agenda agenda) {
        this.agendas.add(agenda);
        agenda.setPaciente(this);
        return this;
    }

    public Paciente removeAgendas(Agenda agenda) {
        this.agendas.remove(agenda);
        agenda.setPaciente(null);
        return this;
    }

    public Set<Prontuario> getProntuarios() {
        return this.prontuarios;
    }

    public void setProntuarios(Set<Prontuario> prontuarios) {
        if (this.prontuarios != null) {
            this.prontuarios.forEach(i -> i.setPaciente(null));
        }
        if (prontuarios != null) {
            prontuarios.forEach(i -> i.setPaciente(this));
        }
        this.prontuarios = prontuarios;
    }

    public Paciente prontuarios(Set<Prontuario> prontuarios) {
        this.setProntuarios(prontuarios);
        return this;
    }

    public Paciente addProntuarios(Prontuario prontuario) {
        this.prontuarios.add(prontuario);
        prontuario.setPaciente(this);
        return this;
    }

    public Paciente removeProntuarios(Prontuario prontuario) {
        this.prontuarios.remove(prontuario);
        prontuario.setPaciente(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paciente)) {
            return false;
        }
        return getId() != null && getId().equals(((Paciente) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paciente{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", email='" + getEmail() + "'" +
            ", nomeResponsavel='" + getNomeResponsavel() + "'" +
            ", telefoneResponsavel='" + getTelefoneResponsavel() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", convenio='" + getConvenio() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", ativo='" + getAtivo() + "'" +
            "}";
    }
}
