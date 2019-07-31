package com.algorithmdb.web.rest;

import com.algorithmdb.AlgorithmdbApp;
import com.algorithmdb.domain.ProblemGroup;
import com.algorithmdb.repository.ProblemGroupRepository;
import com.algorithmdb.service.ProblemGroupService;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.algorithmdb.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ProblemGroupResource} REST controller.
 */
@SpringBootTest(classes = AlgorithmdbApp.class)
public class ProblemGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ProblemGroupRepository problemGroupRepository;

    @Autowired
    private ProblemGroupService problemGroupService;

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

    private MockMvc restProblemGroupMockMvc;

    private ProblemGroup problemGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProblemGroupResource problemGroupResource = new ProblemGroupResource(problemGroupService);
        this.restProblemGroupMockMvc = MockMvcBuilders.standaloneSetup(problemGroupResource)
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
    public static ProblemGroup createEntity(EntityManager em) {
        ProblemGroup problemGroup = new ProblemGroup()
            .name(DEFAULT_NAME);
        return problemGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProblemGroup createUpdatedEntity(EntityManager em) {
        ProblemGroup problemGroup = new ProblemGroup()
            .name(UPDATED_NAME);
        return problemGroup;
    }

    @BeforeEach
    public void initTest() {
        problemGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createProblemGroup() throws Exception {
        int databaseSizeBeforeCreate = problemGroupRepository.findAll().size();

        // Create the ProblemGroup
        restProblemGroupMockMvc.perform(post("/api/problem-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemGroup)))
            .andExpect(status().isCreated());

        // Validate the ProblemGroup in the database
        List<ProblemGroup> problemGroupList = problemGroupRepository.findAll();
        assertThat(problemGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ProblemGroup testProblemGroup = problemGroupList.get(problemGroupList.size() - 1);
        assertThat(testProblemGroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProblemGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = problemGroupRepository.findAll().size();

        // Create the ProblemGroup with an existing ID
        problemGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProblemGroupMockMvc.perform(post("/api/problem-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemGroup)))
            .andExpect(status().isBadRequest());

        // Validate the ProblemGroup in the database
        List<ProblemGroup> problemGroupList = problemGroupRepository.findAll();
        assertThat(problemGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = problemGroupRepository.findAll().size();
        // set the field null
        problemGroup.setName(null);

        // Create the ProblemGroup, which fails.

        restProblemGroupMockMvc.perform(post("/api/problem-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemGroup)))
            .andExpect(status().isBadRequest());

        List<ProblemGroup> problemGroupList = problemGroupRepository.findAll();
        assertThat(problemGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProblemGroups() throws Exception {
        // Initialize the database
        problemGroupRepository.saveAndFlush(problemGroup);

        // Get all the problemGroupList
        restProblemGroupMockMvc.perform(get("/api/problem-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(problemGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getProblemGroup() throws Exception {
        // Initialize the database
        problemGroupRepository.saveAndFlush(problemGroup);

        // Get the problemGroup
        restProblemGroupMockMvc.perform(get("/api/problem-groups/{id}", problemGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(problemGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProblemGroup() throws Exception {
        // Get the problemGroup
        restProblemGroupMockMvc.perform(get("/api/problem-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProblemGroup() throws Exception {
        // Initialize the database
        problemGroupService.save(problemGroup);

        int databaseSizeBeforeUpdate = problemGroupRepository.findAll().size();

        // Update the problemGroup
        ProblemGroup updatedProblemGroup = problemGroupRepository.findById(problemGroup.getId()).get();
        // Disconnect from session so that the updates on updatedProblemGroup are not directly saved in db
        em.detach(updatedProblemGroup);
        updatedProblemGroup
            .name(UPDATED_NAME);

        restProblemGroupMockMvc.perform(put("/api/problem-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProblemGroup)))
            .andExpect(status().isOk());

        // Validate the ProblemGroup in the database
        List<ProblemGroup> problemGroupList = problemGroupRepository.findAll();
        assertThat(problemGroupList).hasSize(databaseSizeBeforeUpdate);
        ProblemGroup testProblemGroup = problemGroupList.get(problemGroupList.size() - 1);
        assertThat(testProblemGroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProblemGroup() throws Exception {
        int databaseSizeBeforeUpdate = problemGroupRepository.findAll().size();

        // Create the ProblemGroup

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProblemGroupMockMvc.perform(put("/api/problem-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemGroup)))
            .andExpect(status().isBadRequest());

        // Validate the ProblemGroup in the database
        List<ProblemGroup> problemGroupList = problemGroupRepository.findAll();
        assertThat(problemGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProblemGroup() throws Exception {
        // Initialize the database
        problemGroupService.save(problemGroup);

        int databaseSizeBeforeDelete = problemGroupRepository.findAll().size();

        // Delete the problemGroup
        restProblemGroupMockMvc.perform(delete("/api/problem-groups/{id}", problemGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProblemGroup> problemGroupList = problemGroupRepository.findAll();
        assertThat(problemGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProblemGroup.class);
        ProblemGroup problemGroup1 = new ProblemGroup();
        problemGroup1.setId(1L);
        ProblemGroup problemGroup2 = new ProblemGroup();
        problemGroup2.setId(problemGroup1.getId());
        assertThat(problemGroup1).isEqualTo(problemGroup2);
        problemGroup2.setId(2L);
        assertThat(problemGroup1).isNotEqualTo(problemGroup2);
        problemGroup1.setId(null);
        assertThat(problemGroup1).isNotEqualTo(problemGroup2);
    }
}
