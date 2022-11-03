package com.chad.produitmanager.web.rest;

import com.chad.produitmanager.domain.ProductSaleView;
import com.chad.produitmanager.repository.ProductSaleViewRepository;
import com.chad.produitmanager.service.ProductSaleViewService;
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
 * REST controller for managing {@link com.chad.produitmanager.domain.ProductSaleView}.
 */
@RestController
@RequestMapping("/api")
public class ProductSaleViewResource {

    private final Logger log = LoggerFactory.getLogger(ProductSaleViewResource.class);

    private static final String ENTITY_NAME = "productSaleView";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductSaleViewService productSaleViewService;

    private final ProductSaleViewRepository productSaleViewRepository;

    public ProductSaleViewResource(ProductSaleViewService productSaleViewService, ProductSaleViewRepository productSaleViewRepository) {
        this.productSaleViewService = productSaleViewService;
        this.productSaleViewRepository = productSaleViewRepository;
    }

    /**
     * {@code POST  /product-sale-views} : Create a new productSaleView.
     *
     * @param productSaleView the productSaleView to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productSaleView, or with status {@code 400 (Bad Request)} if the productSaleView has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-sale-views")
    public ResponseEntity<ProductSaleView> createProductSaleView(@RequestBody ProductSaleView productSaleView) throws URISyntaxException {
        log.debug("REST request to save ProductSaleView : {}", productSaleView);
        if (productSaleView.getId() != null) {
            throw new BadRequestAlertException("A new productSaleView cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductSaleView result = productSaleViewService.save(productSaleView);
        return ResponseEntity
            .created(new URI("/api/product-sale-views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-sale-views/:id} : Updates an existing productSaleView.
     *
     * @param id the id of the productSaleView to save.
     * @param productSaleView the productSaleView to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSaleView,
     * or with status {@code 400 (Bad Request)} if the productSaleView is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productSaleView couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-sale-views/{id}")
    public ResponseEntity<ProductSaleView> updateProductSaleView(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductSaleView productSaleView
    ) throws URISyntaxException {
        log.debug("REST request to update ProductSaleView : {}, {}", id, productSaleView);
        if (productSaleView.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productSaleView.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productSaleViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductSaleView result = productSaleViewService.update(productSaleView);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productSaleView.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-sale-views/:id} : Partial updates given fields of an existing productSaleView, field will ignore if it is null
     *
     * @param id the id of the productSaleView to save.
     * @param productSaleView the productSaleView to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSaleView,
     * or with status {@code 400 (Bad Request)} if the productSaleView is not valid,
     * or with status {@code 404 (Not Found)} if the productSaleView is not found,
     * or with status {@code 500 (Internal Server Error)} if the productSaleView couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-sale-views/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductSaleView> partialUpdateProductSaleView(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductSaleView productSaleView
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductSaleView partially : {}, {}", id, productSaleView);
        if (productSaleView.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productSaleView.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productSaleViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductSaleView> result = productSaleViewService.partialUpdate(productSaleView);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productSaleView.getId().toString())
        );
    }

    /**
     * {@code GET  /product-sale-views} : get all the productSaleViews.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productSaleViews in body.
     */
    @GetMapping("/product-sale-views")
    public ResponseEntity<List<ProductSaleView>> getAllProductSaleViews(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ProductSaleViews");
        Page<ProductSaleView> page = productSaleViewService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-sale-views/:id} : get the "id" productSaleView.
     *
     * @param id the id of the productSaleView to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productSaleView, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-sale-views/{id}")
    public ResponseEntity<ProductSaleView> getProductSaleView(@PathVariable Long id) {
        log.debug("REST request to get ProductSaleView : {}", id);
        Optional<ProductSaleView> productSaleView = productSaleViewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productSaleView);
    }

    /**
     * {@code DELETE  /product-sale-views/:id} : delete the "id" productSaleView.
     *
     * @param id the id of the productSaleView to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-sale-views/{id}")
    public ResponseEntity<Void> deleteProductSaleView(@PathVariable Long id) {
        log.debug("REST request to delete ProductSaleView : {}", id);
        productSaleViewService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
