package com.algorithmdb.service.impl;

import com.algorithmdb.service.ImplementationService;
import com.algorithmdb.domain.Implementation;
import com.algorithmdb.repository.ImplementationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Implementation}.
 */
@Service
@Transactional
public class ImplementationServiceImpl implements ImplementationService {

    private final Logger log = LoggerFactory.getLogger(ImplementationServiceImpl.class);

    private final ImplementationRepository implementationRepository;

    public ImplementationServiceImpl(ImplementationRepository implementationRepository) {
        this.implementationRepository = implementationRepository;
    }

    /**
     * Save a implementation.
     *
     * @param implementation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Implementation save(Implementation implementation) {
        log.debug("Request to save Implementation : {}", implementation);
        return implementationRepository.save(implementation);
    }

    /**
     * Get all the implementations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Implementation> findAll(Pageable pageable) {
        log.debug("Request to get all Implementations");
        return implementationRepository.findAll(pageable);
    }


    /**
     * Get one implementation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Implementation> findOne(Long id) {
        log.debug("Request to get Implementation : {}", id);
        return implementationRepository.findById(id);
    }

    /**
     * Delete the implementation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Implementation : {}", id);
        implementationRepository.deleteById(id);
    }
}
