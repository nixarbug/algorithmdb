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
 * A BlogEntry.
 */
@Entity
@Table(name = "blog_entry")
public class BlogEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @Column(name = "date_updated")
    private Instant dateUpdated;

    @ManyToMany
    @JoinTable(name = "blog_entry_tag",
               joinColumns = @JoinColumn(name = "blog_entry_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("blogEntries")
    private Blog blog;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public BlogEntry title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public BlogEntry content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public BlogEntry dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public BlogEntry dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public BlogEntry tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public BlogEntry addTag(Tag tag) {
        this.tags.add(tag);
        tag.getBlogEntries().add(this);
        return this;
    }

    public BlogEntry removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getBlogEntries().remove(this);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Blog getBlog() {
        return blog;
    }

    public BlogEntry blog(Blog blog) {
        this.blog = blog;
        return this;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlogEntry)) {
            return false;
        }
        return id != null && id.equals(((BlogEntry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BlogEntry{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            "}";
    }
}
