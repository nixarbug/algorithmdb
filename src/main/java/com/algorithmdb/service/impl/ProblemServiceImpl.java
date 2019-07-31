package com.algorithmdb.service.impl;

import com.algorithmdb.service.ProblemService;
import com.algorithmdb.domain.Problem;
import com.algorithmdb.repository.ProblemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Problem}.
 */
@Service
@Transactional
public class ProblemServiceImpl implements ProblemService {

    private final Logger log = LoggerFactory.getLogger(ProblemServiceImpl.class);

    private final ProblemRepository problemRepository;

    public ProblemServiceImpl(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    /**
     * Save a problem.
     *
     * @param problem the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Problem save(Problem problem) {
        log.debug("Request to save Problem : {}", problem);
        return problemRepository.save(problem);
    }

    /**
     * Get all the problems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Problem> findAll(Pageable pageable) {
        log.debug("Request to get all Problems");
        return problemRepository.findAll(pageable);
    }

    /**
     * Get all the problems with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Problem> findAllWithEagerRelationships(Pageable pageable) {
        return problemRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one problem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Problem> findOne(Long id) {
        log.debug("Request to get Problem : {}", id);
        return problemRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the problem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Problem : {}", id);
        problemRepository.deleteById(id);
    }
}
