package com.algorithmdb.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Algorithm.
 */
@Entity
@Table(name = "algorithm")
public class Algorithm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 250)
    @Column(name = "name", length = 250, nullable = false)
    private String name;

    @NotNull
    @Size(min = 2, max = 500)
    @Column(name = "input", length = 500, nullable = false)
    private String input;

    @NotNull
    @Size(min = 2, max = 500)
    @Column(name = "output", length = 500, nullable = false)
    private String output;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "idea")
    private String idea;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "real_life_use")
    private String realLifeUse;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "pseudocode")
    private String pseudocode;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "flowchart")
    private String flowchart;

    @Lob
    @Column(name = "flowchart_image")
    private byte[] flowchartImage;

    @Column(name = "flowchart_image_content_type")
    private String flowchartImageContentType;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "complexity_analysis")
    private String complexityAnalysis;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "correctness_proof")
    private String correctnessProof;

    @Column(name = "average_stars")
    private Float averageStars;

    @Column(name = "total_favs")
    private Integer totalFavs;

    @Column(name = "weighted_rating")
    private Float weightedRating;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @Column(name = "date_updated")
    private Instant dateUpdated;

    @OneToMany(mappedBy = "algorithm")
    private Set<Implementation> implementations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("algorithms")
    private FunctionClass worstCaseComplexity;

    @ManyToOne
    @JsonIgnoreProperties("algorithms")
    private FunctionClass averageCaseComplexity;

    @ManyToOne
    @JsonIgnoreProperties("algorithms")
    private FunctionClass bestCaseComplexity;

    @ManyToMany
    @JoinTable(name = "algorithm_author",
               joinColumns = @JoinColumn(name = "algorithm_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
    private Set<Author> authors = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "algorithm_tag",
               joinColumns = @JoinColumn(name = "algorithm_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "algorithm_problem",
               joinColumns = @JoinColumn(name = "algorithm_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "problem_id", referencedColumnName = "id"))
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

    public Algorithm name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInput() {
        return input;
    }

    public Algorithm input(String input) {
        this.input = input;
        return this;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public Algorithm output(String output) {
        this.output = output;
        return this;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getIdea() {
        return idea;
    }

    public Algorithm idea(String idea) {
        this.idea = idea;
        return this;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }

    public String getDescription() {
        return description;
    }

    public Algorithm description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRealLifeUse() {
        return realLifeUse;
    }

    public Algorithm realLifeUse(String realLifeUse) {
        this.realLifeUse = realLifeUse;
        return this;
    }

    public void setRealLifeUse(String realLifeUse) {
        this.realLifeUse = realLifeUse;
    }

    public String getPseudocode() {
        return pseudocode;
    }

    public Algorithm pseudocode(String pseudocode) {
        this.pseudocode = pseudocode;
        return this;
    }

    public void setPseudocode(String pseudocode) {
        this.pseudocode = pseudocode;
    }

    public String getFlowchart() {
        return flowchart;
    }

    public Algorithm flowchart(String flowchart) {
        this.flowchart = flowchart;
        return this;
    }

    public void setFlowchart(String flowchart) {
        this.flowchart = flowchart;
    }

    public byte[] getFlowchartImage() {
        return flowchartImage;
    }

    public Algorithm flowchartImage(byte[] flowchartImage) {
        this.flowchartImage = flowchartImage;
        return this;
    }

    public void setFlowchartImage(byte[] flowchartImage) {
        this.flowchartImage = flowchartImage;
    }

    public String getFlowchartImageContentType() {
        return flowchartImageContentType;
    }

    public Algorithm flowchartImageContentType(String flowchartImageContentType) {
        this.flowchartImageContentType = flowchartImageContentType;
        return this;
    }

    public void setFlowchartImageContentType(String flowchartImageContentType) {
        this.flowchartImageContentType = flowchartImageContentType;
    }

    public String getComplexityAnalysis() {
        return complexityAnalysis;
    }

    public Algorithm complexityAnalysis(String complexityAnalysis) {
        this.complexityAnalysis = complexityAnalysis;
        return this;
    }

    public void setComplexityAnalysis(String complexityAnalysis) {
        this.complexityAnalysis = complexityAnalysis;
    }

    public String getCorrectnessProof() {
        return correctnessProof;
    }

    public Algorithm correctnessProof(String correctnessProof) {
        this.correctnessProof = correctnessProof;
        return this;
    }

    public void setCorrectnessProof(String correctnessProof) {
        this.correctnessProof = correctnessProof;
    }

    public Float getAverageStars() {
        return averageStars;
    }

    public Algorithm averageStars(Float averageStars) {
        this.averageStars = averageStars;
        return this;
    }

    public void setAverageStars(Float averageStars) {
        this.averageStars = averageStars;
    }

    public Integer getTotalFavs() {
        return totalFavs;
    }

    public Algorithm totalFavs(Integer totalFavs) {
        this.totalFavs = totalFavs;
        return this;
    }

    public void setTotalFavs(Integer totalFavs) {
        this.totalFavs = totalFavs;
    }

    public Float getWeightedRating() {
        return weightedRating;
    }

    public Algorithm weightedRating(Float weightedRating) {
        this.weightedRating = weightedRating;
        return this;
    }

    public void setWeightedRating(Float weightedRating) {
        this.weightedRating = weightedRating;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public Algorithm dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public Algorithm dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Set<Implementation> getImplementations() {
        return implementations;
    }

    public Algorithm implementations(Set<Implementation> implementations) {
        this.implementations = implementations;
        return this;
    }

    public Algorithm addImplementation(Implementation implementation) {
        this.implementations.add(implementation);
        implementation.setAlgorithm(this);
        return this;
    }

    public Algorithm removeImplementation(Implementation implementation) {
        this.implementations.remove(implementation);
        implementation.setAlgorithm(null);
        return this;
    }

    public void setImplementations(Set<Implementation> implementations) {
        this.implementations = implementations;
    }

    public FunctionClass getWorstCaseComplexity() {
        return worstCaseComplexity;
    }

    public Algorithm worstCaseComplexity(FunctionClass functionClass) {
        this.worstCaseComplexity = functionClass;
        return this;
    }

    public void setWorstCaseComplexity(FunctionClass functionClass) {
        this.worstCaseComplexity = functionClass;
    }

    public FunctionClass getAverageCaseComplexity() {
        return averageCaseComplexity;
    }

    public Algorithm averageCaseComplexity(FunctionClass functionClass) {
        this.averageCaseComplexity = functionClass;
        return this;
    }

    public void setAverageCaseComplexity(FunctionClass functionClass) {
        this.averageCaseComplexity = functionClass;
    }

    public FunctionClass getBestCaseComplexity() {
        return bestCaseComplexity;
    }

    public Algorithm bestCaseComplexity(FunctionClass functionClass) {
        this.bestCaseComplexity = functionClass;
        return this;
    }

    public void setBestCaseComplexity(FunctionClass functionClass) {
        this.bestCaseComplexity = functionClass;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public Algorithm authors(Set<Author> authors) {
        this.authors = authors;
        return this;
    }

    public Algorithm addAuthor(Author author) {
        this.authors.add(author);
        author.getAlgorithms().add(this);
        return this;
    }

    public Algorithm removeAuthor(Author author) {
        this.authors.remove(author);
        author.getAlgorithms().remove(this);
        return this;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Algorithm tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Algorithm addTag(Tag tag) {
        this.tags.add(tag);
        tag.getAlgorithms().add(this);
        return this;
    }

    public Algorithm removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getAlgorithms().remove(this);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Problem> getProblems() {
        return problems;
    }

    public Algorithm problems(Set<Problem> problems) {
        this.problems = problems;
        return this;
    }

    public Algorithm addProblem(Problem problem) {
        this.problems.add(problem);
        problem.getAlgorithms().add(this);
        return this;
    }

    public Algorithm removeProblem(Problem problem) {
        this.problems.remove(problem);
        problem.getAlgorithms().remove(this);
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
        if (!(o instanceof Algorithm)) {
            return false;
        }
        return id != null && id.equals(((Algorithm) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Algorithm{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", input='" + getInput() + "'" +
            ", output='" + getOutput() + "'" +
            ", idea='" + getIdea() + "'" +
            ", description='" + getDescription() + "'" +
            ", realLifeUse='" + getRealLifeUse() + "'" +
            ", pseudocode='" + getPseudocode() + "'" +
            ", flowchart='" + getFlowchart() + "'" +
            ", flowchartImage='" + getFlowchartImage() + "'" +
            ", flowchartImageContentType='" + getFlowchartImageContentType() + "'" +
            ", complexityAnalysis='" + getComplexityAnalysis() + "'" +
            ", correctnessProof='" + getCorrectnessProof() + "'" +
            ", averageStars=" + getAverageStars() +
            ", totalFavs=" + getTotalFavs() +
            ", weightedRating=" + getWeightedRating() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            "}";
    }
}
