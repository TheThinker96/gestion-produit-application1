package com.chad.produitmanager.service;

import com.chad.produitmanager.domain.StockProductView;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link StockProductView}.
 */
public interface StockProductViewService {
    /**
     * Save a stockProductView.
     *
     * @param stockProductView the entity to save.
     * @return the persisted entity.
     */
    StockProductView save(StockProductView stockProductView);

    /**
     * Updates a stockProductView.
     *
     * @param stockProductView the entity to update.
     * @return the persisted entity.
     */
    StockProductView update(StockProductView stockProductView);

    /**
     * Partially updates a stockProductView.
     *
     * @param stockProductView the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StockProductView> partialUpdate(StockProductView stockProductView);

    /**
     * Get all the stockProductViews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StockProductView> findAll(Pageable pageable);

    /**
     * Get the "id" stockProductView.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockProductView> findOne(Long id);

    /**
     * Delete the "id" stockProductView.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
