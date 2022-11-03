package com.chad.produitmanager.service;

import com.chad.produitmanager.domain.ProductTransactionView;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProductTransactionView}.
 */
public interface ProductTransactionViewService {
    /**
     * Save a productTransactionView.
     *
     * @param productTransactionView the entity to save.
     * @return the persisted entity.
     */
    ProductTransactionView save(ProductTransactionView productTransactionView);

    /**
     * Updates a productTransactionView.
     *
     * @param productTransactionView the entity to update.
     * @return the persisted entity.
     */
    ProductTransactionView update(ProductTransactionView productTransactionView);

    /**
     * Partially updates a productTransactionView.
     *
     * @param productTransactionView the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductTransactionView> partialUpdate(ProductTransactionView productTransactionView);

    /**
     * Get all the productTransactionViews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductTransactionView> findAll(Pageable pageable);

    /**
     * Get the "id" productTransactionView.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductTransactionView> findOne(Long id);

    /**
     * Delete the "id" productTransactionView.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
