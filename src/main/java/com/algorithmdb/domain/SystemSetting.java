package com.algorithmdb.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.algorithmdb.domain.enumeration.SystemSettingKey;

/**
 * A SystemSetting.
 */
@Entity
@Table(name = "system_setting")
public class SystemSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "key", nullable = false)
    private SystemSettingKey key;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "value", length = 100, nullable = false)
    private String value;

    @NotNull
    @Size(min = 2, max = 500)
    @Column(name = "description", length = 500, nullable = false)
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SystemSettingKey getKey() {
        return key;
    }

    public SystemSetting key(SystemSettingKey key) {
        this.key = key;
        return this;
    }

    public void setKey(SystemSettingKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public SystemSetting value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public SystemSetting description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemSetting)) {
            return false;
        }
        return id != null && id.equals(((SystemSetting) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SystemSetting{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
