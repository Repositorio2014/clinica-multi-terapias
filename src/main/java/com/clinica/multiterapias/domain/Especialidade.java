package com.clinica.multiterapias.domain;

import com.clinica.multiterapias.domain.enumeration.TipoEspecialidade;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Especialidade.
 */
@Entity
@Table(name = "especialidade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Especialidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nome", nullable = false)
    private TipoEspecialidade nome;

    @Column(name = "descricao")
    private String descricao;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "especialidades")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "especialidades" }, allowSetters = true)
    private Set<Profissional> profissionais = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Especialidade id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoEspecialidade getNome() {
        return this.nome;
    }

    public Especialidade nome(TipoEspecialidade nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(TipoEspecialidade nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Especialidade descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Profissional> getProfissionais() {
        return this.profissionais;
    }

    public void setProfissionais(Set<Profissional> profissionals) {
        if (this.profissionais != null) {
            this.profissionais.forEach(i -> i.removeEspecialidades(this));
        }
        if (profissionals != null) {
            profissionals.forEach(i -> i.addEspecialidades(this));
        }
        this.profissionais = profissionals;
    }

    public Especialidade profissionais(Set<Profissional> profissionals) {
        this.setProfissionais(profissionals);
        return this;
    }

    public Especialidade addProfissionais(Profissional profissional) {
        this.profissionais.add(profissional);
        profissional.getEspecialidades().add(this);
        return this;
    }

    public Especialidade removeProfissionais(Profissional profissional) {
        this.profissionais.remove(profissional);
        profissional.getEspecialidades().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Especialidade)) {
            return false;
        }
        return getId() != null && getId().equals(((Especialidade) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Especialidade{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
