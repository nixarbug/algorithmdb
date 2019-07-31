package com.algorithmdb.service;

import com.algorithmdb.domain.Implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Implementation}.
 */
public interface ImplementationService {

    /**
     * Save a implementation.
     *
     * @param implementation the entity to save.
     * @return the persisted entity.
     */
    Implementation save(Implementation implementation);

    /**
     * Get all the implementations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Implementation> findAll(Pageable pageable);


    /**
     * Get the "id" implementation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Implementation> findOne(Long id);

    /**
     * Delete the "id" implementation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
