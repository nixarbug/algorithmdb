package com.algorithmdb.domain;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A FunctionClass.
 */
@Entity
@Table(name = "function_class")
public class FunctionClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "formula", nullable = false)
    private String formula;

    @NotNull
    @Column(name = "relative_order", nullable = false)
    private Integer relativeOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public FunctionClass name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormula() {
        return formula;
    }

    public FunctionClass formula(String formula) {
        this.formula = formula;
        return this;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Integer getRelativeOrder() {
        return relativeOrder;
    }

    public FunctionClass relativeOrder(Integer relativeOrder) {
        this.relativeOrder = relativeOrder;
        return this;
    }

    public void setRelativeOrder(Integer relativeOrder) {
        this.relativeOrder = relativeOrder;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FunctionClass)) {
            return false;
        }
        return id != null && id.equals(((FunctionClass) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FunctionClass{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", formula='" + getFormula() + "'" +
            ", relativeOrder=" + getRelativeOrder() +
            "}";
    }
}
