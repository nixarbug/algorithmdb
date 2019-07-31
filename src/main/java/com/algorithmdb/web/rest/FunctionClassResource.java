package com.algorithmdb.web.rest;

import com.algorithmdb.domain.FunctionClass;
import com.algorithmdb.service.FunctionClassService;
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
 * REST controller for managing {@link com.algorithmdb.domain.FunctionClass}.
 */
@RestController
@RequestMapping("/api")
public class FunctionClassResource {

    private final Logger log = LoggerFactory.getLogger(FunctionClassResource.class);

    private static final String ENTITY_NAME = "functionClass";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FunctionClassService functionClassService;

    public FunctionClassResource(FunctionClassService functionClassService) {
        this.functionClassService = functionClassService;
    }

    /**
     * {@code POST  /function-classes} : Create a new functionClass.
     *
     * @param functionClass the functionClass to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new functionClass, or with status {@code 400 (Bad Request)} if the functionClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/function-classes")
    public ResponseEntity<FunctionClass> createFunctionClass(@Valid @RequestBody FunctionClass functionClass) throws URISyntaxException {
        log.debug("REST request to save FunctionClass : {}", functionClass);
        if (functionClass.getId() != null) {
            throw new BadRequestAlertException("A new functionClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FunctionClass result = functionClassService.save(functionClass);
        return ResponseEntity.created(new URI("/api/function-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /function-classes} : Updates an existing functionClass.
     *
     * @param functionClass the functionClass to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated functionClass,
     * or with status {@code 400 (Bad Request)} if the functionClass is not valid,
     * or with status {@code 500 (Internal Server Error)} if the functionClass couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/function-classes")
    public ResponseEntity<FunctionClass> updateFunctionClass(@Valid @RequestBody FunctionClass functionClass) throws URISyntaxException {
        log.debug("REST request to update FunctionClass : {}", functionClass);
        if (functionClass.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FunctionClass result = functionClassService.save(functionClass);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, functionClass.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /function-classes} : get all the functionClasses.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of functionClasses in body.
     */
    @GetMapping("/function-classes")
    public ResponseEntity<List<FunctionClass>> getAllFunctionClasses(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of FunctionClasses");
        Page<FunctionClass> page = functionClassService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /function-classes/:id} : get the "id" functionClass.
     *
     * @param id the id of the functionClass to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the functionClass, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/function-classes/{id}")
    public ResponseEntity<FunctionClass> getFunctionClass(@PathVariable Long id) {
        log.debug("REST request to get FunctionClass : {}", id);
        Optional<FunctionClass> functionClass = functionClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(functionClass);
    }

    /**
     * {@code DELETE  /function-classes/:id} : delete the "id" functionClass.
     *
     * @param id the id of the functionClass to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/function-classes/{id}")
    public ResponseEntity<Void> deleteFunctionClass(@PathVariable Long id) {
        log.debug("REST request to delete FunctionClass : {}", id);
        functionClassService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
