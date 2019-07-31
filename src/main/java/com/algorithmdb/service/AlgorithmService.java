package com.algorithmdb.service;

import com.algorithmdb.domain.Algorithm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Algorithm}.
 */
public interface AlgorithmService {

    /**
     * Save a algorithm.
     *
     * @param algorithm the entity to save.
     * @return the persisted entity.
     */
    Algorithm save(Algorithm algorithm);

    /**
     * Get all the algorithms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Algorithm> findAll(Pageable pageable);

    /**
     * Get all the algorithms with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Algorithm> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" algorithm.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Algorithm> findOne(Long id);

    /**
     * Delete the "id" algorithm.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
