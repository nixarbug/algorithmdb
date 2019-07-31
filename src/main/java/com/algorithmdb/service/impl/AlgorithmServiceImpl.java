package com.algorithmdb.service.impl;

import com.algorithmdb.service.AlgorithmService;
import com.algorithmdb.domain.Algorithm;
import com.algorithmdb.repository.AlgorithmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Algorithm}.
 */
@Service
@Transactional
public class AlgorithmServiceImpl implements AlgorithmService {

    private final Logger log = LoggerFactory.getLogger(AlgorithmServiceImpl.class);

    private final AlgorithmRepository algorithmRepository;

    public AlgorithmServiceImpl(AlgorithmRepository algorithmRepository) {
        this.algorithmRepository = algorithmRepository;
    }

    /**
     * Save a algorithm.
     *
     * @param algorithm the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Algorithm save(Algorithm algorithm) {
        log.debug("Request to save Algorithm : {}", algorithm);
        return algorithmRepository.save(algorithm);
    }

    /**
     * Get all the algorithms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Algorithm> findAll(Pageable pageable) {
        log.debug("Request to get all Algorithms");
        return algorithmRepository.findAll(pageable);
    }

    /**
     * Get all the algorithms with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Algorithm> findAllWithEagerRelationships(Pageable pageable) {
        return algorithmRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one algorithm by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Algorithm> findOne(Long id) {
        log.debug("Request to get Algorithm : {}", id);
        return algorithmRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the algorithm by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Algorithm : {}", id);
        algorithmRepository.deleteById(id);
    }
}
