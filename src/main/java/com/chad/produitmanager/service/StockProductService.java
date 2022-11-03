package com.chad.produitmanager.service;

import com.chad.produitmanager.service.dto.StockProductDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.chad.produitmanager.domain.StockProduct}.
 */
public interface StockProductService {
    /**
     * Save a stockProduct.
     *
     * @param stockProductDTO the entity to save.
     * @return the persisted entity.
     */
    StockProductDTO save(StockProductDTO stockProductDTO);

    /**
     * Updates a stockProduct.
     *
     * @param stockProductDTO the entity to update.
     * @return the persisted entity.
     */
    StockProductDTO update(StockProductDTO stockProductDTO);

    /**
     * Partially updates a stockProduct.
     *
     * @param stockProductDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StockProductDTO> partialUpdate(StockProductDTO stockProductDTO);

    /**
     * Get all the stockProducts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StockProductDTO> findAll(Pageable pageable);

    /**
     * Get the "id" stockProduct.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockProductDTO> findOne(Long id);

    /**
     * Delete the "id" stockProduct.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
