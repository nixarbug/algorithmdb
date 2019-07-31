package com.algorithmdb.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProblemGroup.
 */
@Entity
@Table(name = "problem_group")
public class ProblemGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 500)
    @Column(name = "name", length = 500, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "problemGroups")
    @JsonIgnore
    private Set<Problem> problems = new HashSet<>();

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

    public ProblemGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Problem> getProblems() {
        return problems;
    }

    public ProblemGroup problems(Set<Problem> problems) {
        this.problems = problems;
        return this;
    }

    public ProblemGroup addProblem(Problem problem) {
        this.problems.add(problem);
        problem.getProblemGroups().add(this);
        return this;
    }

    public ProblemGroup removeProblem(Problem problem) {
        this.problems.remove(problem);
        problem.getProblemGroups().remove(this);
        return this;
    }

    public void setProblems(Set<Problem> problems) {
        this.problems = problems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProblemGroup)) {
            return false;
        }
        return id != null && id.equals(((ProblemGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProblemGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
