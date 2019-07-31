package com.algorithmdb.service.impl;

import com.algorithmdb.service.ProblemGroupService;
import com.algorithmdb.domain.ProblemGroup;
import com.algorithmdb.repository.ProblemGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProblemGroup}.
 */
@Service
@Transactional
public class ProblemGroupServiceImpl implements ProblemGroupService {

    private final Logger log = LoggerFactory.getLogger(ProblemGroupServiceImpl.class);

    private final ProblemGroupRepository problemGroupRepository;

    public ProblemGroupServiceImpl(ProblemGroupRepository problemGroupRepository) {
        this.problemGroupRepository = problemGroupRepository;
    }

    /**
     * Save a problemGroup.
     *
     * @param problemGroup the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProblemGroup save(ProblemGroup problemGroup) {
        log.debug("Request to save ProblemGroup : {}", problemGroup);
        return problemGroupRepository.save(problemGroup);
    }

    /**
     * Get all the problemGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProblemGroup> findAll(Pageable pageable) {
        log.debug("Request to get all ProblemGroups");
        return problemGroupRepository.findAll(pageable);
    }


    /**
     * Get one problemGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProblemGroup> findOne(Long id) {
        log.debug("Request to get ProblemGroup : {}", id);
        return problemGroupRepository.findById(id);
    }

    /**
     * Delete the problemGroup by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProblemGroup : {}", id);
        problemGroupRepository.deleteById(id);
    }
}
