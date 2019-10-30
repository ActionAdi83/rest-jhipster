package ro.siveco.hipster.web.rest;

import ro.siveco.hipster.domain.AppLogs;
import ro.siveco.hipster.repository.AppLogsRepository;
import ro.siveco.hipster.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ro.siveco.hipster.domain.AppLogs}.
 */
@RestController
@RequestMapping("/api")
public class AppLogsResource {

    private final Logger log = LoggerFactory.getLogger(AppLogsResource.class);

    private static final String ENTITY_NAME = "restappAppLogs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppLogsRepository appLogsRepository;

    public AppLogsResource(AppLogsRepository appLogsRepository) {
        this.appLogsRepository = appLogsRepository;
    }

    /**
     * {@code POST  /app-logs} : Create a new appLogs.
     *
     * @param appLogs the appLogs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appLogs, or with status {@code 400 (Bad Request)} if the appLogs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-logs")
    public ResponseEntity<AppLogs> createAppLogs(@RequestBody AppLogs appLogs) throws URISyntaxException {
        log.debug("REST request to save AppLogs : {}", appLogs);
        if (appLogs.getId() != null) {
            throw new BadRequestAlertException("A new appLogs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppLogs result = appLogsRepository.save(appLogs);
        return ResponseEntity.created(new URI("/api/app-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-logs} : Updates an existing appLogs.
     *
     * @param appLogs the appLogs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appLogs,
     * or with status {@code 400 (Bad Request)} if the appLogs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appLogs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-logs")
    public ResponseEntity<AppLogs> updateAppLogs(@RequestBody AppLogs appLogs) throws URISyntaxException {
        log.debug("REST request to update AppLogs : {}", appLogs);
        if (appLogs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AppLogs result = appLogsRepository.save(appLogs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appLogs.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /app-logs} : get all the appLogs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appLogs in body.
     */
    @GetMapping("/app-logs")
    public List<AppLogs> getAllAppLogs() {
        log.debug("REST request to get all AppLogs");
        return appLogsRepository.findAll();
    }

    /**
     * {@code GET  /app-logs/:id} : get the "id" appLogs.
     *
     * @param id the id of the appLogs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appLogs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-logs/{id}")
    public ResponseEntity<AppLogs> getAppLogs(@PathVariable Long id) {
        log.debug("REST request to get AppLogs : {}", id);
        Optional<AppLogs> appLogs = appLogsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(appLogs);
    }

    /**
     * {@code DELETE  /app-logs/:id} : delete the "id" appLogs.
     *
     * @param id the id of the appLogs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-logs/{id}")
    public ResponseEntity<Void> deleteAppLogs(@PathVariable Long id) {
        log.debug("REST request to delete AppLogs : {}", id);
        appLogsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
