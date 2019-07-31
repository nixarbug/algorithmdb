package com.algorithmdb.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Blog.
 */
@Entity
@Table(name = "blog")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 150)
    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @NotNull
    @Size(min = 2)
    @Column(name = "handle", nullable = false)
    private String handle;

    @OneToMany(mappedBy = "blog")
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

    public Blog name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandle() {
        return handle;
    }

    public Blog handle(String handle) {
        this.handle = handle;
        return this;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Set<BlogEntry> getBlogEntries() {
        return blogEntries;
    }

    public Blog blogEntries(Set<BlogEntry> blogEntries) {
        this.blogEntries = blogEntries;
        return this;
    }

    public Blog addBlogEntry(BlogEntry blogEntry) {
        this.blogEntries.add(blogEntry);
        blogEntry.setBlog(this);
        return this;
    }

    public Blog removeBlogEntry(BlogEntry blogEntry) {
        this.blogEntries.remove(blogEntry);
        blogEntry.setBlog(null);
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
        if (!(o instanceof Blog)) {
            return false;
        }
        return id != null && id.equals(((Blog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Blog{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", handle='" + getHandle() + "'" +
            "}";
    }
}
