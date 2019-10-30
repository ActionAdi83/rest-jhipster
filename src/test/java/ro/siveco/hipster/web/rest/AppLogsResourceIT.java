package ro.siveco.hipster.web.rest;

import ro.siveco.hipster.RestappApp;
import ro.siveco.hipster.domain.AppLogs;
import ro.siveco.hipster.repository.AppLogsRepository;
import ro.siveco.hipster.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static ro.siveco.hipster.web.rest.TestUtil.sameInstant;
import static ro.siveco.hipster.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AppLogsResource} REST controller.
 */
@SpringBootTest(classes = RestappApp.class)
public class AppLogsResourceIT {

    private static final UUID DEFAULT_LOG_ID = UUID.randomUUID();
    private static final UUID UPDATED_LOG_ID = UUID.randomUUID();

    private static final ZonedDateTime DEFAULT_ENTRY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ENTRY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LOGGER = "AAAAAAAAAA";
    private static final String UPDATED_LOGGER = "BBBBBBBBBB";

    private static final String DEFAULT_LOG_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_LOG_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_APLICATIE = "AAAAAAAAAA";
    private static final String UPDATED_APLICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_COD = "AAAAAAAAAA";
    private static final String UPDATED_COD = "BBBBBBBBBB";

    private static final String DEFAULT_TIP = "AAAAAAAAAA";
    private static final String UPDATED_TIP = "BBBBBBBBBB";

    @Autowired
    private AppLogsRepository appLogsRepository;

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

    private MockMvc restAppLogsMockMvc;

