package com.algorithmdb.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Implementation.
 */
@Entity
@Table(name = "implementation")
public class Implementation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "code", nullable = false)
    private String code;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "note")
    private String note;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @Column(name = "date_updated")
    private Instant dateUpdated;

    @ManyToOne
    @JsonIgnoreProperties("implementations")
    private Language language;

    @ManyToOne
    @JsonIgnoreProperties("implementations")
    private Algorithm algorithm;

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

    public Implementation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Implementation code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNote() {
        return note;
    }

    public Implementation note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public Implementation dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public Implementation dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Language getLanguage() {
        return language;
    }

    public Implementation language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Implementation algorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Implementation)) {
            return false;
        }
        return id != null && id.equals(((Implementation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Implementation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", note='" + getNote() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            "}";
    }
}
