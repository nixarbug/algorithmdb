package com.algorithmdb.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Problem.
 */
@Entity
@Table(name = "problem")
public class Problem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 500)
    @Column(name = "name", length = 500, nullable = false)
    private String name;

    @NotNull
    @Size(max = 1000)
    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @Column(name = "date_updated")
    private Instant dateUpdated;

    @ManyToMany
    @JoinTable(name = "problem_problem_group",
               joinColumns = @JoinColumn(name = "problem_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "problem_group_id", referencedColumnName = "id"))
    private Set<ProblemGroup> problemGroups = new HashSet<>();

    @ManyToMany(mappedBy = "problems")
    @JsonIgnore
    private Set<Algorithm> algorithms = new HashSet<>();

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

    public Problem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Problem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public Problem dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public Problem dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Set<ProblemGroup> getProblemGroups() {
        return problemGroups;
    }

    public Problem problemGroups(Set<ProblemGroup> problemGroups) {
        this.problemGroups = problemGroups;
        return this;
    }

    public Problem addProblemGroup(ProblemGroup problemGroup) {
        this.problemGroups.add(problemGroup);
        problemGroup.getProblems().add(this);
        return this;
    }

    public Problem removeProblemGroup(ProblemGroup problemGroup) {
        this.problemGroups.remove(problemGroup);
        problemGroup.getProblems().remove(this);
        return this;
    }

    public void setProblemGroups(Set<ProblemGroup> problemGroups) {
        this.problemGroups = problemGroups;
    }

    public Set<Algorithm> getAlgorithms() {
        return algorithms;
    }

    public Problem algorithms(Set<Algorithm> algorithms) {
        this.algorithms = algorithms;
        return this;
    }

    public Problem addAlgorithm(Algorithm algorithm) {
        this.algorithms.add(algorithm);
        algorithm.getProblems().add(this);
        return this;
    }

    public Problem removeAlgorithm(Algorithm algorithm) {
        this.algorithms.remove(algorithm);
        algorithm.getProblems().remove(this);
        return this;
    }

    public void setAlgorithms(Set<Algorithm> algorithms) {
        this.algorithms = algorithms;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Problem)) {
            return false;
        }
        return id != null && id.equals(((Problem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Problem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            "}";
    }
}
