package com.clinica.multiterapias.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.clinica.multiterapias.domain.Paciente} entity. This class is used
 * in {@link com.clinica.multiterapias.web.rest.PacienteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pacientes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PacienteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter cpf;

    private LocalDateFilter dataNascimento;

    private StringFilter telefone;

    private StringFilter email;

    private StringFilter nomeResponsavel;

    private StringFilter telefoneResponsavel;

    private StringFilter endereco;

    private StringFilter convenio;

    private BooleanFilter ativo;

    private LongFilter agendasId;

    private LongFilter prontuariosId;

    private Boolean distinct;

    public PacienteCriteria() {}

    public PacienteCriteria(PacienteCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.cpf = other.optionalCpf().map(StringFilter::copy).orElse(null);
        this.dataNascimento = other.optionalDataNascimento().map(LocalDateFilter::copy).orElse(null);
        this.telefone = other.optionalTelefone().map(StringFilter::copy).orElse(null);
        this.email = other.optionalEmail().map(StringFilter::copy).orElse(null);
        this.nomeResponsavel = other.optionalNomeResponsavel().map(StringFilter::copy).orElse(null);
        this.telefoneResponsavel = other.optionalTelefoneResponsavel().map(StringFilter::copy).orElse(null);
        this.endereco = other.optionalEndereco().map(StringFilter::copy).orElse(null);
        this.convenio = other.optionalConvenio().map(StringFilter::copy).orElse(null);
        this.ativo = other.optionalAtivo().map(BooleanFilter::copy).orElse(null);
        this.agendasId = other.optionalAgendasId().map(LongFilter::copy).orElse(null);
        this.prontuariosId = other.optionalProntuariosId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PacienteCriteria copy() {
        return new PacienteCriteria(this);
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

    public StringFilter getNome() {
        return nome;
    }

    public Optional<StringFilter> optionalNome() {
        return Optional.ofNullable(nome);
    }

    public StringFilter nome() {
        if (nome == null) {
            setNome(new StringFilter());
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getCpf() {
        return cpf;
    }

    public Optional<StringFilter> optionalCpf() {
        return Optional.ofNullable(cpf);
    }

    public StringFilter cpf() {
        if (cpf == null) {
            setCpf(new StringFilter());
        }
        return cpf;
    }

    public void setCpf(StringFilter cpf) {
        this.cpf = cpf;
    }

    public LocalDateFilter getDataNascimento() {
        return dataNascimento;
    }

    public Optional<LocalDateFilter> optionalDataNascimento() {
        return Optional.ofNullable(dataNascimento);
    }

    public LocalDateFilter dataNascimento() {
        if (dataNascimento == null) {
            setDataNascimento(new LocalDateFilter());
        }
        return dataNascimento;
    }

    public void setDataNascimento(LocalDateFilter dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public StringFilter getTelefone() {
        return telefone;
    }

    public Optional<StringFilter> optionalTelefone() {
        return Optional.ofNullable(telefone);
    }

    public StringFilter telefone() {
        if (telefone == null) {
            setTelefone(new StringFilter());
        }
        return telefone;
    }

    public void setTelefone(StringFilter telefone) {
        this.telefone = telefone;
    }

    public StringFilter getEmail() {
        return email;
    }

    public Optional<StringFilter> optionalEmail() {
        return Optional.ofNullable(email);
    }

    public StringFilter email() {
        if (email == null) {
            setEmail(new StringFilter());
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getNomeResponsavel() {
        return nomeResponsavel;
    }

    public Optional<StringFilter> optionalNomeResponsavel() {
        return Optional.ofNullable(nomeResponsavel);
    }

    public StringFilter nomeResponsavel() {
        if (nomeResponsavel == null) {
            setNomeResponsavel(new StringFilter());
        }
        return nomeResponsavel;
    }

    public void setNomeResponsavel(StringFilter nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public StringFilter getTelefoneResponsavel() {
        return telefoneResponsavel;
    }

    public Optional<StringFilter> optionalTelefoneResponsavel() {
        return Optional.ofNullable(telefoneResponsavel);
    }

    public StringFilter telefoneResponsavel() {
        if (telefoneResponsavel == null) {
            setTelefoneResponsavel(new StringFilter());
        }
        return telefoneResponsavel;
    }

    public void setTelefoneResponsavel(StringFilter telefoneResponsavel) {
        this.telefoneResponsavel = telefoneResponsavel;
    }

    public StringFilter getEndereco() {
        return endereco;
    }

    public Optional<StringFilter> optionalEndereco() {
        return Optional.ofNullable(endereco);
    }

    public StringFilter endereco() {
        if (endereco == null) {
            setEndereco(new StringFilter());
        }
        return endereco;
    }

    public void setEndereco(StringFilter endereco) {
        this.endereco = endereco;
    }

    public StringFilter getConvenio() {
        return convenio;
    }

    public Optional<StringFilter> optionalConvenio() {
        return Optional.ofNullable(convenio);
    }

    public StringFilter convenio() {
        if (convenio == null) {
            setConvenio(new StringFilter());
        }
        return convenio;
    }

    public void setConvenio(StringFilter convenio) {
        this.convenio = convenio;
    }

    public BooleanFilter getAtivo() {
        return ativo;
    }

    public Optional<BooleanFilter> optionalAtivo() {
        return Optional.ofNullable(ativo);
    }

    public BooleanFilter ativo() {
        if (ativo == null) {
            setAtivo(new BooleanFilter());
        }
        return ativo;
    }

    public void setAtivo(BooleanFilter ativo) {
        this.ativo = ativo;
    }

    public LongFilter getAgendasId() {
        return agendasId;
    }

    public Optional<LongFilter> optionalAgendasId() {
        return Optional.ofNullable(agendasId);
    }

    public LongFilter agendasId() {
        if (agendasId == null) {
            setAgendasId(new LongFilter());
        }
        return agendasId;
    }

    public void setAgendasId(LongFilter agendasId) {
        this.agendasId = agendasId;
    }

    public LongFilter getProntuariosId() {
        return prontuariosId;
    }

    public Optional<LongFilter> optionalProntuariosId() {
        return Optional.ofNullable(prontuariosId);
    }

    public LongFilter prontuariosId() {
        if (prontuariosId == null) {
            setProntuariosId(new LongFilter());
        }
        return prontuariosId;
    }

    public void setProntuariosId(LongFilter prontuariosId) {
        this.prontuariosId = prontuariosId;
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
        final PacienteCriteria that = (PacienteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(cpf, that.cpf) &&
            Objects.equals(dataNascimento, that.dataNascimento) &&
            Objects.equals(telefone, that.telefone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(nomeResponsavel, that.nomeResponsavel) &&
            Objects.equals(telefoneResponsavel, that.telefoneResponsavel) &&
            Objects.equals(endereco, that.endereco) &&
            Objects.equals(convenio, that.convenio) &&
            Objects.equals(ativo, that.ativo) &&
            Objects.equals(agendasId, that.agendasId) &&
            Objects.equals(prontuariosId, that.prontuariosId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nome,
            cpf,
            dataNascimento,
            telefone,
            email,
            nomeResponsavel,
            telefoneResponsavel,
            endereco,
            convenio,
            ativo,
            agendasId,
            prontuariosId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PacienteCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalCpf().map(f -> "cpf=" + f + ", ").orElse("") +
            optionalDataNascimento().map(f -> "dataNascimento=" + f + ", ").orElse("") +
            optionalTelefone().map(f -> "telefone=" + f + ", ").orElse("") +
            optionalEmail().map(f -> "email=" + f + ", ").orElse("") +
            optionalNomeResponsavel().map(f -> "nomeResponsavel=" + f + ", ").orElse("") +
            optionalTelefoneResponsavel().map(f -> "telefoneResponsavel=" + f + ", ").orElse("") +
            optionalEndereco().map(f -> "endereco=" + f + ", ").orElse("") +
            optionalConvenio().map(f -> "convenio=" + f + ", ").orElse("") +
            optionalAtivo().map(f -> "ativo=" + f + ", ").orElse("") +
            optionalAgendasId().map(f -> "agendasId=" + f + ", ").orElse("") +
            optionalProntuariosId().map(f -> "prontuariosId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
