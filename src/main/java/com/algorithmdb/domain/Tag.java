package com.algorithmdb.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.algorithmdb.domain.enumeration.TagType;

/**
 * A Tag.
 */
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

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
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TagType type;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<Algorithm> algorithms = new HashSet<>();

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<BlogEntry> blogEntries = new HashSet<>();

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

    public Tag name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TagType getType() {
        return type;
    }

    public Tag type(TagType type) {
        this.type = type;
        return this;
    }

    public void setType(TagType type) {
        this.type = type;
    }

    public Set<Algorithm> getAlgorithms() {
        return algorithms;
    }

    public Tag algorithms(Set<Algorithm> algorithms) {
        this.algorithms = algorithms;
        return this;
    }

    public Tag addAlgorithm(Algorithm algorithm) {
        this.algorithms.add(algorithm);
        algorithm.getTags().add(this);
        return this;
    }

    public Tag removeAlgorithm(Algorithm algorithm) {
        this.algorithms.remove(algorithm);
        algorithm.getTags().remove(this);
        return this;
    }

    public void setAlgorithms(Set<Algorithm> algorithms) {
        this.algorithms = algorithms;
    }

    public Set<BlogEntry> getBlogEntries() {
        return blogEntries;
    }

    public Tag blogEntries(Set<BlogEntry> blogEntries) {
        this.blogEntries = blogEntries;
        return this;
    }

    public Tag addBlogEntry(BlogEntry blogEntry) {
        this.blogEntries.add(blogEntry);
        blogEntry.getTags().add(this);
        return this;
    }

    public Tag removeBlogEntry(BlogEntry blogEntry) {
        this.blogEntries.remove(blogEntry);
        blogEntry.getTags().remove(this);
        return this;
    }

    public void setBlogEntries(Set<BlogEntry> blogEntries) {
        this.blogEntries = blogEntries;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        return id != null && id.equals(((Tag) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tag{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
