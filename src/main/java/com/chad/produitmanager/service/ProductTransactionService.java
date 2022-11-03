package com.chad.produitmanager.service;

import com.chad.produitmanager.service.dto.ProductTransactionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.chad.produitmanager.domain.ProductTransaction}.
 */
public interface ProductTransactionService {
    /**
     * Save a productTransaction.
     *
     * @param productTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    ProductTransactionDTO save(ProductTransactionDTO productTransactionDTO);

    /**
     * Updates a productTransaction.
     *
     * @param productTransactionDTO the entity to update.
     * @return the persisted entity.
     */
    ProductTransactionDTO update(ProductTransactionDTO productTransactionDTO);

    /**
     * Partially updates a productTransaction.
     *
     * @param productTransactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductTransactionDTO> partialUpdate(ProductTransactionDTO productTransactionDTO);

    /**
     * Get all the productTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductTransactionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" productTransaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductTransactionDTO> findOne(Long id);

    /**
     * Delete the "id" productTransaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
