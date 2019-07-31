package com.algorithmdb.web.rest;

import com.algorithmdb.AlgorithmdbApp;
import com.algorithmdb.domain.Problem;
import com.algorithmdb.repository.ProblemRepository;
import com.algorithmdb.service.ProblemService;
import com.algorithmdb.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.algorithmdb.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ProblemResource} REST controller.
 */
@SpringBootTest(classes = AlgorithmdbApp.class)
public class ProblemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProblemRepository problemRepository;

    @Mock
    private ProblemRepository problemRepositoryMock;

    @Mock
    private ProblemService problemServiceMock;

    @Autowired
    private ProblemService problemService;

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

    private MockMvc restProblemMockMvc;

    private Problem problem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProblemResource problemResource = new ProblemResource(problemService);
        this.restProblemMockMvc = MockMvcBuilders.standaloneSetup(problemResource)
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
    public static Problem createEntity(EntityManager em) {
        Problem problem = new Problem()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED);
        return problem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Problem createUpdatedEntity(EntityManager em) {
        Problem problem = new Problem()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);
        return problem;
    }

    @BeforeEach
    public void initTest() {
        problem = createEntity(em);
    }

    @Test
    @Transactional
    public void createProblem() throws Exception {
        int databaseSizeBeforeCreate = problemRepository.findAll().size();

        // Create the Problem
        restProblemMockMvc.perform(post("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problem)))
            .andExpect(status().isCreated());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeCreate + 1);
        Problem testProblem = problemList.get(problemList.size() - 1);
        assertThat(testProblem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProblem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProblem.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testProblem.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void createProblemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = problemRepository.findAll().size();

        // Create the Problem with an existing ID
        problem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProblemMockMvc.perform(post("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problem)))
            .andExpect(status().isBadRequest());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = problemRepository.findAll().size();
        // set the field null
        problem.setName(null);

        // Create the Problem, which fails.

        restProblemMockMvc.perform(post("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problem)))
            .andExpect(status().isBadRequest());

        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = problemRepository.findAll().size();
        // set the field null
        problem.setDescription(null);

        // Create the Problem, which fails.

        restProblemMockMvc.perform(post("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problem)))
            .andExpect(status().isBadRequest());

        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = problemRepository.findAll().size();
        // set the field null
        problem.setDateCreated(null);

        // Create the Problem, which fails.

        restProblemMockMvc.perform(post("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problem)))
            .andExpect(status().isBadRequest());

        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProblems() throws Exception {
        // Initialize the database
        problemRepository.saveAndFlush(problem);

        // Get all the problemList
        restProblemMockMvc.perform(get("/api/problems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(problem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProblemsWithEagerRelationshipsIsEnabled() throws Exception {
        ProblemResource problemResource = new ProblemResource(problemServiceMock);
        when(problemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restProblemMockMvc = MockMvcBuilders.standaloneSetup(problemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProblemMockMvc.perform(get("/api/problems?eagerload=true"))
        .andExpect(status().isOk());

        verify(problemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProblemsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ProblemResource problemResource = new ProblemResource(problemServiceMock);
            when(problemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restProblemMockMvc = MockMvcBuilders.standaloneSetup(problemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProblemMockMvc.perform(get("/api/problems?eagerload=true"))
        .andExpect(status().isOk());

            verify(problemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProblem() throws Exception {
        // Initialize the database
        problemRepository.saveAndFlush(problem);

        // Get the problem
        restProblemMockMvc.perform(get("/api/problems/{id}", problem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(problem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProblem() throws Exception {
        // Get the problem
        restProblemMockMvc.perform(get("/api/problems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProblem() throws Exception {
        // Initialize the database
        problemService.save(problem);

        int databaseSizeBeforeUpdate = problemRepository.findAll().size();

        // Update the problem
        Problem updatedProblem = problemRepository.findById(problem.getId()).get();
        // Disconnect from session so that the updates on updatedProblem are not directly saved in db
        em.detach(updatedProblem);
        updatedProblem
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);

        restProblemMockMvc.perform(put("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProblem)))
            .andExpect(status().isOk());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeUpdate);
        Problem testProblem = problemList.get(problemList.size() - 1);
        assertThat(testProblem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProblem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProblem.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testProblem.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingProblem() throws Exception {
        int databaseSizeBeforeUpdate = problemRepository.findAll().size();

        // Create the Problem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProblemMockMvc.perform(put("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problem)))
            .andExpect(status().isBadRequest());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProblem() throws Exception {
        // Initialize the database
        problemService.save(problem);

        int databaseSizeBeforeDelete = problemRepository.findAll().size();

        // Delete the problem
        restProblemMockMvc.perform(delete("/api/problems/{id}", problem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Problem.class);
        Problem problem1 = new Problem();
        problem1.setId(1L);
        Problem problem2 = new Problem();
        problem2.setId(problem1.getId());
        assertThat(problem1).isEqualTo(problem2);
        problem2.setId(2L);
        assertThat(problem1).isNotEqualTo(problem2);
        problem1.setId(null);
        assertThat(problem1).isNotEqualTo(problem2);
    }
}
