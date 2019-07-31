package com.algorithmdb.service;

import com.algorithmdb.domain.BlogEntry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link BlogEntry}.
 */
public interface BlogEntryService {

    /**
     * Save a blogEntry.
     *
     * @param blogEntry the entity to save.
     * @return the persisted entity.
     */
    BlogEntry save(BlogEntry blogEntry);

    /**
     * Get all the blogEntries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BlogEntry> findAll(Pageable pageable);

    /**
     * Get all the blogEntries with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<BlogEntry> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" blogEntry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlogEntry> findOne(Long id);

    /**
     * Delete the "id" blogEntry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
