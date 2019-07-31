package com.algorithmdb.web.rest;

import com.algorithmdb.domain.Algorithm;
import com.algorithmdb.service.AlgorithmService;
import com.algorithmdb.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.algorithmdb.domain.Algorithm}.
 */
@RestController
@RequestMapping("/api")
public class AlgorithmResource {

    private final Logger log = LoggerFactory.getLogger(AlgorithmResource.class);

    private static final String ENTITY_NAME = "algorithm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlgorithmService algorithmService;

    public AlgorithmResource(AlgorithmService algorithmService) {
        this.algorithmService = algorithmService;
    }

    /**
     * {@code POST  /algorithms} : Create a new algorithm.
     *
     * @param algorithm the algorithm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new algorithm, or with status {@code 400 (Bad Request)} if the algorithm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/algorithms")
    public ResponseEntity<Algorithm> createAlgorithm(@Valid @RequestBody Algorithm algorithm) throws URISyntaxException {
        log.debug("REST request to save Algorithm : {}", algorithm);
        if (algorithm.getId() != null) {
            throw new BadRequestAlertException("A new algorithm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Algorithm result = algorithmService.save(algorithm);
        return ResponseEntity.created(new URI("/api/algorithms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /algorithms} : Updates an existing algorithm.
     *
     * @param algorithm the algorithm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated algorithm,
     * or with status {@code 400 (Bad Request)} if the algorithm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the algorithm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/algorithms")
    public ResponseEntity<Algorithm> updateAlgorithm(@Valid @RequestBody Algorithm algorithm) throws URISyntaxException {
        log.debug("REST request to update Algorithm : {}", algorithm);
        if (algorithm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Algorithm result = algorithmService.save(algorithm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, algorithm.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /algorithms} : get all the algorithms.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of algorithms in body.
     */
    @GetMapping("/algorithms")
    public ResponseEntity<List<Algorithm>> getAllAlgorithms(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Algorithms");
        Page<Algorithm> page;
        if (eagerload) {
            page = algorithmService.findAllWithEagerRelationships(pageable);
        } else {
            page = algorithmService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /algorithms/:id} : get the "id" algorithm.
     *
     * @param id the id of the algorithm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the algorithm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/algorithms/{id}")
    public ResponseEntity<Algorithm> getAlgorithm(@PathVariable Long id) {
        log.debug("REST request to get Algorithm : {}", id);
        Optional<Algorithm> algorithm = algorithmService.findOne(id);
        return ResponseUtil.wrapOrNotFound(algorithm);
    }

    /**
     * {@code DELETE  /algorithms/:id} : delete the "id" algorithm.
     *
     * @param id the id of the algorithm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/algorithms/{id}")
    public ResponseEntity<Void> deleteAlgorithm(@PathVariable Long id) {
        log.debug("REST request to delete Algorithm : {}", id);
        algorithmService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
