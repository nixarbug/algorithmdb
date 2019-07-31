package com.algorithmdb.web.rest;

import com.algorithmdb.domain.ProblemGroup;
import com.algorithmdb.service.ProblemGroupService;
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
 * REST controller for managing {@link com.algorithmdb.domain.ProblemGroup}.
 */
@RestController
@RequestMapping("/api")
public class ProblemGroupResource {

    private final Logger log = LoggerFactory.getLogger(ProblemGroupResource.class);

    private static final String ENTITY_NAME = "problemGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProblemGroupService problemGroupService;

    public ProblemGroupResource(ProblemGroupService problemGroupService) {
        this.problemGroupService = problemGroupService;
    }

    /**
     * {@code POST  /problem-groups} : Create a new problemGroup.
     *
     * @param problemGroup the problemGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new problemGroup, or with status {@code 400 (Bad Request)} if the problemGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/problem-groups")
    public ResponseEntity<ProblemGroup> createProblemGroup(@Valid @RequestBody ProblemGroup problemGroup) throws URISyntaxException {
        log.debug("REST request to save ProblemGroup : {}", problemGroup);
        if (problemGroup.getId() != null) {
            throw new BadRequestAlertException("A new problemGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProblemGroup result = problemGroupService.save(problemGroup);
        return ResponseEntity.created(new URI("/api/problem-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /problem-groups} : Updates an existing problemGroup.
     *
     * @param problemGroup the problemGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated problemGroup,
     * or with status {@code 400 (Bad Request)} if the problemGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the problemGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/problem-groups")
    public ResponseEntity<ProblemGroup> updateProblemGroup(@Valid @RequestBody ProblemGroup problemGroup) throws URISyntaxException {
        log.debug("REST request to update ProblemGroup : {}", problemGroup);
        if (problemGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProblemGroup result = problemGroupService.save(problemGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, problemGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /problem-groups} : get all the problemGroups.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of problemGroups in body.
     */
    @GetMapping("/problem-groups")
    public ResponseEntity<List<ProblemGroup>> getAllProblemGroups(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ProblemGroups");
        Page<ProblemGroup> page = problemGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /problem-groups/:id} : get the "id" problemGroup.
     *
     * @param id the id of the problemGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the problemGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/problem-groups/{id}")
    public ResponseEntity<ProblemGroup> getProblemGroup(@PathVariable Long id) {
        log.debug("REST request to get ProblemGroup : {}", id);
        Optional<ProblemGroup> problemGroup = problemGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(problemGroup);
    }

    /**
     * {@code DELETE  /problem-groups/:id} : delete the "id" problemGroup.
     *
     * @param id the id of the problemGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/problem-groups/{id}")
    public ResponseEntity<Void> deleteProblemGroup(@PathVariable Long id) {
        log.debug("REST request to delete ProblemGroup : {}", id);
        problemGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
