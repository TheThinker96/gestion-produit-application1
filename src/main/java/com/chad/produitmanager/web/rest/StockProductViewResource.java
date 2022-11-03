package com.chad.produitmanager.web.rest;

import com.chad.produitmanager.domain.StockProductView;
import com.chad.produitmanager.repository.StockProductViewRepository;
import com.chad.produitmanager.service.StockProductViewService;
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
 * REST controller for managing {@link com.chad.produitmanager.domain.StockProductView}.
 */
@RestController
@RequestMapping("/api")
public class StockProductViewResource {

    private final Logger log = LoggerFactory.getLogger(StockProductViewResource.class);

    private static final String ENTITY_NAME = "stockProductView";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockProductViewService stockProductViewService;

    private final StockProductViewRepository stockProductViewRepository;

    public StockProductViewResource(
        StockProductViewService stockProductViewService,
        StockProductViewRepository stockProductViewRepository
    ) {
        this.stockProductViewService = stockProductViewService;
        this.stockProductViewRepository = stockProductViewRepository;
    }

    /**
     * {@code POST  /stock-product-views} : Create a new stockProductView.
     *
     * @param stockProductView the stockProductView to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockProductView, or with status {@code 400 (Bad Request)} if the stockProductView has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stock-product-views")
    public ResponseEntity<StockProductView> createStockProductView(@RequestBody StockProductView stockProductView)
        throws URISyntaxException {
        log.debug("REST request to save StockProductView : {}", stockProductView);
        if (stockProductView.getId() != null) {
            throw new BadRequestAlertException("A new stockProductView cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockProductView result = stockProductViewService.save(stockProductView);
        return ResponseEntity
            .created(new URI("/api/stock-product-views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stock-product-views/:id} : Updates an existing stockProductView.
     *
     * @param id the id of the stockProductView to save.
     * @param stockProductView the stockProductView to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockProductView,
     * or with status {@code 400 (Bad Request)} if the stockProductView is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockProductView couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stock-product-views/{id}")
    public ResponseEntity<StockProductView> updateStockProductView(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StockProductView stockProductView
    ) throws URISyntaxException {
        log.debug("REST request to update StockProductView : {}, {}", id, stockProductView);
        if (stockProductView.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stockProductView.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stockProductViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StockProductView result = stockProductViewService.update(stockProductView);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockProductView.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /stock-product-views/:id} : Partial updates given fields of an existing stockProductView, field will ignore if it is null
     *
     * @param id the id of the stockProductView to save.
     * @param stockProductView the stockProductView to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockProductView,
     * or with status {@code 400 (Bad Request)} if the stockProductView is not valid,
     * or with status {@code 404 (Not Found)} if the stockProductView is not found,
     * or with status {@code 500 (Internal Server Error)} if the stockProductView couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/stock-product-views/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StockProductView> partialUpdateStockProductView(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StockProductView stockProductView
    ) throws URISyntaxException {
        log.debug("REST request to partial update StockProductView partially : {}, {}", id, stockProductView);
        if (stockProductView.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stockProductView.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stockProductViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StockProductView> result = stockProductViewService.partialUpdate(stockProductView);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockProductView.getId().toString())
        );
    }

    /**
     * {@code GET  /stock-product-views} : get all the stockProductViews.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockProductViews in body.
     */
    @GetMapping("/stock-product-views")
    public ResponseEntity<List<StockProductView>> getAllStockProductViews(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of StockProductViews");
        Page<StockProductView> page = stockProductViewService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stock-product-views/:id} : get the "id" stockProductView.
     *
     * @param id the id of the stockProductView to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockProductView, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-product-views/{id}")
    public ResponseEntity<StockProductView> getStockProductView(@PathVariable Long id) {
        log.debug("REST request to get StockProductView : {}", id);
        Optional<StockProductView> stockProductView = stockProductViewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockProductView);
    }

    /**
     * {@code DELETE  /stock-product-views/:id} : delete the "id" stockProductView.
     *
     * @param id the id of the stockProductView to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-product-views/{id}")
    public ResponseEntity<Void> deleteStockProductView(@PathVariable Long id) {
        log.debug("REST request to delete StockProductView : {}", id);
        stockProductViewService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
