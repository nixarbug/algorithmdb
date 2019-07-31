package com.algorithmdb.web.rest;

import com.algorithmdb.domain.Language;
import com.algorithmdb.service.LanguageService;
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
 * REST controller for managing {@link com.algorithmdb.domain.Language}.
 */
@RestController
@RequestMapping("/api")
public class LanguageResource {

    private final Logger log = LoggerFactory.getLogger(LanguageResource.class);

    private static final String ENTITY_NAME = "language";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LanguageService languageService;

    public LanguageResource(LanguageService languageService) {
        this.languageService = languageService;
    }

    /**
     * {@code POST  /languages} : Create a new language.
     *
     * @param language the language to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new language, or with status {@code 400 (Bad Request)} if the language has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/languages")
    public ResponseEntity<Language> createLanguage(@Valid @RequestBody Language language) throws URISyntaxException {
        log.debug("REST request to save Language : {}", language);
        if (language.getId() != null) {
            throw new BadRequestAlertException("A new language cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Language result = languageService.save(language);
        return ResponseEntity.created(new URI("/api/languages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /languages} : Updates an existing language.
     *
     * @param language the language to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated language,
     * or with status {@code 400 (Bad Request)} if the language is not valid,
     * or with status {@code 500 (Internal Server Error)} if the language couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/languages")
    public ResponseEntity<Language> updateLanguage(@Valid @RequestBody Language language) throws URISyntaxException {
        log.debug("REST request to update Language : {}", language);
        if (language.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Language result = languageService.save(language);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, language.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /languages} : get all the languages.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of languages in body.
     */
    @GetMapping("/languages")
    public ResponseEntity<List<Language>> getAllLanguages(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Languages");
        Page<Language> page = languageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /languages/:id} : get the "id" language.
     *
     * @param id the id of the language to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the language, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/languages/{id}")
    public ResponseEntity<Language> getLanguage(@PathVariable Long id) {
        log.debug("REST request to get Language : {}", id);
        Optional<Language> language = languageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(language);
    }

    /**
     * {@code DELETE  /languages/:id} : delete the "id" language.
     *
     * @param id the id of the language to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/languages/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long id) {
        log.debug("REST request to delete Language : {}", id);
        languageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
