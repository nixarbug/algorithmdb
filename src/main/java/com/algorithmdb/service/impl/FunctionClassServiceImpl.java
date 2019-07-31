package com.algorithmdb.service.impl;

import com.algorithmdb.service.FunctionClassService;
import com.algorithmdb.domain.FunctionClass;
import com.algorithmdb.repository.FunctionClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FunctionClass}.
 */
@Service
@Transactional
public class FunctionClassServiceImpl implements FunctionClassService {

    private final Logger log = LoggerFactory.getLogger(FunctionClassServiceImpl.class);

    private final FunctionClassRepository functionClassRepository;

    public FunctionClassServiceImpl(FunctionClassRepository functionClassRepository) {
        this.functionClassRepository = functionClassRepository;
    }

    /**
     * Save a functionClass.
     *
     * @param functionClass the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FunctionClass save(FunctionClass functionClass) {
        log.debug("Request to save FunctionClass : {}", functionClass);
        return functionClassRepository.save(functionClass);
    }

    /**
     * Get all the functionClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FunctionClass> findAll(Pageable pageable) {
        log.debug("Request to get all FunctionClasses");
        return functionClassRepository.findAll(pageable);
    }


    /**
     * Get one functionClass by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FunctionClass> findOne(Long id) {
        log.debug("Request to get FunctionClass : {}", id);
        return functionClassRepository.findById(id);
    }

    /**
     * Delete the functionClass by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FunctionClass : {}", id);
        functionClassRepository.deleteById(id);
    }
}
