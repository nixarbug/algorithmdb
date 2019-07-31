package com.algorithmdb.web.rest;

import com.algorithmdb.AlgorithmdbApp;
import com.algorithmdb.domain.SystemSetting;
import com.algorithmdb.repository.SystemSettingRepository;
import com.algorithmdb.service.SystemSettingService;
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

import com.algorithmdb.domain.enumeration.SystemSettingKey;
/**
 * Integration tests for the {@Link SystemSettingResource} REST controller.
 */
@SpringBootTest(classes = AlgorithmdbApp.class)
public class SystemSettingResourceIT {

    private static final SystemSettingKey DEFAULT_KEY = SystemSettingKey.MIN_NUMBER_OF_RATINGS;
    private static final SystemSettingKey UPDATED_KEY = SystemSettingKey.MIN_NUMBER_OF_RATINGS;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SystemSettingRepository systemSettingRepository;

    @Autowired
    private SystemSettingService systemSettingService;

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

    private MockMvc restSystemSettingMockMvc;

    private SystemSetting systemSetting;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SystemSettingResource systemSettingResource = new SystemSettingResource(systemSettingService);
        this.restSystemSettingMockMvc = MockMvcBuilders.standaloneSetup(systemSettingResource)
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
    public static SystemSetting createEntity(EntityManager em) {
        SystemSetting systemSetting = new SystemSetting()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION);
        return systemSetting;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemSetting createUpdatedEntity(EntityManager em) {
        SystemSetting systemSetting = new SystemSetting()
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION);
        return systemSetting;
    }

    @BeforeEach
    public void initTest() {
        systemSetting = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystemSetting() throws Exception {
        int databaseSizeBeforeCreate = systemSettingRepository.findAll().size();

        // Create the SystemSetting
        restSystemSettingMockMvc.perform(post("/api/system-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemSetting)))
            .andExpect(status().isCreated());

        // Validate the SystemSetting in the database
        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeCreate + 1);
        SystemSetting testSystemSetting = systemSettingList.get(systemSettingList.size() - 1);
        assertThat(testSystemSetting.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testSystemSetting.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testSystemSetting.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSystemSettingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemSettingRepository.findAll().size();

        // Create the SystemSetting with an existing ID
        systemSetting.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemSettingMockMvc.perform(post("/api/system-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemSetting)))
            .andExpect(status().isBadRequest());

        // Validate the SystemSetting in the database
        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemSettingRepository.findAll().size();
        // set the field null
        systemSetting.setKey(null);

        // Create the SystemSetting, which fails.

        restSystemSettingMockMvc.perform(post("/api/system-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemSetting)))
            .andExpect(status().isBadRequest());

        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemSettingRepository.findAll().size();
        // set the field null
        systemSetting.setValue(null);

        // Create the SystemSetting, which fails.

        restSystemSettingMockMvc.perform(post("/api/system-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemSetting)))
            .andExpect(status().isBadRequest());

        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemSettingRepository.findAll().size();
        // set the field null
        systemSetting.setDescription(null);

        // Create the SystemSetting, which fails.

        restSystemSettingMockMvc.perform(post("/api/system-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemSetting)))
            .andExpect(status().isBadRequest());

        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSystemSettings() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList
        restSystemSettingMockMvc.perform(get("/api/system-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getSystemSetting() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get the systemSetting
        restSystemSettingMockMvc.perform(get("/api/system-settings/{id}", systemSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(systemSetting.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSystemSetting() throws Exception {
        // Get the systemSetting
        restSystemSettingMockMvc.perform(get("/api/system-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystemSetting() throws Exception {
        // Initialize the database
        systemSettingService.save(systemSetting);

        int databaseSizeBeforeUpdate = systemSettingRepository.findAll().size();

        // Update the systemSetting
        SystemSetting updatedSystemSetting = systemSettingRepository.findById(systemSetting.getId()).get();
        // Disconnect from session so that the updates on updatedSystemSetting are not directly saved in db
        em.detach(updatedSystemSetting);
        updatedSystemSetting
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION);

        restSystemSettingMockMvc.perform(put("/api/system-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSystemSetting)))
            .andExpect(status().isOk());

        // Validate the SystemSetting in the database
        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeUpdate);
        SystemSetting testSystemSetting = systemSettingList.get(systemSettingList.size() - 1);
        assertThat(testSystemSetting.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testSystemSetting.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testSystemSetting.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSystemSetting() throws Exception {
        int databaseSizeBeforeUpdate = systemSettingRepository.findAll().size();

        // Create the SystemSetting

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemSettingMockMvc.perform(put("/api/system-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemSetting)))
            .andExpect(status().isBadRequest());

        // Validate the SystemSetting in the database
        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSystemSetting() throws Exception {
        // Initialize the database
        systemSettingService.save(systemSetting);

        int databaseSizeBeforeDelete = systemSettingRepository.findAll().size();

        // Delete the systemSetting
        restSystemSettingMockMvc.perform(delete("/api/system-settings/{id}", systemSetting.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemSetting.class);
        SystemSetting systemSetting1 = new SystemSetting();
        systemSetting1.setId(1L);
        SystemSetting systemSetting2 = new SystemSetting();
        systemSetting2.setId(systemSetting1.getId());
        assertThat(systemSetting1).isEqualTo(systemSetting2);
        systemSetting2.setId(2L);
        assertThat(systemSetting1).isNotEqualTo(systemSetting2);
        systemSetting1.setId(null);
        assertThat(systemSetting1).isNotEqualTo(systemSetting2);
    }
}