    private AppLogs appLogs;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppLogsResource appLogsResource = new AppLogsResource(appLogsRepository);
        this.restAppLogsMockMvc = MockMvcBuilders.standaloneSetup(appLogsResource)
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
    public static AppLogs createEntity(EntityManager em) {
        AppLogs appLogs = new AppLogs()
            .logId(DEFAULT_LOG_ID)
            .entryDate(DEFAULT_ENTRY_DATE)
            .logger(DEFAULT_LOGGER)
            .logLevel(DEFAULT_LOG_LEVEL)
            .message(DEFAULT_MESSAGE)
            .username(DEFAULT_USERNAME)
            .aplicatie(DEFAULT_APLICATIE)
            .cod(DEFAULT_COD)
            .tip(DEFAULT_TIP);
        return appLogs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppLogs createUpdatedEntity(EntityManager em) {
        AppLogs appLogs = new AppLogs()
            .logId(UPDATED_LOG_ID)
            .entryDate(UPDATED_ENTRY_DATE)
            .logger(UPDATED_LOGGER)
            .logLevel(UPDATED_LOG_LEVEL)
            .message(UPDATED_MESSAGE)
            .username(UPDATED_USERNAME)
            .aplicatie(UPDATED_APLICATIE)
            .cod(UPDATED_COD)
            .tip(UPDATED_TIP);
        return appLogs;
    }

    @BeforeEach
    public void initTest() {
        appLogs = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppLogs() throws Exception {
        int databaseSizeBeforeCreate = appLogsRepository.findAll().size();

        // Create the AppLogs
        restAppLogsMockMvc.perform(post("/api/app-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appLogs)))
            .andExpect(status().isCreated());

        // Validate the AppLogs in the database
        List<AppLogs> appLogsList = appLogsRepository.findAll();
        assertThat(appLogsList).hasSize(databaseSizeBeforeCreate + 1);
        AppLogs testAppLogs = appLogsList.get(appLogsList.size() - 1);
        assertThat(testAppLogs.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testAppLogs.getEntryDate()).isEqualTo(DEFAULT_ENTRY_DATE);
        assertThat(testAppLogs.getLogger()).isEqualTo(DEFAULT_LOGGER);
        assertThat(testAppLogs.getLogLevel()).isEqualTo(DEFAULT_LOG_LEVEL);
        assertThat(testAppLogs.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testAppLogs.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testAppLogs.getAplicatie()).isEqualTo(DEFAULT_APLICATIE);
        assertThat(testAppLogs.getCod()).isEqualTo(DEFAULT_COD);
        assertThat(testAppLogs.getTip()).isEqualTo(DEFAULT_TIP);
    }

    @Test
    @Transactional
    public void createAppLogsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appLogsRepository.findAll().size();

        // Create the AppLogs with an existing ID
        appLogs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppLogsMockMvc.perform(post("/api/app-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appLogs)))
            .andExpect(status().isBadRequest());

        // Validate the AppLogs in the database
        List<AppLogs> appLogsList = appLogsRepository.findAll();
        assertThat(appLogsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAppLogs() throws Exception {
        // Initialize the database
        appLogsRepository.saveAndFlush(appLogs);

        // Get all the appLogsList
        restAppLogsMockMvc.perform(get("/api/app-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].logId").value(hasItem(DEFAULT_LOG_ID.toString())))
            .andExpect(jsonPath("$.[*].entryDate").value(hasItem(sameInstant(DEFAULT_ENTRY_DATE))))
            .andExpect(jsonPath("$.[*].logger").value(hasItem(DEFAULT_LOGGER)))
            .andExpect(jsonPath("$.[*].logLevel").value(hasItem(DEFAULT_LOG_LEVEL)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].aplicatie").value(hasItem(DEFAULT_APLICATIE)))
            .andExpect(jsonPath("$.[*].cod").value(hasItem(DEFAULT_COD)))
            .andExpect(jsonPath("$.[*].tip").value(hasItem(DEFAULT_TIP)));
    }
    
    @Test
    @Transactional
    public void getAppLogs() throws Exception {
        // Initialize the database
        appLogsRepository.saveAndFlush(appLogs);

        // Get the appLogs
        restAppLogsMockMvc.perform(get("/api/app-logs/{id}", appLogs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appLogs.getId().intValue()))
            .andExpect(jsonPath("$.logId").value(DEFAULT_LOG_ID.toString()))
            .andExpect(jsonPath("$.entryDate").value(sameInstant(DEFAULT_ENTRY_DATE)))
            .andExpect(jsonPath("$.logger").value(DEFAULT_LOGGER))
            .andExpect(jsonPath("$.logLevel").value(DEFAULT_LOG_LEVEL))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.aplicatie").value(DEFAULT_APLICATIE))
            .andExpect(jsonPath("$.cod").value(DEFAULT_COD))
            .andExpect(jsonPath("$.tip").value(DEFAULT_TIP));
    }

    @Test
    @Transactional
    public void getNonExistingAppLogs() throws Exception {
        // Get the appLogs
        restAppLogsMockMvc.perform(get("/api/app-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppLogs() throws Exception {
        // Initialize the database
        appLogsRepository.saveAndFlush(appLogs);

        int databaseSizeBeforeUpdate = appLogsRepository.findAll().size();

        // Update the appLogs
        AppLogs updatedAppLogs = appLogsRepository.findById(appLogs.getId()).get();
        // Disconnect from session so that the updates on updatedAppLogs are not directly saved in db
        em.detach(updatedAppLogs);
        updatedAppLogs
            .logId(UPDATED_LOG_ID)
            .entryDate(UPDATED_ENTRY_DATE)
            .logger(UPDATED_LOGGER)
            .logLevel(UPDATED_LOG_LEVEL)
            .message(UPDATED_MESSAGE)
            .username(UPDATED_USERNAME)
            .aplicatie(UPDATED_APLICATIE)
            .cod(UPDATED_COD)
            .tip(UPDATED_TIP);

        restAppLogsMockMvc.perform(put("/api/app-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppLogs)))
            .andExpect(status().isOk());

        // Validate the AppLogs in the database
        List<AppLogs> appLogsList = appLogsRepository.findAll();
        assertThat(appLogsList).hasSize(databaseSizeBeforeUpdate);
        AppLogs testAppLogs = appLogsList.get(appLogsList.size() - 1);
        assertThat(testAppLogs.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testAppLogs.getEntryDate()).isEqualTo(UPDATED_ENTRY_DATE);
        assertThat(testAppLogs.getLogger()).isEqualTo(UPDATED_LOGGER);
        assertThat(testAppLogs.getLogLevel()).isEqualTo(UPDATED_LOG_LEVEL);
        assertThat(testAppLogs.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testAppLogs.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testAppLogs.getAplicatie()).isEqualTo(UPDATED_APLICATIE);
        assertThat(testAppLogs.getCod()).isEqualTo(UPDATED_COD);
        assertThat(testAppLogs.getTip()).isEqualTo(UPDATED_TIP);
    }

    @Test
    @Transactional
    public void updateNonExistingAppLogs() throws Exception {
        int databaseSizeBeforeUpdate = appLogsRepository.findAll().size();

        // Create the AppLogs

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppLogsMockMvc.perform(put("/api/app-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appLogs)))
            .andExpect(status().isBadRequest());

        // Validate the AppLogs in the database
        List<AppLogs> appLogsList = appLogsRepository.findAll();
        assertThat(appLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppLogs() throws Exception {
        // Initialize the database
        appLogsRepository.saveAndFlush(appLogs);

        int databaseSizeBeforeDelete = appLogsRepository.findAll().size();

        // Delete the appLogs
        restAppLogsMockMvc.perform(delete("/api/app-logs/{id}", appLogs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppLogs> appLogsList = appLogsRepository.findAll();
        assertThat(appLogsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppLogs.class);
        AppLogs appLogs1 = new AppLogs();
        appLogs1.setId(1L);
        AppLogs appLogs2 = new AppLogs();
        appLogs2.setId(appLogs1.getId());
        assertThat(appLogs1).isEqualTo(appLogs2);
        appLogs2.setId(2L);
        assertThat(appLogs1).isNotEqualTo(appLogs2);
        appLogs1.setId(null);
        assertThat(appLogs1).isNotEqualTo(appLogs2);
    }
}
