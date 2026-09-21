package com.clinica.multiterapias.service.dto;

import com.clinica.multiterapias.domain.enumeration.TipoEspecialidade;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.clinica.multiterapias.domain.Especialidade} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EspecialidadeDTO implements Serializable {

    private Long id;

    @NotNull
    private TipoEspecialidade nome;

    private String descricao;

    private Set<ProfissionalDTO> profissionais = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoEspecialidade getNome() {
        return nome;
    }

    public void setNome(TipoEspecialidade nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<ProfissionalDTO> getProfissionais() {
        return profissionais;
    }

    public void setProfissionais(Set<ProfissionalDTO> profissionais) {
        this.profissionais = profissionais;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EspecialidadeDTO)) {
            return false;
        }

        EspecialidadeDTO especialidadeDTO = (EspecialidadeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, especialidadeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EspecialidadeDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", profissionais=" + getProfissionais() +
            "}";
    }
}
