package com.clinica.multiterapias.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.clinica.multiterapias.domain.Profissional} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProfissionalDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String cpf;

    @NotNull
    private String registroConselho;

    @NotNull
    private String telefone;

    @NotNull
    private String email;

    @NotNull
    private Boolean ativo;

    private BigDecimal valorSessao;

    private Set<EspecialidadeDTO> especialidades = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRegistroConselho() {
        return registroConselho;
    }

    public void setRegistroConselho(String registroConselho) {
        this.registroConselho = registroConselho;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public BigDecimal getValorSessao() {
        return valorSessao;
    }

    public void setValorSessao(BigDecimal valorSessao) {
        this.valorSessao = valorSessao;
    }

    public Set<EspecialidadeDTO> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(Set<EspecialidadeDTO> especialidades) {
        this.especialidades = especialidades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfissionalDTO)) {
            return false;
        }

        ProfissionalDTO profissionalDTO = (ProfissionalDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, profissionalDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfissionalDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", registroConselho='" + getRegistroConselho() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", email='" + getEmail() + "'" +
            ", ativo='" + getAtivo() + "'" +
            ", valorSessao=" + getValorSessao() +
            ", especialidades=" + getEspecialidades() +
            "}";
    }
}
