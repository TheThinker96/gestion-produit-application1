package com.chad.produitmanager.web.rest;

import com.chad.produitmanager.repository.ProductSaleRepository;
import com.chad.produitmanager.service.ProductSaleService;
import com.chad.produitmanager.service.dto.ProductSaleDTO;
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
 * REST controller for managing {@link com.chad.produitmanager.domain.ProductSale}.
 */
@RestController
@RequestMapping("/api")
public class ProductSaleResource {

    private final Logger log = LoggerFactory.getLogger(ProductSaleResource.class);

    private static final String ENTITY_NAME = "productSale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductSaleService productSaleService;

    private final ProductSaleRepository productSaleRepository;

    public ProductSaleResource(ProductSaleService productSaleService, ProductSaleRepository productSaleRepository) {
        this.productSaleService = productSaleService;
        this.productSaleRepository = productSaleRepository;
    }

    /**
     * {@code POST  /product-sales} : Create a new productSale.
     *
     * @param productSaleDTO the productSaleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productSaleDTO, or with status {@code 400 (Bad Request)} if the productSale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-sales")
    public ResponseEntity<ProductSaleDTO> createProductSale(@Valid @RequestBody ProductSaleDTO productSaleDTO) throws URISyntaxException {
        log.debug("REST request to save ProductSale : {}", productSaleDTO);
        if (productSaleDTO.getId() != null) {
            throw new BadRequestAlertException("A new productSale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductSaleDTO result = productSaleService.save(productSaleDTO);
        return ResponseEntity
            .created(new URI("/api/product-sales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-sales/:id} : Updates an existing productSale.
     *
     * @param id the id of the productSaleDTO to save.
     * @param productSaleDTO the productSaleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSaleDTO,
     * or with status {@code 400 (Bad Request)} if the productSaleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productSaleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-sales/{id}")
    public ResponseEntity<ProductSaleDTO> updateProductSale(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductSaleDTO productSaleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductSale : {}, {}", id, productSaleDTO);
        if (productSaleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productSaleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productSaleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductSaleDTO result = productSaleService.update(productSaleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productSaleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-sales/:id} : Partial updates given fields of an existing productSale, field will ignore if it is null
     *
     * @param id the id of the productSaleDTO to save.
     * @param productSaleDTO the productSaleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSaleDTO,
     * or with status {@code 400 (Bad Request)} if the productSaleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productSaleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productSaleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-sales/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductSaleDTO> partialUpdateProductSale(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductSaleDTO productSaleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductSale partially : {}, {}", id, productSaleDTO);
        if (productSaleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productSaleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productSaleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductSaleDTO> result = productSaleService.partialUpdate(productSaleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productSaleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-sales} : get all the productSales.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productSales in body.
     */
    @GetMapping("/product-sales")
    public ResponseEntity<List<ProductSaleDTO>> getAllProductSales(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ProductSales");
        Page<ProductSaleDTO> page = productSaleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-sales/:id} : get the "id" productSale.
     *
     * @param id the id of the productSaleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productSaleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-sales/{id}")
    public ResponseEntity<ProductSaleDTO> getProductSale(@PathVariable Long id) {
        log.debug("REST request to get ProductSale : {}", id);
        Optional<ProductSaleDTO> productSaleDTO = productSaleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productSaleDTO);
    }

    /**
     * {@code DELETE  /product-sales/:id} : delete the "id" productSale.
     *
     * @param id the id of the productSaleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-sales/{id}")
    public ResponseEntity<Void> deleteProductSale(@PathVariable Long id) {
        log.debug("REST request to delete ProductSale : {}", id);
        productSaleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
