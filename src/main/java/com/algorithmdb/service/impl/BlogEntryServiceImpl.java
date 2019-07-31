package com.algorithmdb.service.impl;

import com.algorithmdb.service.BlogEntryService;
import com.algorithmdb.domain.BlogEntry;
import com.algorithmdb.repository.BlogEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BlogEntry}.
 */
@Service
@Transactional
public class BlogEntryServiceImpl implements BlogEntryService {

    private final Logger log = LoggerFactory.getLogger(BlogEntryServiceImpl.class);

    private final BlogEntryRepository blogEntryRepository;

    public BlogEntryServiceImpl(BlogEntryRepository blogEntryRepository) {
        this.blogEntryRepository = blogEntryRepository;
    }

    /**
     * Save a blogEntry.
     *
     * @param blogEntry the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BlogEntry save(BlogEntry blogEntry) {
        log.debug("Request to save BlogEntry : {}", blogEntry);
        return blogEntryRepository.save(blogEntry);
    }

    /**
     * Get all the blogEntries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BlogEntry> findAll(Pageable pageable) {
        log.debug("Request to get all BlogEntries");
        return blogEntryRepository.findAll(pageable);
    }

    /**
     * Get all the blogEntries with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<BlogEntry> findAllWithEagerRelationships(Pageable pageable) {
        return blogEntryRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one blogEntry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BlogEntry> findOne(Long id) {
        log.debug("Request to get BlogEntry : {}", id);
        return blogEntryRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the blogEntry by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BlogEntry : {}", id);
        blogEntryRepository.deleteById(id);
    }
}
