package com.algorithmdb.web.rest;

import com.algorithmdb.AlgorithmdbApp;
import com.algorithmdb.domain.FunctionClass;
import com.algorithmdb.repository.FunctionClassRepository;
import com.algorithmdb.service.FunctionClassService;
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
import java.util.List;

import static com.algorithmdb.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link FunctionClassResource} REST controller.
 */
@SpringBootTest(classes = AlgorithmdbApp.class)
public class FunctionClassResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FORMULA = "AAAAAAAAAA";
    private static final String UPDATED_FORMULA = "BBBBBBBBBB";

    private static final Integer DEFAULT_RELATIVE_ORDER = 1;
    private static final Integer UPDATED_RELATIVE_ORDER = 2;

    @Autowired
    private FunctionClassRepository functionClassRepository;

    @Autowired
    private FunctionClassService functionClassService;

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

    private MockMvc restFunctionClassMockMvc;

    private FunctionClass functionClass;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FunctionClassResource functionClassResource = new FunctionClassResource(functionClassService);
        this.restFunctionClassMockMvc = MockMvcBuilders.standaloneSetup(functionClassResource)
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
    public static FunctionClass createEntity(EntityManager em) {
        FunctionClass functionClass = new FunctionClass()
            .name(DEFAULT_NAME)
            .formula(DEFAULT_FORMULA)
            .relativeOrder(DEFAULT_RELATIVE_ORDER);
        return functionClass;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FunctionClass createUpdatedEntity(EntityManager em) {
        FunctionClass functionClass = new FunctionClass()
            .name(UPDATED_NAME)
            .formula(UPDATED_FORMULA)
            .relativeOrder(UPDATED_RELATIVE_ORDER);
        return functionClass;
    }

    @BeforeEach
    public void initTest() {
        functionClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createFunctionClass() throws Exception {
        int databaseSizeBeforeCreate = functionClassRepository.findAll().size();

        // Create the FunctionClass
        restFunctionClassMockMvc.perform(post("/api/function-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(functionClass)))
            .andExpect(status().isCreated());

        // Validate the FunctionClass in the database
        List<FunctionClass> functionClassList = functionClassRepository.findAll();
        assertThat(functionClassList).hasSize(databaseSizeBeforeCreate + 1);
        FunctionClass testFunctionClass = functionClassList.get(functionClassList.size() - 1);
        assertThat(testFunctionClass.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFunctionClass.getFormula()).isEqualTo(DEFAULT_FORMULA);
        assertThat(testFunctionClass.getRelativeOrder()).isEqualTo(DEFAULT_RELATIVE_ORDER);
    }

    @Test
    @Transactional
    public void createFunctionClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = functionClassRepository.findAll().size();

        // Create the FunctionClass with an existing ID
        functionClass.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFunctionClassMockMvc.perform(post("/api/function-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(functionClass)))
            .andExpect(status().isBadRequest());

        // Validate the FunctionClass in the database
        List<FunctionClass> functionClassList = functionClassRepository.findAll();
        assertThat(functionClassList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = functionClassRepository.findAll().size();
        // set the field null
        functionClass.setName(null);

        // Create the FunctionClass, which fails.

        restFunctionClassMockMvc.perform(post("/api/function-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(functionClass)))
            .andExpect(status().isBadRequest());

        List<FunctionClass> functionClassList = functionClassRepository.findAll();
        assertThat(functionClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRelativeOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = functionClassRepository.findAll().size();
        // set the field null
        functionClass.setRelativeOrder(null);

        // Create the FunctionClass, which fails.

        restFunctionClassMockMvc.perform(post("/api/function-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(functionClass)))
            .andExpect(status().isBadRequest());

        List<FunctionClass> functionClassList = functionClassRepository.findAll();
        assertThat(functionClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFunctionClasses() throws Exception {
        // Initialize the database
        functionClassRepository.saveAndFlush(functionClass);

        // Get all the functionClassList
        restFunctionClassMockMvc.perform(get("/api/function-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(functionClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].formula").value(hasItem(DEFAULT_FORMULA.toString())))
            .andExpect(jsonPath("$.[*].relativeOrder").value(hasItem(DEFAULT_RELATIVE_ORDER)));
    }
    
    @Test
    @Transactional
    public void getFunctionClass() throws Exception {
        // Initialize the database
        functionClassRepository.saveAndFlush(functionClass);

        // Get the functionClass
        restFunctionClassMockMvc.perform(get("/api/function-classes/{id}", functionClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(functionClass.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.formula").value(DEFAULT_FORMULA.toString()))
            .andExpect(jsonPath("$.relativeOrder").value(DEFAULT_RELATIVE_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingFunctionClass() throws Exception {
        // Get the functionClass
        restFunctionClassMockMvc.perform(get("/api/function-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFunctionClass() throws Exception {
        // Initialize the database
        functionClassService.save(functionClass);

        int databaseSizeBeforeUpdate = functionClassRepository.findAll().size();

        // Update the functionClass
        FunctionClass updatedFunctionClass = functionClassRepository.findById(functionClass.getId()).get();
        // Disconnect from session so that the updates on updatedFunctionClass are not directly saved in db
        em.detach(updatedFunctionClass);
        updatedFunctionClass
            .name(UPDATED_NAME)
            .formula(UPDATED_FORMULA)
            .relativeOrder(UPDATED_RELATIVE_ORDER);

        restFunctionClassMockMvc.perform(put("/api/function-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFunctionClass)))
            .andExpect(status().isOk());

        // Validate the FunctionClass in the database
        List<FunctionClass> functionClassList = functionClassRepository.findAll();
        assertThat(functionClassList).hasSize(databaseSizeBeforeUpdate);
        FunctionClass testFunctionClass = functionClassList.get(functionClassList.size() - 1);
        assertThat(testFunctionClass.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFunctionClass.getFormula()).isEqualTo(UPDATED_FORMULA);
        assertThat(testFunctionClass.getRelativeOrder()).isEqualTo(UPDATED_RELATIVE_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingFunctionClass() throws Exception {
        int databaseSizeBeforeUpdate = functionClassRepository.findAll().size();

        // Create the FunctionClass

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFunctionClassMockMvc.perform(put("/api/function-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(functionClass)))
            .andExpect(status().isBadRequest());

        // Validate the FunctionClass in the database
        List<FunctionClass> functionClassList = functionClassRepository.findAll();
        assertThat(functionClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFunctionClass() throws Exception {
        // Initialize the database
        functionClassService.save(functionClass);

        int databaseSizeBeforeDelete = functionClassRepository.findAll().size();

        // Delete the functionClass
        restFunctionClassMockMvc.perform(delete("/api/function-classes/{id}", functionClass.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FunctionClass> functionClassList = functionClassRepository.findAll();
        assertThat(functionClassList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FunctionClass.class);
        FunctionClass functionClass1 = new FunctionClass();
        functionClass1.setId(1L);
        FunctionClass functionClass2 = new FunctionClass();
        functionClass2.setId(functionClass1.getId());
        assertThat(functionClass1).isEqualTo(functionClass2);
        functionClass2.setId(2L);
        assertThat(functionClass1).isNotEqualTo(functionClass2);
        functionClass1.setId(null);
        assertThat(functionClass1).isNotEqualTo(functionClass2);
    }
}
