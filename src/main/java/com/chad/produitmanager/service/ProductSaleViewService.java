package com.chad.produitmanager.service;

import com.chad.produitmanager.domain.ProductSaleView;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProductSaleView}.
 */
public interface ProductSaleViewService {
    /**
     * Save a productSaleView.
     *
     * @param productSaleView the entity to save.
     * @return the persisted entity.
     */
    ProductSaleView save(ProductSaleView productSaleView);

    /**
     * Updates a productSaleView.
     *
     * @param productSaleView the entity to update.
     * @return the persisted entity.
     */
    ProductSaleView update(ProductSaleView productSaleView);

    /**
     * Partially updates a productSaleView.
     *
     * @param productSaleView the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductSaleView> partialUpdate(ProductSaleView productSaleView);

    /**
     * Get all the productSaleViews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductSaleView> findAll(Pageable pageable);

    /**
     * Get the "id" productSaleView.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductSaleView> findOne(Long id);

    /**
     * Delete the "id" productSaleView.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
