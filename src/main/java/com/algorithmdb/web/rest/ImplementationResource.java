package com.algorithmdb.web.rest;

import com.algorithmdb.domain.Implementation;
import com.algorithmdb.service.ImplementationService;
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
 * REST controller for managing {@link com.algorithmdb.domain.Implementation}.
 */
@RestController
@RequestMapping("/api")
public class ImplementationResource {

    private final Logger log = LoggerFactory.getLogger(ImplementationResource.class);

    private static final String ENTITY_NAME = "implementation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImplementationService implementationService;

    public ImplementationResource(ImplementationService implementationService) {
        this.implementationService = implementationService;
    }

    /**
     * {@code POST  /implementations} : Create a new implementation.
     *
     * @param implementation the implementation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new implementation, or with status {@code 400 (Bad Request)} if the implementation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/implementations")
    public ResponseEntity<Implementation> createImplementation(@Valid @RequestBody Implementation implementation) throws URISyntaxException {
        log.debug("REST request to save Implementation : {}", implementation);
        if (implementation.getId() != null) {
            throw new BadRequestAlertException("A new implementation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Implementation result = implementationService.save(implementation);
        return ResponseEntity.created(new URI("/api/implementations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /implementations} : Updates an existing implementation.
     *
     * @param implementation the implementation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated implementation,
     * or with status {@code 400 (Bad Request)} if the implementation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the implementation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/implementations")
    public ResponseEntity<Implementation> updateImplementation(@Valid @RequestBody Implementation implementation) throws URISyntaxException {
        log.debug("REST request to update Implementation : {}", implementation);
        if (implementation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Implementation result = implementationService.save(implementation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, implementation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /implementations} : get all the implementations.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of implementations in body.
     */
    @GetMapping("/implementations")
    public ResponseEntity<List<Implementation>> getAllImplementations(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Implementations");
        Page<Implementation> page = implementationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /implementations/:id} : get the "id" implementation.
     *
     * @param id the id of the implementation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the implementation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/implementations/{id}")
    public ResponseEntity<Implementation> getImplementation(@PathVariable Long id) {
        log.debug("REST request to get Implementation : {}", id);
        Optional<Implementation> implementation = implementationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(implementation);
    }

    /**
     * {@code DELETE  /implementations/:id} : delete the "id" implementation.
     *
     * @param id the id of the implementation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/implementations/{id}")
    public ResponseEntity<Void> deleteImplementation(@PathVariable Long id) {
        log.debug("REST request to delete Implementation : {}", id);
        implementationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
