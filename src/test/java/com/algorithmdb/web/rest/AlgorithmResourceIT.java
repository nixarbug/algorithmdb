package com.algorithmdb.web.rest;

import com.algorithmdb.AlgorithmdbApp;
import com.algorithmdb.domain.Algorithm;
import com.algorithmdb.repository.AlgorithmRepository;
import com.algorithmdb.service.AlgorithmService;
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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@Link AlgorithmResource} REST controller.
 */
@SpringBootTest(classes = AlgorithmdbApp.class)
public class AlgorithmResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INPUT = "AAAAAAAAAA";
    private static final String UPDATED_INPUT = "BBBBBBBBBB";

    private static final String DEFAULT_OUTPUT = "AAAAAAAAAA";
    private static final String UPDATED_OUTPUT = "BBBBBBBBBB";

    private static final String DEFAULT_IDEA = "AAAAAAAAAA";
    private static final String UPDATED_IDEA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_REAL_LIFE_USE = "AAAAAAAAAA";
    private static final String UPDATED_REAL_LIFE_USE = "BBBBBBBBBB";

    private static final String DEFAULT_PSEUDOCODE = "AAAAAAAAAA";
    private static final String UPDATED_PSEUDOCODE = "BBBBBBBBBB";

    private static final String DEFAULT_FLOWCHART = "AAAAAAAAAA";
    private static final String UPDATED_FLOWCHART = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FLOWCHART_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FLOWCHART_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FLOWCHART_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FLOWCHART_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_COMPLEXITY_ANALYSIS = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEXITY_ANALYSIS = "BBBBBBBBBB";

    private static final String DEFAULT_CORRECTNESS_PROOF = "AAAAAAAAAA";
    private static final String UPDATED_CORRECTNESS_PROOF = "BBBBBBBBBB";

    private static final Float DEFAULT_AVERAGE_STARS = 1F;
    private static final Float UPDATED_AVERAGE_STARS = 2F;

    private static final Integer DEFAULT_TOTAL_FAVS = 1;
    private static final Integer UPDATED_TOTAL_FAVS = 2;

    private static final Float DEFAULT_WEIGHTED_RATING = 1F;
    private static final Float UPDATED_WEIGHTED_RATING = 2F;

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AlgorithmRepository algorithmRepository;

    @Mock
    private AlgorithmRepository algorithmRepositoryMock;

    @Mock
    private AlgorithmService algorithmServiceMock;

    @Autowired
    private AlgorithmService algorithmService;

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

    private MockMvc restAlgorithmMockMvc;

    private Algorithm algorithm;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlgorithmResource algorithmResource = new AlgorithmResource(algorithmService);
        this.restAlgorithmMockMvc = MockMvcBuilders.standaloneSetup(algorithmResource)
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
    public static Algorithm createEntity(EntityManager em) {
        Algorithm algorithm = new Algorithm()
            .name(DEFAULT_NAME)
            .input(DEFAULT_INPUT)
            .output(DEFAULT_OUTPUT)
            .idea(DEFAULT_IDEA)
            .description(DEFAULT_DESCRIPTION)
            .realLifeUse(DEFAULT_REAL_LIFE_USE)
            .pseudocode(DEFAULT_PSEUDOCODE)
            .flowchart(DEFAULT_FLOWCHART)
            .flowchartImage(DEFAULT_FLOWCHART_IMAGE)
            .flowchartImageContentType(DEFAULT_FLOWCHART_IMAGE_CONTENT_TYPE)
            .complexityAnalysis(DEFAULT_COMPLEXITY_ANALYSIS)
            .correctnessProof(DEFAULT_CORRECTNESS_PROOF)
            .averageStars(DEFAULT_AVERAGE_STARS)
            .totalFavs(DEFAULT_TOTAL_FAVS)
            .weightedRating(DEFAULT_WEIGHTED_RATING)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED);
        return algorithm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Algorithm createUpdatedEntity(EntityManager em) {
        Algorithm algorithm = new Algorithm()
            .name(UPDATED_NAME)
            .input(UPDATED_INPUT)
            .output(UPDATED_OUTPUT)
            .idea(UPDATED_IDEA)
            .description(UPDATED_DESCRIPTION)
            .realLifeUse(UPDATED_REAL_LIFE_USE)
            .pseudocode(UPDATED_PSEUDOCODE)
            .flowchart(UPDATED_FLOWCHART)
            .flowchartImage(UPDATED_FLOWCHART_IMAGE)
            .flowchartImageContentType(UPDATED_FLOWCHART_IMAGE_CONTENT_TYPE)
            .complexityAnalysis(UPDATED_COMPLEXITY_ANALYSIS)
            .correctnessProof(UPDATED_CORRECTNESS_PROOF)
            .averageStars(UPDATED_AVERAGE_STARS)
            .totalFavs(UPDATED_TOTAL_FAVS)
            .weightedRating(UPDATED_WEIGHTED_RATING)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);
        return algorithm;
    }

    @BeforeEach
    public void initTest() {
        algorithm = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlgorithm() throws Exception {
        int databaseSizeBeforeCreate = algorithmRepository.findAll().size();

        // Create the Algorithm
        restAlgorithmMockMvc.perform(post("/api/algorithms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(algorithm)))
            .andExpect(status().isCreated());

        // Validate the Algorithm in the database
        List<Algorithm> algorithmList = algorithmRepository.findAll();
        assertThat(algorithmList).hasSize(databaseSizeBeforeCreate + 1);
        Algorithm testAlgorithm = algorithmList.get(algorithmList.size() - 1);
        assertThat(testAlgorithm.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAlgorithm.getInput()).isEqualTo(DEFAULT_INPUT);
        assertThat(testAlgorithm.getOutput()).isEqualTo(DEFAULT_OUTPUT);
        assertThat(testAlgorithm.getIdea()).isEqualTo(DEFAULT_IDEA);
        assertThat(testAlgorithm.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAlgorithm.getRealLifeUse()).isEqualTo(DEFAULT_REAL_LIFE_USE);
        assertThat(testAlgorithm.getPseudocode()).isEqualTo(DEFAULT_PSEUDOCODE);
        assertThat(testAlgorithm.getFlowchart()).isEqualTo(DEFAULT_FLOWCHART);
        assertThat(testAlgorithm.getFlowchartImage()).isEqualTo(DEFAULT_FLOWCHART_IMAGE);
        assertThat(testAlgorithm.getFlowchartImageContentType()).isEqualTo(DEFAULT_FLOWCHART_IMAGE_CONTENT_TYPE);
        assertThat(testAlgorithm.getComplexityAnalysis()).isEqualTo(DEFAULT_COMPLEXITY_ANALYSIS);
        assertThat(testAlgorithm.getCorrectnessProof()).isEqualTo(DEFAULT_CORRECTNESS_PROOF);
        assertThat(testAlgorithm.getAverageStars()).isEqualTo(DEFAULT_AVERAGE_STARS);
        assertThat(testAlgorithm.getTotalFavs()).isEqualTo(DEFAULT_TOTAL_FAVS);
        assertThat(testAlgorithm.getWeightedRating()).isEqualTo(DEFAULT_WEIGHTED_RATING);
        assertThat(testAlgorithm.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testAlgorithm.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void createAlgorithmWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = algorithmRepository.findAll().size();

        // Create the Algorithm with an existing ID
        algorithm.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlgorithmMockMvc.perform(post("/api/algorithms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(algorithm)))
            .andExpect(status().isBadRequest());

        // Validate the Algorithm in the database
        List<Algorithm> algorithmList = algorithmRepository.findAll();
        assertThat(algorithmList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = algorithmRepository.findAll().size();
        // set the field null
        algorithm.setName(null);

        // Create the Algorithm, which fails.

        restAlgorithmMockMvc.perform(post("/api/algorithms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(algorithm)))
            .andExpect(status().isBadRequest());

        List<Algorithm> algorithmList = algorithmRepository.findAll();
        assertThat(algorithmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInputIsRequired() throws Exception {
        int databaseSizeBeforeTest = algorithmRepository.findAll().size();
        // set the field null
        algorithm.setInput(null);

        // Create the Algorithm, which fails.

        restAlgorithmMockMvc.perform(post("/api/algorithms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(algorithm)))
            .andExpect(status().isBadRequest());

        List<Algorithm> algorithmList = algorithmRepository.findAll();
        assertThat(algorithmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOutputIsRequired() throws Exception {
        int databaseSizeBeforeTest = algorithmRepository.findAll().size();
        // set the field null
        algorithm.setOutput(null);

        // Create the Algorithm, which fails.

        restAlgorithmMockMvc.perform(post("/api/algorithms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(algorithm)))
            .andExpect(status().isBadRequest());

        List<Algorithm> algorithmList = algorithmRepository.findAll();
        assertThat(algorithmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = algorithmRepository.findAll().size();
        // set the field null
        algorithm.setDateCreated(null);

        // Create the Algorithm, which fails.

        restAlgorithmMockMvc.perform(post("/api/algorithms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(algorithm)))
            .andExpect(status().isBadRequest());

        List<Algorithm> algorithmList = algorithmRepository.findAll();
        assertThat(algorithmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlgorithms() throws Exception {
        // Initialize the database
        algorithmRepository.saveAndFlush(algorithm);

        // Get all the algorithmList
        restAlgorithmMockMvc.perform(get("/api/algorithms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(algorithm.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].input").value(hasItem(DEFAULT_INPUT.toString())))
            .andExpect(jsonPath("$.[*].output").value(hasItem(DEFAULT_OUTPUT.toString())))
            .andExpect(jsonPath("$.[*].idea").value(hasItem(DEFAULT_IDEA.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].realLifeUse").value(hasItem(DEFAULT_REAL_LIFE_USE.toString())))
            .andExpect(jsonPath("$.[*].pseudocode").value(hasItem(DEFAULT_PSEUDOCODE.toString())))
            .andExpect(jsonPath("$.[*].flowchart").value(hasItem(DEFAULT_FLOWCHART.toString())))
            .andExpect(jsonPath("$.[*].flowchartImageContentType").value(hasItem(DEFAULT_FLOWCHART_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].flowchartImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_FLOWCHART_IMAGE))))
            .andExpect(jsonPath("$.[*].complexityAnalysis").value(hasItem(DEFAULT_COMPLEXITY_ANALYSIS.toString())))
            .andExpect(jsonPath("$.[*].correctnessProof").value(hasItem(DEFAULT_CORRECTNESS_PROOF.toString())))
            .andExpect(jsonPath("$.[*].averageStars").value(hasItem(DEFAULT_AVERAGE_STARS.doubleValue())))
            .andExpect(jsonPath("$.[*].totalFavs").value(hasItem(DEFAULT_TOTAL_FAVS)))
            .andExpect(jsonPath("$.[*].weightedRating").value(hasItem(DEFAULT_WEIGHTED_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAlgorithmsWithEagerRelationshipsIsEnabled() throws Exception {
        AlgorithmResource algorithmResource = new AlgorithmResource(algorithmServiceMock);
        when(algorithmServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAlgorithmMockMvc = MockMvcBuilders.standaloneSetup(algorithmResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAlgorithmMockMvc.perform(get("/api/algorithms?eagerload=true"))
        .andExpect(status().isOk());

        verify(algorithmServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAlgorithmsWithEagerRelationshipsIsNotEnabled() throws Exception {
        AlgorithmResource algorithmResource = new AlgorithmResource(algorithmServiceMock);
            when(algorithmServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAlgorithmMockMvc = MockMvcBuilders.standaloneSetup(algorithmResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAlgorithmMockMvc.perform(get("/api/algorithms?eagerload=true"))
        .andExpect(status().isOk());

            verify(algorithmServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAlgorithm() throws Exception {
        // Initialize the database
        algorithmRepository.saveAndFlush(algorithm);

        // Get the algorithm
        restAlgorithmMockMvc.perform(get("/api/algorithms/{id}", algorithm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(algorithm.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.input").value(DEFAULT_INPUT.toString()))
            .andExpect(jsonPath("$.output").value(DEFAULT_OUTPUT.toString()))
            .andExpect(jsonPath("$.idea").value(DEFAULT_IDEA.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.realLifeUse").value(DEFAULT_REAL_LIFE_USE.toString()))
            .andExpect(jsonPath("$.pseudocode").value(DEFAULT_PSEUDOCODE.toString()))
            .andExpect(jsonPath("$.flowchart").value(DEFAULT_FLOWCHART.toString()))
            .andExpect(jsonPath("$.flowchartImageContentType").value(DEFAULT_FLOWCHART_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.flowchartImage").value(Base64Utils.encodeToString(DEFAULT_FLOWCHART_IMAGE)))
            .andExpect(jsonPath("$.complexityAnalysis").value(DEFAULT_COMPLEXITY_ANALYSIS.toString()))
            .andExpect(jsonPath("$.correctnessProof").value(DEFAULT_CORRECTNESS_PROOF.toString()))
            .andExpect(jsonPath("$.averageStars").value(DEFAULT_AVERAGE_STARS.doubleValue()))
            .andExpect(jsonPath("$.totalFavs").value(DEFAULT_TOTAL_FAVS))
            .andExpect(jsonPath("$.weightedRating").value(DEFAULT_WEIGHTED_RATING.doubleValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAlgorithm() throws Exception {
        // Get the algorithm
        restAlgorithmMockMvc.perform(get("/api/algorithms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlgorithm() throws Exception {
        // Initialize the database
        algorithmService.save(algorithm);

        int databaseSizeBeforeUpdate = algorithmRepository.findAll().size();

        // Update the algorithm
        Algorithm updatedAlgorithm = algorithmRepository.findById(algorithm.getId()).get();
        // Disconnect from session so that the updates on updatedAlgorithm are not directly saved in db
        em.detach(updatedAlgorithm);
        updatedAlgorithm
            .name(UPDATED_NAME)
            .input(UPDATED_INPUT)
            .output(UPDATED_OUTPUT)
            .idea(UPDATED_IDEA)
            .description(UPDATED_DESCRIPTION)
            .realLifeUse(UPDATED_REAL_LIFE_USE)
            .pseudocode(UPDATED_PSEUDOCODE)
            .flowchart(UPDATED_FLOWCHART)
            .flowchartImage(UPDATED_FLOWCHART_IMAGE)
            .flowchartImageContentType(UPDATED_FLOWCHART_IMAGE_CONTENT_TYPE)
            .complexityAnalysis(UPDATED_COMPLEXITY_ANALYSIS)
            .correctnessProof(UPDATED_CORRECTNESS_PROOF)
            .averageStars(UPDATED_AVERAGE_STARS)
            .totalFavs(UPDATED_TOTAL_FAVS)
            .weightedRating(UPDATED_WEIGHTED_RATING)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);

        restAlgorithmMockMvc.perform(put("/api/algorithms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlgorithm)))
            .andExpect(status().isOk());

        // Validate the Algorithm in the database
        List<Algorithm> algorithmList = algorithmRepository.findAll();
        assertThat(algorithmList).hasSize(databaseSizeBeforeUpdate);
        Algorithm testAlgorithm = algorithmList.get(algorithmList.size() - 1);
        assertThat(testAlgorithm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlgorithm.getInput()).isEqualTo(UPDATED_INPUT);
        assertThat(testAlgorithm.getOutput()).isEqualTo(UPDATED_OUTPUT);
        assertThat(testAlgorithm.getIdea()).isEqualTo(UPDATED_IDEA);
        assertThat(testAlgorithm.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAlgorithm.getRealLifeUse()).isEqualTo(UPDATED_REAL_LIFE_USE);
        assertThat(testAlgorithm.getPseudocode()).isEqualTo(UPDATED_PSEUDOCODE);
        assertThat(testAlgorithm.getFlowchart()).isEqualTo(UPDATED_FLOWCHART);
        assertThat(testAlgorithm.getFlowchartImage()).isEqualTo(UPDATED_FLOWCHART_IMAGE);
        assertThat(testAlgorithm.getFlowchartImageContentType()).isEqualTo(UPDATED_FLOWCHART_IMAGE_CONTENT_TYPE);
        assertThat(testAlgorithm.getComplexityAnalysis()).isEqualTo(UPDATED_COMPLEXITY_ANALYSIS);
        assertThat(testAlgorithm.getCorrectnessProof()).isEqualTo(UPDATED_CORRECTNESS_PROOF);
        assertThat(testAlgorithm.getAverageStars()).isEqualTo(UPDATED_AVERAGE_STARS);
        assertThat(testAlgorithm.getTotalFavs()).isEqualTo(UPDATED_TOTAL_FAVS);
        assertThat(testAlgorithm.getWeightedRating()).isEqualTo(UPDATED_WEIGHTED_RATING);
        assertThat(testAlgorithm.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testAlgorithm.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAlgorithm() throws Exception {
        int databaseSizeBeforeUpdate = algorithmRepository.findAll().size();

        // Create the Algorithm

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlgorithmMockMvc.perform(put("/api/algorithms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(algorithm)))
            .andExpect(status().isBadRequest());

        // Validate the Algorithm in the database
        List<Algorithm> algorithmList = algorithmRepository.findAll();
        assertThat(algorithmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlgorithm() throws Exception {
        // Initialize the database
        algorithmService.save(algorithm);

        int databaseSizeBeforeDelete = algorithmRepository.findAll().size();

        // Delete the algorithm
        restAlgorithmMockMvc.perform(delete("/api/algorithms/{id}", algorithm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Algorithm> algorithmList = algorithmRepository.findAll();
        assertThat(algorithmList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Algorithm.class);
        Algorithm algorithm1 = new Algorithm();
        algorithm1.setId(1L);
        Algorithm algorithm2 = new Algorithm();
        algorithm2.setId(algorithm1.getId());
        assertThat(algorithm1).isEqualTo(algorithm2);
        algorithm2.setId(2L);
        assertThat(algorithm1).isNotEqualTo(algorithm2);
        algorithm1.setId(null);
        assertThat(algorithm1).isNotEqualTo(algorithm2);
    }
}
