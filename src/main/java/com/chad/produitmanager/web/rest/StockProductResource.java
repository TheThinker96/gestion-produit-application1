package com.chad.produitmanager.web.rest;

import com.chad.produitmanager.repository.StockProductRepository;
import com.chad.produitmanager.service.StockProductService;
import com.chad.produitmanager.service.dto.StockProductDTO;
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
 * REST controller for managing {@link com.chad.produitmanager.domain.StockProduct}.
 */
@RestController
@RequestMapping("/api")
public class StockProductResource {

    private final Logger log = LoggerFactory.getLogger(StockProductResource.class);

    private static final String ENTITY_NAME = "stockProduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockProductService stockProductService;

    private final StockProductRepository stockProductRepository;

    public StockProductResource(StockProductService stockProductService, StockProductRepository stockProductRepository) {
        this.stockProductService = stockProductService;
        this.stockProductRepository = stockProductRepository;
    }

    /**
     * {@code POST  /stock-products} : Create a new stockProduct.
     *
     * @param stockProductDTO the stockProductDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockProductDTO, or with status {@code 400 (Bad Request)} if the stockProduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stock-products")
    public ResponseEntity<StockProductDTO> createStockProduct(@Valid @RequestBody StockProductDTO stockProductDTO)
        throws URISyntaxException {
        log.debug("REST request to save StockProduct : {}", stockProductDTO);
        if (stockProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockProductDTO result = stockProductService.save(stockProductDTO);
        return ResponseEntity
            .created(new URI("/api/stock-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stock-products/:id} : Updates an existing stockProduct.
     *
     * @param id the id of the stockProductDTO to save.
     * @param stockProductDTO the stockProductDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockProductDTO,
     * or with status {@code 400 (Bad Request)} if the stockProductDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockProductDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stock-products/{id}")
    public ResponseEntity<StockProductDTO> updateStockProduct(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StockProductDTO stockProductDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StockProduct : {}, {}", id, stockProductDTO);
        if (stockProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stockProductDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stockProductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StockProductDTO result = stockProductService.update(stockProductDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /stock-products/:id} : Partial updates given fields of an existing stockProduct, field will ignore if it is null
     *
     * @param id the id of the stockProductDTO to save.
     * @param stockProductDTO the stockProductDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockProductDTO,
     * or with status {@code 400 (Bad Request)} if the stockProductDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stockProductDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stockProductDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/stock-products/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StockProductDTO> partialUpdateStockProduct(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StockProductDTO stockProductDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StockProduct partially : {}, {}", id, stockProductDTO);
        if (stockProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stockProductDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stockProductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StockProductDTO> result = stockProductService.partialUpdate(stockProductDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockProductDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /stock-products} : get all the stockProducts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockProducts in body.
     */
    @GetMapping("/stock-products")
    public ResponseEntity<List<StockProductDTO>> getAllStockProducts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of StockProducts");
        Page<StockProductDTO> page = stockProductService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stock-products/:id} : get the "id" stockProduct.
     *
     * @param id the id of the stockProductDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockProductDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-products/{id}")
    public ResponseEntity<StockProductDTO> getStockProduct(@PathVariable Long id) {
        log.debug("REST request to get StockProduct : {}", id);
        Optional<StockProductDTO> stockProductDTO = stockProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockProductDTO);
    }

    /**
     * {@code DELETE  /stock-products/:id} : delete the "id" stockProduct.
     *
     * @param id the id of the stockProductDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-products/{id}")
    public ResponseEntity<Void> deleteStockProduct(@PathVariable Long id) {
        log.debug("REST request to delete StockProduct : {}", id);
        stockProductService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
