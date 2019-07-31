package com.algorithmdb.service;

import com.algorithmdb.domain.FunctionClass;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link FunctionClass}.
 */
public interface FunctionClassService {

    /**
     * Save a functionClass.
     *
     * @param functionClass the entity to save.
     * @return the persisted entity.
     */
    FunctionClass save(FunctionClass functionClass);

    /**
     * Get all the functionClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FunctionClass> findAll(Pageable pageable);


    /**
     * Get the "id" functionClass.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FunctionClass> findOne(Long id);

    /**
     * Delete the "id" functionClass.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
