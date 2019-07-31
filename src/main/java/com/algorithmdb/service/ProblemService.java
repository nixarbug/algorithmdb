package com.algorithmdb.service;

import com.algorithmdb.domain.Problem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Problem}.
 */
public interface ProblemService {

    /**
     * Save a problem.
     *
     * @param problem the entity to save.
     * @return the persisted entity.
     */
    Problem save(Problem problem);

    /**
     * Get all the problems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Problem> findAll(Pageable pageable);

    /**
     * Get all the problems with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Problem> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" problem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Problem> findOne(Long id);

    /**
     * Delete the "id" problem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
