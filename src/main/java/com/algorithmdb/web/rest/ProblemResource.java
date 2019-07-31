package com.algorithmdb.web.rest;

import com.algorithmdb.domain.Problem;
import com.algorithmdb.service.ProblemService;
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
 * REST controller for managing {@link com.algorithmdb.domain.Problem}.
 */
@RestController
@RequestMapping("/api")
public class ProblemResource {

    private final Logger log = LoggerFactory.getLogger(ProblemResource.class);

    private static final String ENTITY_NAME = "problem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProblemService problemService;

    public ProblemResource(ProblemService problemService) {
        this.problemService = problemService;
    }

    /**
     * {@code POST  /problems} : Create a new problem.
     *
     * @param problem the problem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new problem, or with status {@code 400 (Bad Request)} if the problem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/problems")
    public ResponseEntity<Problem> createProblem(@Valid @RequestBody Problem problem) throws URISyntaxException {
        log.debug("REST request to save Problem : {}", problem);
        if (problem.getId() != null) {
            throw new BadRequestAlertException("A new problem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Problem result = problemService.save(problem);
        return ResponseEntity.created(new URI("/api/problems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /problems} : Updates an existing problem.
     *
     * @param problem the problem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated problem,
     * or with status {@code 400 (Bad Request)} if the problem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the problem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/problems")
    public ResponseEntity<Problem> updateProblem(@Valid @RequestBody Problem problem) throws URISyntaxException {
        log.debug("REST request to update Problem : {}", problem);
        if (problem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Problem result = problemService.save(problem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, problem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /problems} : get all the problems.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of problems in body.
     */
    @GetMapping("/problems")
    public ResponseEntity<List<Problem>> getAllProblems(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Problems");
        Page<Problem> page;
        if (eagerload) {
            page = problemService.findAllWithEagerRelationships(pageable);
        } else {
            page = problemService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /problems/:id} : get the "id" problem.
     *
     * @param id the id of the problem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the problem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/problems/{id}")
    public ResponseEntity<Problem> getProblem(@PathVariable Long id) {
        log.debug("REST request to get Problem : {}", id);
        Optional<Problem> problem = problemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(problem);
    }

    /**
     * {@code DELETE  /problems/:id} : delete the "id" problem.
     *
     * @param id the id of the problem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/problems/{id}")
    public ResponseEntity<Void> deleteProblem(@PathVariable Long id) {
        log.debug("REST request to delete Problem : {}", id);
        problemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
