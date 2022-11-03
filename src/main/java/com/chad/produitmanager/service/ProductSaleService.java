package com.chad.produitmanager.service;

import com.chad.produitmanager.service.dto.ProductSaleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.chad.produitmanager.domain.ProductSale}.
 */
public interface ProductSaleService {
    /**
     * Save a productSale.
     *
     * @param productSaleDTO the entity to save.
     * @return the persisted entity.
     */
    ProductSaleDTO save(ProductSaleDTO productSaleDTO);

    /**
     * Updates a productSale.
     *
     * @param productSaleDTO the entity to update.
     * @return the persisted entity.
     */
    ProductSaleDTO update(ProductSaleDTO productSaleDTO);

    /**
     * Partially updates a productSale.
     *
     * @param productSaleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductSaleDTO> partialUpdate(ProductSaleDTO productSaleDTO);

    /**
     * Get all the productSales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductSaleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" productSale.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductSaleDTO> findOne(Long id);

    /**
     * Delete the "id" productSale.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
