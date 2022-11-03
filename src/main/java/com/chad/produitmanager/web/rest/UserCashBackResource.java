package com.chad.produitmanager.web.rest;

import com.chad.produitmanager.repository.UserCashBackRepository;
import com.chad.produitmanager.service.UserCashBackService;
import com.chad.produitmanager.service.dto.UserCashBackDTO;
import com.chad.produitmanager.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.chad.produitmanager.domain.UserCashBack}.
 */
@RestController
@RequestMapping("/api")
public class UserCashBackResource {

    private final Logger log = LoggerFactory.getLogger(UserCashBackResource.class);

    private static final String ENTITY_NAME = "userCashBack";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserCashBackService userCashBackService;

    private final UserCashBackRepository userCashBackRepository;

    public UserCashBackResource(UserCashBackService userCashBackService, UserCashBackRepository userCashBackRepository) {
        this.userCashBackService = userCashBackService;
        this.userCashBackRepository = userCashBackRepository;
    }

    /**
     * {@code POST  /user-cash-backs} : Create a new userCashBack.
     *
     * @param userCashBackDTO the userCashBackDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userCashBackDTO, or with status {@code 400 (Bad Request)} if the userCashBack has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-cash-backs")
    public ResponseEntity<UserCashBackDTO> createUserCashBack(@Valid @RequestBody UserCashBackDTO userCashBackDTO)
        throws URISyntaxException {
        log.debug("REST request to save UserCashBack : {}", userCashBackDTO);
        if (userCashBackDTO.getId() != null) {
            throw new BadRequestAlertException("A new userCashBack cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserCashBackDTO result = userCashBackService.save(userCashBackDTO);
        return ResponseEntity
            .created(new URI("/api/user-cash-backs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-cash-backs/:id} : Updates an existing userCashBack.
     *
     * @param id the id of the userCashBackDTO to save.
     * @param userCashBackDTO the userCashBackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userCashBackDTO,
     * or with status {@code 400 (Bad Request)} if the userCashBackDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userCashBackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-cash-backs/{id}")
    public ResponseEntity<UserCashBackDTO> updateUserCashBack(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserCashBackDTO userCashBackDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserCashBack : {}, {}", id, userCashBackDTO);
        if (userCashBackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userCashBackDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userCashBackRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserCashBackDTO result = userCashBackService.update(userCashBackDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userCashBackDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-cash-backs/:id} : Partial updates given fields of an existing userCashBack, field will ignore if it is null
     *
     * @param id the id of the userCashBackDTO to save.
     * @param userCashBackDTO the userCashBackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userCashBackDTO,
     * or with status {@code 400 (Bad Request)} if the userCashBackDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userCashBackDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userCashBackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-cash-backs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserCashBackDTO> partialUpdateUserCashBack(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserCashBackDTO userCashBackDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserCashBack partially : {}, {}", id, userCashBackDTO);
        if (userCashBackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userCashBackDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userCashBackRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserCashBackDTO> result = userCashBackService.partialUpdate(userCashBackDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userCashBackDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-cash-backs} : get all the userCashBacks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userCashBacks in body.
     */
    @GetMapping("/user-cash-backs")
    public ResponseEntity<List<UserCashBackDTO>> getAllUserCashBacks(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of UserCashBacks");
        Page<UserCashBackDTO> page = userCashBackService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-cash-backs/:id} : get the "id" userCashBack.
     *
     * @param id the id of the userCashBackDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userCashBackDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-cash-backs/{id}")
    public ResponseEntity<UserCashBackDTO> getUserCashBack(@PathVariable Long id) {
        log.debug("REST request to get UserCashBack : {}", id);
        Optional<UserCashBackDTO> userCashBackDTO = userCashBackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userCashBackDTO);
    }

    /**
     * {@code DELETE  /user-cash-backs/:id} : delete the "id" userCashBack.
     *
     * @param id the id of the userCashBackDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-cash-backs/{id}")
    public ResponseEntity<Void> deleteUserCashBack(@PathVariable Long id) {
        log.debug("REST request to delete UserCashBack : {}", id);
        userCashBackService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
