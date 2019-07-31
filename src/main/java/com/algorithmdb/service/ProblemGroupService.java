package com.algorithmdb.service;

import com.algorithmdb.domain.ProblemGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ProblemGroup}.
 */
public interface ProblemGroupService {

    /**
     * Save a problemGroup.
     *
     * @param problemGroup the entity to save.
     * @return the persisted entity.
     */
    ProblemGroup save(ProblemGroup problemGroup);

    /**
     * Get all the problemGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProblemGroup> findAll(Pageable pageable);


    /**
     * Get the "id" problemGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProblemGroup> findOne(Long id);

    /**
     * Delete the "id" problemGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
