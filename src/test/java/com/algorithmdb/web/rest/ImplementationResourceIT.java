package com.algorithmdb.web.rest;

import com.algorithmdb.AlgorithmdbApp;
import com.algorithmdb.domain.Implementation;
import com.algorithmdb.repository.ImplementationRepository;
import com.algorithmdb.service.ImplementationService;
import com.algorithmdb.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.algorithmdb.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ImplementationResource} REST controller.
 */
@SpringBootTest(classes = AlgorithmdbApp.class)
public class ImplementationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ImplementationRepository implementationRepository;

    @Autowired
    private ImplementationService implementationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restImplementationMockMvc;

    private Implementation implementation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImplementationResource implementationResource = new ImplementationResource(implementationService);
        this.restImplementationMockMvc = MockMvcBuilders.standaloneSetup(implementationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Implementation createEntity(EntityManager em) {
        Implementation implementation = new Implementation()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .note(DEFAULT_NOTE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED);
        return implementation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Implementation createUpdatedEntity(EntityManager em) {
        Implementation implementation = new Implementation()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .note(UPDATED_NOTE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);
        return implementation;
    }

    @BeforeEach
    public void initTest() {
        implementation = createEntity(em);
    }

    @Test
    @Transactional
    public void createImplementation() throws Exception {
        int databaseSizeBeforeCreate = implementationRepository.findAll().size();

        // Create the Implementation
        restImplementationMockMvc.perform(post("/api/implementations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(implementation)))
            .andExpect(status().isCreated());

        // Validate the Implementation in the database
        List<Implementation> implementationList = implementationRepository.findAll();
        assertThat(implementationList).hasSize(databaseSizeBeforeCreate + 1);
        Implementation testImplementation = implementationList.get(implementationList.size() - 1);
        assertThat(testImplementation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testImplementation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testImplementation.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testImplementation.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testImplementation.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void createImplementationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = implementationRepository.findAll().size();

        // Create the Implementation with an existing ID
        implementation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImplementationMockMvc.perform(post("/api/implementations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(implementation)))
            .andExpect(status().isBadRequest());

        // Validate the Implementation in the database
        List<Implementation> implementationList = implementationRepository.findAll();
        assertThat(implementationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = implementationRepository.findAll().size();
        // set the field null
        implementation.setName(null);

        // Create the Implementation, which fails.

        restImplementationMockMvc.perform(post("/api/implementations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(implementation)))
            .andExpect(status().isBadRequest());

        List<Implementation> implementationList = implementationRepository.findAll();
        assertThat(implementationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = implementationRepository.findAll().size();
        // set the field null
        implementation.setDateCreated(null);

        // Create the Implementation, which fails.

        restImplementationMockMvc.perform(post("/api/implementations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(implementation)))
            .andExpect(status().isBadRequest());

        List<Implementation> implementationList = implementationRepository.findAll();
        assertThat(implementationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllImplementations() throws Exception {
        // Initialize the database
        implementationRepository.saveAndFlush(implementation);

        // Get all the implementationList
        restImplementationMockMvc.perform(get("/api/implementations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(implementation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getImplementation() throws Exception {
        // Initialize the database
        implementationRepository.saveAndFlush(implementation);

        // Get the implementation
        restImplementationMockMvc.perform(get("/api/implementations/{id}", implementation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(implementation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingImplementation() throws Exception {
        // Get the implementation
        restImplementationMockMvc.perform(get("/api/implementations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImplementation() throws Exception {
        // Initialize the database
        implementationService.save(implementation);

        int databaseSizeBeforeUpdate = implementationRepository.findAll().size();

        // Update the implementation
        Implementation updatedImplementation = implementationRepository.findById(implementation.getId()).get();
        // Disconnect from session so that the updates on updatedImplementation are not directly saved in db
        em.detach(updatedImplementation);
        updatedImplementation
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .note(UPDATED_NOTE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);

        restImplementationMockMvc.perform(put("/api/implementations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImplementation)))
            .andExpect(status().isOk());

        // Validate the Implementation in the database
        List<Implementation> implementationList = implementationRepository.findAll();
        assertThat(implementationList).hasSize(databaseSizeBeforeUpdate);
        Implementation testImplementation = implementationList.get(implementationList.size() - 1);
        assertThat(testImplementation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testImplementation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testImplementation.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testImplementation.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testImplementation.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingImplementation() throws Exception {
        int databaseSizeBeforeUpdate = implementationRepository.findAll().size();

        // Create the Implementation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImplementationMockMvc.perform(put("/api/implementations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(implementation)))
            .andExpect(status().isBadRequest());

        // Validate the Implementation in the database
        List<Implementation> implementationList = implementationRepository.findAll();
        assertThat(implementationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImplementation() throws Exception {
        // Initialize the database
        implementationService.save(implementation);

        int databaseSizeBeforeDelete = implementationRepository.findAll().size();

        // Delete the implementation
        restImplementationMockMvc.perform(delete("/api/implementations/{id}", implementation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Implementation> implementationList = implementationRepository.findAll();
        assertThat(implementationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Implementation.class);
        Implementation implementation1 = new Implementation();
        implementation1.setId(1L);
        Implementation implementation2 = new Implementation();
        implementation2.setId(implementation1.getId());
        assertThat(implementation1).isEqualTo(implementation2);
        implementation2.setId(2L);
        assertThat(implementation1).isNotEqualTo(implementation2);
        implementation1.setId(null);
        assertThat(implementation1).isNotEqualTo(implementation2);
    }
}
