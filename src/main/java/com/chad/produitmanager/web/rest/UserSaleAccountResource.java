package com.chad.produitmanager.web.rest;

import com.chad.produitmanager.repository.UserSaleAccountRepository;
import com.chad.produitmanager.service.UserSaleAccountService;
import com.chad.produitmanager.service.dto.UserSaleAccountDTO;
import com.chad.produitmanager.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.chad.produitmanager.domain.UserSaleAccount}.
 */
@RestController
@RequestMapping("/api")
public class UserSaleAccountResource {

    private final Logger log = LoggerFactory.getLogger(UserSaleAccountResource.class);

    private static final String ENTITY_NAME = "userSaleAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserSaleAccountService userSaleAccountService;

    private final UserSaleAccountRepository userSaleAccountRepository;

    public UserSaleAccountResource(UserSaleAccountService userSaleAccountService, UserSaleAccountRepository userSaleAccountRepository) {
        this.userSaleAccountService = userSaleAccountService;
        this.userSaleAccountRepository = userSaleAccountRepository;
    }

    /**
     * {@code POST  /user-sale-accounts} : Create a new userSaleAccount.
     *
     * @param userSaleAccountDTO the userSaleAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userSaleAccountDTO, or with status {@code 400 (Bad Request)} if the userSaleAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-sale-accounts")
    public ResponseEntity<UserSaleAccountDTO> createUserSaleAccount(@RequestBody UserSaleAccountDTO userSaleAccountDTO)
        throws URISyntaxException {
        log.debug("REST request to save UserSaleAccount : {}", userSaleAccountDTO);
        if (userSaleAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new userSaleAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserSaleAccountDTO result = userSaleAccountService.save(userSaleAccountDTO);
        return ResponseEntity
            .created(new URI("/api/user-sale-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-sale-accounts/:id} : Updates an existing userSaleAccount.
     *
     * @param id the id of the userSaleAccountDTO to save.
     * @param userSaleAccountDTO the userSaleAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userSaleAccountDTO,
     * or with status {@code 400 (Bad Request)} if the userSaleAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userSaleAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-sale-accounts/{id}")
    public ResponseEntity<UserSaleAccountDTO> updateUserSaleAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserSaleAccountDTO userSaleAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserSaleAccount : {}, {}", id, userSaleAccountDTO);
        if (userSaleAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userSaleAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userSaleAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserSaleAccountDTO result = userSaleAccountService.update(userSaleAccountDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userSaleAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-sale-accounts/:id} : Partial updates given fields of an existing userSaleAccount, field will ignore if it is null
     *
     * @param id the id of the userSaleAccountDTO to save.
     * @param userSaleAccountDTO the userSaleAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userSaleAccountDTO,
     * or with status {@code 400 (Bad Request)} if the userSaleAccountDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userSaleAccountDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userSaleAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-sale-accounts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserSaleAccountDTO> partialUpdateUserSaleAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserSaleAccountDTO userSaleAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserSaleAccount partially : {}, {}", id, userSaleAccountDTO);
        if (userSaleAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userSaleAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userSaleAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserSaleAccountDTO> result = userSaleAccountService.partialUpdate(userSaleAccountDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userSaleAccountDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-sale-accounts} : get all the userSaleAccounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userSaleAccounts in body.
     */
    @GetMapping("/user-sale-accounts")
    public ResponseEntity<List<UserSaleAccountDTO>> getAllUserSaleAccounts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of UserSaleAccounts");
        Page<UserSaleAccountDTO> page = userSaleAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-sale-accounts/:id} : get the "id" userSaleAccount.
     *
     * @param id the id of the userSaleAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userSaleAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-sale-accounts/{id}")
    public ResponseEntity<UserSaleAccountDTO> getUserSaleAccount(@PathVariable Long id) {
        log.debug("REST request to get UserSaleAccount : {}", id);
        Optional<UserSaleAccountDTO> userSaleAccountDTO = userSaleAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userSaleAccountDTO);
    }

    /**
     * {@code DELETE  /user-sale-accounts/:id} : delete the "id" userSaleAccount.
     *
     * @param id the id of the userSaleAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-sale-accounts/{id}")
    public ResponseEntity<Void> deleteUserSaleAccount(@PathVariable Long id) {
        log.debug("REST request to delete UserSaleAccount : {}", id);
        userSaleAccountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
