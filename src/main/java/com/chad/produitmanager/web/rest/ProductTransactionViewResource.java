package com.chad.produitmanager.web.rest;

import com.chad.produitmanager.domain.ProductTransactionView;
import com.chad.produitmanager.repository.ProductTransactionViewRepository;
import com.chad.produitmanager.service.ProductTransactionViewService;
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
 * REST controller for managing {@link com.chad.produitmanager.domain.ProductTransactionView}.
 */
@RestController
@RequestMapping("/api")
public class ProductTransactionViewResource {

    private final Logger log = LoggerFactory.getLogger(ProductTransactionViewResource.class);

    private static final String ENTITY_NAME = "productTransactionView";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductTransactionViewService productTransactionViewService;

    private final ProductTransactionViewRepository productTransactionViewRepository;

    public ProductTransactionViewResource(
        ProductTransactionViewService productTransactionViewService,
        ProductTransactionViewRepository productTransactionViewRepository
    ) {
        this.productTransactionViewService = productTransactionViewService;
        this.productTransactionViewRepository = productTransactionViewRepository;
    }

    /**
     * {@code POST  /product-transaction-views} : Create a new productTransactionView.
     *
     * @param productTransactionView the productTransactionView to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productTransactionView, or with status {@code 400 (Bad Request)} if the productTransactionView has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-transaction-views")
    public ResponseEntity<ProductTransactionView> createProductTransactionView(@RequestBody ProductTransactionView productTransactionView)
        throws URISyntaxException {
        log.debug("REST request to save ProductTransactionView : {}", productTransactionView);
        if (productTransactionView.getId() != null) {
            throw new BadRequestAlertException("A new productTransactionView cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductTransactionView result = productTransactionViewService.save(productTransactionView);
        return ResponseEntity
            .created(new URI("/api/product-transaction-views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-transaction-views/:id} : Updates an existing productTransactionView.
     *
     * @param id the id of the productTransactionView to save.
     * @param productTransactionView the productTransactionView to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productTransactionView,
     * or with status {@code 400 (Bad Request)} if the productTransactionView is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productTransactionView couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-transaction-views/{id}")
    public ResponseEntity<ProductTransactionView> updateProductTransactionView(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductTransactionView productTransactionView
    ) throws URISyntaxException {
        log.debug("REST request to update ProductTransactionView : {}, {}", id, productTransactionView);
        if (productTransactionView.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productTransactionView.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productTransactionViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductTransactionView result = productTransactionViewService.update(productTransactionView);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productTransactionView.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-transaction-views/:id} : Partial updates given fields of an existing productTransactionView, field will ignore if it is null
     *
     * @param id the id of the productTransactionView to save.
     * @param productTransactionView the productTransactionView to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productTransactionView,
     * or with status {@code 400 (Bad Request)} if the productTransactionView is not valid,
     * or with status {@code 404 (Not Found)} if the productTransactionView is not found,
     * or with status {@code 500 (Internal Server Error)} if the productTransactionView couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-transaction-views/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductTransactionView> partialUpdateProductTransactionView(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductTransactionView productTransactionView
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductTransactionView partially : {}, {}", id, productTransactionView);
        if (productTransactionView.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productTransactionView.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productTransactionViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductTransactionView> result = productTransactionViewService.partialUpdate(productTransactionView);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productTransactionView.getId().toString())
        );
    }

    /**
     * {@code GET  /product-transaction-views} : get all the productTransactionViews.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productTransactionViews in body.
     */
    @GetMapping("/product-transaction-views")
    public ResponseEntity<List<ProductTransactionView>> getAllProductTransactionViews(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProductTransactionViews");
        Page<ProductTransactionView> page = productTransactionViewService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-transaction-views/:id} : get the "id" productTransactionView.
     *
     * @param id the id of the productTransactionView to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productTransactionView, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-transaction-views/{id}")
    public ResponseEntity<ProductTransactionView> getProductTransactionView(@PathVariable Long id) {
        log.debug("REST request to get ProductTransactionView : {}", id);
        Optional<ProductTransactionView> productTransactionView = productTransactionViewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productTransactionView);
    }

    /**
     * {@code DELETE  /product-transaction-views/:id} : delete the "id" productTransactionView.
     *
     * @param id the id of the productTransactionView to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-transaction-views/{id}")
    public ResponseEntity<Void> deleteProductTransactionView(@PathVariable Long id) {
        log.debug("REST request to delete ProductTransactionView : {}", id);
        productTransactionViewService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
